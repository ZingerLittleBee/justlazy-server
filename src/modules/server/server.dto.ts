import { IsString, IsNotEmpty } from 'class-validator'


export class ServerDto{
  constructor(id, name, host, port, username, password, key) {
    this.id = id
    this.name = name
    this.host = host
    this.port = port
    this.username = username
    this.password = password
    this.key = key
  }

  @IsString()
  readonly id: string;

  @IsString()
  readonly name: string;

  @IsNotEmpty()
  readonly host: string

  readonly port: number

  readonly username: string;

  readonly password: string

  readonly key: string
}
