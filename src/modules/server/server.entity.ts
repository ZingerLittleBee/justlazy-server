import { Entity, Column } from 'typeorm';
import { BaseEntity } from '../../common/base.entity';

@Entity()
export class Server extends BaseEntity{
  constructor(name, host, key, port, username, password) {
    super();
    this.name = name
    this.host = host
    this.username = username
    this.port = port
    this.password = password
    this.key = key
  }

  @Column({ nullable: true })
  name: string;

  @Column({ unique: true })
  host: string

  @Column({ default: 22 })
  port: number

  @Column({ default: 'root' })
  username: string;

  @Column({ default: 'root' })
  password: string

  @Column({ default: '' })
  key: string
}
