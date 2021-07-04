package com.beeij.justlazy.ssh;

import cn.hutool.core.util.StrUtil;
import com.beeij.justlazy.ssh.domian.ActionRepository;
import com.beeij.justlazy.ssh.domian.ServerInfo;
import lombok.extern.slf4j.Slf4j;
import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.common.IOUtils;
import net.schmizz.sshj.connection.ConnectionException;
import net.schmizz.sshj.connection.channel.direct.Session;
import net.schmizz.sshj.transport.TransportException;
import net.schmizz.sshj.transport.verification.PromiscuousVerifier;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 描述
 *
 * @author ye
 * @version 1.0.0, 2021-07-04
 * @since 2021-07-04 22:34
 */

@Slf4j
public class ActionRepositoryImpl implements ActionRepository {
    private final Map<Session, SSHClient> map = new HashMap<>();

    @Override
    public Session startSession(ServerInfo serverInfo) {
        if (StrUtil.isNotBlank(serverInfo.getIp())) {
            throw new RuntimeException("ip 地址不能为空");
        }
        SSHClient sshClient = new SSHClient();
        if (StrUtil.isNotBlank(serverInfo.getSecretKey())) {
            // 调用加载密钥方法
        } else if (StrUtil.isNotBlank(serverInfo.getPassword())) {
            // 调用加载密码方法
            sshClient.addHostKeyVerifier(new PromiscuousVerifier());
            try {
                sshClient.connect(serverInfo.getIp());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            throw new RuntimeException("至少有密钥或者密码");
        }
        try {
            Session session = sshClient.startSession();
            map.put(session, sshClient);
            return session;
        } catch (ConnectionException | TransportException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Session.Command exec(Session session, String cmd) {
        Session.Command command = null;
        try {
            command = session.exec(cmd);
        } catch (ConnectionException | TransportException e) {
            e.printStackTrace();
        }
        return command;
    }

    @Override
    public String read(Session.Command cmd) {
        try {
            InputStream inputStream = cmd.getInputStream();
            ByteArrayOutputStream byteArrayOutputStream1 = new ByteArrayOutputStream();
            int temp = 0;
            while ((temp = inputStream.read()) != -1) {
                if (temp == '\n') {
                    System.out.println(byteArrayOutputStream1.toString());
                    byteArrayOutputStream1.reset();
                }
                byteArrayOutputStream1.write(temp);
            }
            ByteArrayOutputStream byteArrayOutputStream = IOUtils.readFully(inputStream);
            System.out.println(byteArrayOutputStream.toString());
            cmd.join(5, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void close(Session session) {
        SSHClient sshClient = map.get(session);
        try {
            session.close();
        } catch (TransportException | ConnectionException e) {
            e.printStackTrace();
        }
        try {
            sshClient.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
