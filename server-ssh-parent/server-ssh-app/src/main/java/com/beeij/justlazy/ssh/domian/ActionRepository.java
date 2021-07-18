package com.beeij.justlazy.ssh.domian;

import com.beeij.justlazy.ssh.ServerInfo;
import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.connection.channel.direct.Session;

/**
 * ssh 功能接口
 *
 * @author ye
 * @version 1.0.0, 2021-07-04
 * @since 2021-07-04 22:21
 */
public interface ActionRepository {

    /**
     * 获取连接 ssh 会话
     * @param serverInfo 需要 ssh 的服务器信息
     * @return SSHClient
     */
    SSHClient createSshClient(ServerInfo serverInfo);

    /**
     * 获取执行 cmd
     * @param sshClient sshClient
     * @param cmd 执行的命令
     * @return cmd 句柄
     */
    Session.Command exec(SSHClient sshClient, String cmd);

    /**
     * 执行命令
     * @param cmd cmd
     * @return string
     */
    String read(Session.Command cmd);

    /**
     * 关闭 ssh session
     * @param session session
     */
    void closeSession(Session session);

    /**
     * 关闭 ssh 连接
     * @param sshClient sshClient
     */
    void disconnectClient(SSHClient sshClient);
}
