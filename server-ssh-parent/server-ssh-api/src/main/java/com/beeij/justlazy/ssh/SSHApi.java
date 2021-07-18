package com.beeij.justlazy.ssh;

/**
 * SSHApi
 *
 * @author ye
 * @version 1.0.0, 2021-07-17
 * @since 2021-07-17 16:38
 */
public interface SSHApi {
    /**
     * 在服务器上执行命令
     * @param serverInfo 服务器信息
     * @param cmd 命令
     * @return Result
     */
    Result<?> execCommand(ServerInfo serverInfo, String cmd);
}
