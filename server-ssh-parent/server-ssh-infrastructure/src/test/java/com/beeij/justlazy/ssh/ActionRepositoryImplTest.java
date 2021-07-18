package com.beeij.justlazy.ssh;

import cn.hutool.core.lang.Assert;
import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.connection.ConnectionException;
import net.schmizz.sshj.connection.channel.direct.Session;
import net.schmizz.sshj.transport.TransportException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 描述
 *
 * @author ye
 * @version 1.0.0, 2021-07-17
 * @since 2021-07-17 14:02
 */
class ActionRepositoryImplTest {

    private ServerInfo serverInfo;

    private ActionRepositoryImpl actionRepository;

    private SSHClient sshClient;

    private Session.Command command;

    @BeforeEach
    void setUp() {
        System.out.println("ActionRepositoryImplTest 测试开始");
        serverInfo = new ServerInfo();
        serverInfo.setPassword("bAin4kPCngGH");
        serverInfo.setIp("122.112.247.15");
        actionRepository = new ActionRepositoryImpl();
    }

    @AfterEach
    void tearDown() {
        System.out.println("ActionRepositoryImplTest 测试结束");
    }

    @Test
    void createSshClient() {
        sshClient = actionRepository.createSshClient(serverInfo);
    }

    @Test
    void exec() {
        createSshClient();
        command = actionRepository.exec(sshClient, "pwd");
        System.out.println(actionRepository.read(command));
    }

    @Test
    void read() {
        String read = actionRepository.read(command);
        String replace = read.replace("\n", " ");
        System.out.println("read: " + replace);
    }

    @Test
    void close() throws TransportException, ConnectionException {
        command.close();
        Assert.isNull(command);
    }

    @Test
    void TestMap() {
        Map<String, ServerInfo> map = new HashMap<>();
        map.put("123", new ServerInfo());
        ServerInfo serverInfo = map.get("123");
        serverInfo.setIp("153.243.23");
        System.out.println(map.get("123"));
    }
}