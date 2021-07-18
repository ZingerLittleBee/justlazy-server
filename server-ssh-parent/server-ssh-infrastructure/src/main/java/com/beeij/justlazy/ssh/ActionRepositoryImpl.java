package com.beeij.justlazy.ssh;

import cn.hutool.core.util.StrUtil;
import com.beeij.justlazy.ssh.domian.ActionRepository;
import lombok.extern.slf4j.Slf4j;
import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.common.IOUtils;
import net.schmizz.sshj.connection.ConnectionException;
import net.schmizz.sshj.connection.channel.direct.Session;
import net.schmizz.sshj.transport.TransportException;
import net.schmizz.sshj.transport.verification.PromiscuousVerifier;
import net.schmizz.sshj.userauth.UserAuthException;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * 描述
 *
 * @author ye
 * @version 1.0.0, 2021-07-04
 * @since 2021-07-04 22:34
 */

@Slf4j
@Component
public class ActionRepositoryImpl implements ActionRepository {

    @Override
    public SSHClient createSshClient(ServerInfo serverInfo) {
        if (!StrUtil.isNotBlank(serverInfo.getIp())) {
            log.error("ip 地址为空: {}", serverInfo);
            throw new RuntimeException("ip 地址为空");
        }
        SSHClient sshClient = new SSHClient();
        if (StrUtil.isNotBlank(serverInfo.getSecretKey())) {
            // 调用加载密钥方法
            log.debug("ssh 验证方式为密钥");
        } else if (StrUtil.isNotBlank(serverInfo.getPassword())) {
            // 调用加载密码方法
            log.debug("ssh 验证方式为密码");
            sshClient.addHostKeyVerifier(new PromiscuousVerifier());
            try {
                sshClient.connect(serverInfo.getIp());
            } catch (IOException e) {
                log.error("连接服务器失败", e);
            }
            try {
                sshClient.authPassword(serverInfo.getUsername(), serverInfo.getPassword());
            } catch (UserAuthException | TransportException e) {
                log.error("认证服务器失败", e);

            }
        } else {
            throw new RuntimeException("密钥和密码至少有其一");
        }
        return sshClient;
    }

    @Override
    public Session.Command exec(SSHClient sshClient, String cmd) {
        Session session = null;
        Session.Command command = null;
        try {
            session = sshClient.startSession();
            command = session.exec(cmd);
        } catch (ConnectionException | TransportException e) {
            log.error("exec 异常", e);
        } finally {
            if (session != null) {
                closeSession(session);
            }
        }
        return command;
    }

    @Override
    public String read(Session.Command cmd) {
            InputStream inputStream = cmd.getInputStream();
//            分段读取
//            ByteArrayOutputStream byteArrayOutputStream1 = new ByteArrayOutputStream();
//            int temp = 0;
//            while ((temp = inputStream.read()) != -1) {
//                if (temp == '\n') {
////                    System.out.println(byteArrayOutputStream1.toString());
//                    byteArrayOutputStream1.reset();
//                }
//                byteArrayOutputStream1.write(temp);
//            }
        ByteArrayOutputStream byteArrayOutputStream = null;
        try {
            byteArrayOutputStream = IOUtils.readFully(inputStream);
        } catch (IOException e) {
            log.error("读取输入流失败", e);
        }
        return byteArrayOutputStream != null ? byteArrayOutputStream.toString() : null;
    }

    @Override
    public void closeSession(Session session) {
        try {
            session.close();
        } catch (TransportException | ConnectionException e) {
            log.error("ssh session 关闭失败", e);
        }
    }

    @Override
    public void disconnectClient(SSHClient sshClient) {
        try {
            sshClient.disconnect();
        } catch (IOException e) {
            log.error("ssh client 断开失败", e);
        }
    }
}
