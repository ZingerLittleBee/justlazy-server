package com.beeij.justlazy.ssh;

import cn.hutool.core.util.StrUtil;
import com.beeij.justlazy.ssh.domian.ActionRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.connection.channel.direct.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 描述
 *
 * @author ye
 * @version 1.0.0, 2021-07-17
 * @since 2021-07-17 16:41
 */

@Slf4j
@Service
public class SSHApiImpl implements SSHApi {

    private final Map<String, SshInfo> map;

    private final ActionRepository actionRepository;

    @Getter
    @Setter
    @AllArgsConstructor
    private static class SshInfo {
        private ServerInfo serverInfo;
        private SSHClient sshClient;
    }

    @Autowired
    public SSHApiImpl(ActionRepository actionRepository) {
        this.map = new ConcurrentHashMap<>();
        this.actionRepository = actionRepository;
    }

    @Override
    public Result<?> execCommand(ServerInfo serverInfo, String cmd) {
        return Result.OkResult(execCommand(createSshClient(serverInfo), cmd));
    }

    private SSHClient createSshClient(ServerInfo serverInfo) {
        if (StrUtil.isEmpty(serverInfo.getIp())) {
            log.error("ip 为空, {}", serverInfo);
            throw new IllegalArgumentException("ip 为空");
        }
        SshInfo sshInfo = map.computeIfAbsent(
                serverInfo.getIp(),
                key -> {
                    SSHClient sshClient = actionRepository.createSshClient(serverInfo);
                    return new SshInfo(serverInfo, sshClient);
                }
        );
        if (!serverInfo.equals(sshInfo.getServerInfo())) {
            if (serverInfo.getPassword() != null || serverInfo.getSecretKey() != null) {
                sshInfo.setSshClient(actionRepository.createSshClient(serverInfo));
                sshInfo.setServerInfo(serverInfo);
            }
        }
        return sshInfo.getSshClient();
    }

    private String execCommand(SSHClient sshClient, String cmd) {
        Session.Command exec = actionRepository.exec(sshClient, cmd);
        return actionRepository.read(exec);
    }
}
