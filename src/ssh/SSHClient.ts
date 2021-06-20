import { Client, ConnectConfig } from 'ssh2';
import { Logger } from '@nestjs/common'
import { Observable } from 'rxjs';

const execCmd = (conn: Client, cmd: string) => {
  let foo
  return new Promise((resolve, reject) => {
    conn.exec(cmd, (err, stream) => {
      if (err) throw err;
      stream.on('close', (code, signal) => {
        Logger.warn(`Stream :: close :: code: ${code}, signal: ${signal}`)
        console.log();
      })
      resolve(new Observable(subscriber => {
        stream.on('data', (data) => {
          subscriber.next(data.toString());
          if (data.toString().split(':')[0] === 'ROM') {
            subscriber.complete()
          }
        })
      }))
      stream.stderr.on('data', (data) => {
        reject(data)
        Logger.error(`${cmd},执行出错: ${data}`)
      });
    });
  })
}


const SSHClient = (option: ConnectConfig, cb: Function) => {
  const conn = new Client();
  conn.on('ready', () => {
    console.log('Client :: ready');
    cb(conn)
  })
  conn.connect(option);
}

export {
  execCmd,
  SSHClient
}
