import { Entity, Column, PrimaryGeneratedColumn } from 'typeorm';
import { BaseEntity } from '../../common/base.entity';

@Entity()
export class User extends BaseEntity{
  @Column()
  name: string;
}
