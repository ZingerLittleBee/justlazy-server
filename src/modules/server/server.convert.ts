import { ServerDto } from './server.dto';
import { Server } from './server.entity';

export function serverDtoToDo(dto: ServerDto): Server {
  const { name, host, key, port, username, password } = dto
  return new Server(name, host, key, port, username, password)
}

export function serverDtosToDos(dtos: ServerDto[]): Server[] {
  let dos = []
  dtos.forEach(d => {
    dos.push(serverDtoToDo(d))
  })
  return dos
}

export function serverDoToDto(serverDo: Server): ServerDto {
  const { id, name, host, port, username, password, key } = serverDo
  return new ServerDto(id, name, host, port, username, password, key)
}

export function serverDosToDtos(serverDos: Server[]): ServerDto[] {
  let dtos = []
  serverDos.forEach(s => {
    dtos.push(serverDoToDto(s))
  })
  return dtos
}


