import {
  OnGatewayConnection,
  SubscribeMessage,
  WebSocketGateway,
  WebSocketServer,
} from '@nestjs/websockets';
import { Server } from 'socket.io';
import { OnGatewayInit } from '@nestjs/websockets/interfaces/hooks/on-gateway-init.interface';
import { ServerService } from './modules/server/server.service';
import { ServerDto } from './modules/server/server.dto';
import { OnEvent } from '@nestjs/event-emitter';
import { Logger } from '@nestjs/common';
import { SchedulerRegistry } from '@nestjs/schedule';
import { CronJob } from 'cron';
import { execCmd, SSHClient } from './ssh/SSHClient';
import { shellAllInOne } from './ssh/Command';
import { serverDosToDtos } from './modules/server/server.convert';

@WebSocketGateway(9527, { namespace: 'servers' })
export class EventsGateway implements OnGatewayInit, OnGatewayConnection{
  constructor(
    private readonly serverService: ServerService,
    private schedulerRegistry: SchedulerRegistry
  ) {}

  servers: ServerDto[]

  conns = []

  @WebSocketServer()
  server: Server;

  job;

  addCronJob(name: string, cron: string) {
     this.job = new CronJob(cron, () => {
      this.server.emit('servers-message', 'hello client1')
    });
    this.schedulerRegistry.addCronJob(name, this.job);
    Logger.log(`定时任务: ${name}, 开始执行`)
    this.job.start();
  }

  @OnEvent('servers.refresh')
  async handleOrderCreatedEvent() {
    Logger.log('监听到 servers.refresh 事件')
    this.servers = serverDosToDtos(await this.serverService.findAll())
  }

  @SubscribeMessage('start-servers-listener')
  handleMessage(client: any, payload: any) {
    if (!this.job) return
    this.addCronJob('servers-message', '*/2 * * * * *')
  }


  async afterInit(server: any) {
    this.servers = serverDosToDtos(await this.serverService.findAll())
    console.log('servers', this.servers)
    if (this.servers?.length != 0) {
      let resData = {}
      this.servers.forEach(s => {
        const { host, port = 22, username = 'root', password, key: privateKey } = s
        SSHClient({
          host, port, username, password, privateKey
        }, (conn) => {
          this.conns.push(conn)
          this.conns.forEach((c, index) => {
            execCmd(c, shellAllInOne()).then((res: any) => {
              let infoStr = ''
              res.subscribe({
                next: x => {
                  let removeLineFeed  = x.replace(/[\r\n]/g,"")
                  if (infoStr) {
                    infoStr = infoStr + ',' + removeLineFeed
                  } else {
                    infoStr = removeLineFeed
                  }
                },
                error: err => Logger.error('Observer got an error: ' + err),
                complete: () => {
                  Logger.log('Observer got a complete notification')
                  infoStr = `{${infoStr}}`.replace(/([A-Za-z]+)(?=:)/g, '"$1"')
                  console.log('infoStr', infoStr)
                  resData[s.id] = JSON.parse(infoStr)
                  if (index === this.conns.length - 1) {
                    this.server.emit('servers-message', resData)
                    Logger.log('发送 server-message 消息')
                    console.log(resData)
                    resData = {}
                  }
                }
              })
            })
          })
        })
      })
    }
  }

  handleConnection(client: any, ...args: any[]): any {
  }
}
