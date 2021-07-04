package com.beeij.justlazy.ssh.domian;

import net.schmizz.sshj.connection.channel.direct.Session;

/**
 * 描述
 *
 * @author ye
 * @version 1.0.0, 2021-07-04
 * @since 2021-07-04 22:21
 */
public interface ActionRepository {
    /**
     * 获取连接 ssh 会话
     * @param serverInfo 需要 ssh 的服务器信息
     * @return 连接会话
     */
    Session startSession(ServerInfo serverInfo);

    /**
     * 获取执行 cmd
     * @param session 执行命令的会话
     * @param cmd 执行的命令
     * @return cmd 句柄
     */
    Session.Command exec(Session session, String cmd);

    String read(Session.Command cmd);

    void close(Session session);
}
