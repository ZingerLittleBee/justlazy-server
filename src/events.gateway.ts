import {
  OnGatewayConnection,
  SubscribeMessage,
  WebSocketGateway,
  WebSocketServer
} from '@nestjs/websockets'
import { Server } from 'socket.io'
import { OnGatewayInit } from '@nestjs/websockets/interfaces/hooks/on-gateway-init.interface'
import { ServerService } from './modules/server/server.service'
import { ServerDto } from './modules/server/server.dto'
import { OnEvent } from '@nestjs/event-emitter'
import { Logger } from '@nestjs/common'
import { SchedulerRegistry } from '@nestjs/schedule'
import { CronJob } from 'cron'
import { execCmd, SSHClient } from './ssh/SSHClient'
import { shellAllInOne } from './ssh/Command'
import { serverDosToDtos, serverDoToDto } from './modules/server/server.convert';
import { Client } from 'ssh2'

@WebSocketGateway(9527, { namespace: 'servers' })
export class EventsGateway implements OnGatewayInit, OnGatewayConnection {
  constructor(
    private readonly serverService: ServerService,
    private schedulerRegistry: SchedulerRegistry
  ) {}

  /**
   * server 信息
   */
  servers: ServerDto[]

  /**
   * 数据仓库
   */
  responseData = {}

  /**
   * serverId 与 conn 关系映射
   */
  connServerMap: Map<string, Client> = new Map()

  /**
   * server 的连接
   */
  conns = []

  @WebSocketServer()
  server: Server

  createConnServerMap() {
    if (this.servers?.length != 0) {
      this.servers.forEach((s) => {
        const {
          host,
          port = 22,
          username = 'root',
          password,
          key: privateKey
        } = s
        SSHClient(
          {
            host,
            port,
            username,
            password,
            privateKey
          },
          (conn) => {
            this.connServerMap.set(s.id, conn)
          }
        )
      })
    }
  }

  updateResponseData() {
    const mapSize = this.connServerMap.size
    if (mapSize === 0) return
    this.connServerMap.forEach((value, key) => {
      execCmd(value, shellAllInOne()).then((res: any) => {
        let infoStr = ''
        res.subscribe({
          next: (x) => {
            const removeLineFeed = x.replace(/[\r\n]/g, '')
            infoStr = infoStr ? infoStr + ',' + removeLineFeed : removeLineFeed
          },
          error: (err) => Logger.error('Observer got an error: ' + err),
          complete: () => {
            Logger.log('Observer got a complete notification')
            infoStr = `{${infoStr}}`.replace(/([A-Za-z]+)(?=:)/g, '"$1"')
            console.log('infoStr', infoStr)
            this.responseData[key] = JSON.parse(infoStr)
          }
        })
      })
    })
  }

  /**
   * 发送 socket 消息定时任务
   */
  sendJob: CronJob

  /**
   * 更新 responseData 定时任务
   */
  updateJob: CronJob

  addSendCronJob(name: string, cron: string) {
    this.sendJob = new CronJob(cron, () => {
      this.server.volatile.emit('servers-message', this.responseData)
      this.responseData = {}
    })
    this.schedulerRegistry.addCronJob(name, this.sendJob)
    Logger.log(`定时任务: ${name}, 开始执行`)
    this.sendJob.start()
  }

  addUpdateCronJob(name: string, cron: string) {
    this.updateJob = new CronJob(cron, () => {
      this.updateResponseData()
    })
    this.schedulerRegistry.addCronJob(name, this.updateJob)
    Logger.log(`定时任务: ${name}, 开始执行`)
    this.updateJob.start()
  }

  /**
   * 处理 server 添加或更新事件
   * @param payload serverId
   */
  @OnEvent('servers.refresh')
  async handlerServerAddOrUpdateEvent(payload) {
    console.log('payload', payload)
    Logger.log('监听到 servers.refresh 事件')
    this.servers = serverDosToDtos(await this.serverService.findAll())
    let serverDto = serverDoToDto(await this.serverService.findOne(payload))
    let index = this.servers?.findIndex(s => s.id === payload)
    if (index > -1) {
      this.servers[index] = serverDto
    } else {
      this.servers.push(serverDto)
    }
    const { host, port = 22, username = 'root', password, key: privateKey } = serverDto
    SSHClient({ host, port, username, password, privateKey }, (conn) => {
        this.connServerMap.set(serverDto.id, conn)
      }
    )
  }

  /**
   * 监听客户端 start-servers-listener socket 事件
   * @param client
   * @param payload
   */
  @SubscribeMessage('start-servers-listener')
  handleMessage(client: any, payload: any) {
    Logger.log("监听到 start-servers-listener 事件")
    if (this.sendJob) return
    this.addSendCronJob('servers-info', '*/2 * * * * *')
    if (this.updateJob) return
    this.addUpdateCronJob('update-servers-info', '*/1 * * * * *')
  }

  async afterInit(server: any) {
    this.servers = serverDosToDtos(await this.serverService.findAll())
    this.createConnServerMap()
  }

  handleConnection(client: any, ...args: any[]): any {
    client.on('disconnect', (reason) => {
      // this.sendJob?.stop()
      // this.updateJob?.stop()
    })
  }
}
