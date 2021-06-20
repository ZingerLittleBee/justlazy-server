import { Body, Controller, Get, Param, Post, Req } from '@nestjs/common';
import { ServerService } from './server.service';
import { Server } from './server.entity';
import { ServerVo } from './server.vo';

@Controller()
export class ServerController {
  constructor(private readonly serverService: ServerService) {}

  @Get('/servers')
  async getServers(): Promise<ServerVo[]> {
    let servers = await this.serverService.findAll()
    let serverVos = []
    servers.forEach(s => {
      serverVos.push(new ServerVo(s.id, s.name, s.host))
    })
    return serverVos
  }

  @Get('/server/:id')
  getServer(@Param("id") id: string): Promise<Server> {
    console.log('id', id)
    return this.serverService.findOne(id)
  }

  @Post('/server')
  createServer(@Body() serverInfo): Promise<void> {
    console.log('serverInfo', serverInfo)
    return this.serverService.create(serverInfo)
  }
}
