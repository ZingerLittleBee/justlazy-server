import { Controller, Get } from '@nestjs/common';
import { AppService } from './app.service';
import { Client } from 'ssh2'
import { execCmd, SSHClient } from './ssh/SSHClient';
import { ServerService } from './modules/server/server.service';

@Controller()
export class AppController {
  constructor(private readonly appService: AppService) {}

  @Get()
  getHello(): string {
    const serverConfig = {
      host: '122.112.247.15',
      port: 22,
      username: 'root',
      password: '4@w!YrWKRjKNsU'
    }
    SSHClient(serverConfig, async (conn: Client) => {
      let data = await execCmd(conn, 'ls')
      console.log('data', data.toString())
    })

    // const conn = new Client();
    // conn.on('ready', () => {
    //   console.log('Client :: ready');
    //   conn.exec('uptime', (err, stream) => {
    //     if (err) throw err;
    //     stream.on('close', (code, signal) => {
    //       console.log('Stream :: close :: code: ' + code + ', signal: ' + signal);
    //     }).on('data', (data) => {
    //       console.log('STDOUT: ' + data);
    //     }).stderr.on('data', (data) => {
    //       console.log('STDERR: ' + data);
    //     });
    //   });
    // }).connect({
    //   host: '122.112.247.15',
    //   port: 22,
    //   username: 'root',
    //   password: '4@w!YrWKRjKNsU'
    // });
    return this.appService.getHello();
  }
}
