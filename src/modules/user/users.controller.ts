import { Body, Controller, Get, Post, Req } from '@nestjs/common';
import { UsersService } from './users.service';
import { User } from './user.entity';

@Controller()
export class UsersController {
  constructor(private readonly usersService: UsersService) {}

  @Get('/users')
  getUsers(): Promise<User[]> {
    return this.usersService.findAll()
  }

  @Post('/user')
  getUser(@Body("id") id: string): Promise<User> {
    return this.usersService.findOne(id)
  }
}


