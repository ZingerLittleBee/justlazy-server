import { HttpException, Injectable } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { Repository } from 'typeorm';
import { Server } from './server.entity';
import { EventEmitter2 } from '@nestjs/event-emitter';

@Injectable()
export class ServerService {
  constructor(
    @InjectRepository(Server)
    private serverRepository: Repository<Server>,
    private eventEmitter: EventEmitter2,
  ) {}

  findAll(): Promise<Server[]> {
    return this.serverRepository.find();
  }

  findOne(id: string): Promise<Server> {
    return this.serverRepository.findOne(id);
  }

  async create({ host, name, port, username, password, key }): Promise<void> {
    let server = new Server(name, host, key, port, username, password)
    try {
      await this.serverRepository.save(server);
      this.eventEmitter.emit('servers.refresh')
    }
    catch (e) {
      throw new HttpException(`添加失败, ${e.message}`, 400)
    }

  }

  async remove(id: string): Promise<void> {
    await this.serverRepository.delete(id);
  }

  // findByUserId(id: string[]): Promise<Server[]> {
  //   this.serverRepository.findOne()
  // }
}
