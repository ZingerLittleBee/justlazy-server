package com.beeij.justlazy.ssh;

import cn.hutool.core.lang.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * 描述
 *
 * @author ye
 * @version 1.0.0, 2021-07-17
 * @since 2021-07-17 22:35
 */
class SSHApiImplTest {
    private SSHApi sshApi;

    private ServerInfo serverInfo;

    @BeforeEach
    void setUp() {
        System.out.println("SSHApiImplTest 测试开始");
        serverInfo = new ServerInfo();
        serverInfo.setPassword("bAin4kPCngGH");
        serverInfo.setIp("122.112.247.15");
        sshApi = new SSHApiImpl(new ActionRepositoryImpl());
    }

    @AfterEach
    void tearDown() {
        System.out.println("SSHApiImplTest 测试结束");
    }

    @Test
    void execCommand() {
        Result<?> ls = sshApi.execCommand(serverInfo, "ls");
        Assert.isTrue(ls.okOrNot());
        Assert.isTrue(ls.hasData());
        ServerInfo serverInfo = new ServerInfo();
        serverInfo.setIp("122.112.247.15");
        Result<?> ls2 = sshApi.execCommand(serverInfo, "ls2");
        Assert.isTrue(ls2.okOrNot());

        for (int i = 0; i < 1000; i++) {
            System.out.println(sshApi.execCommand(serverInfo, "ls").getData());
        }
    }
}
