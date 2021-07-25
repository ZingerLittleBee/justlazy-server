package com.beeij.server.info.api;

import java.util.List;

/**
 * ServerInfoApi
 *
 * @author ye
 * @version 1.0.0, 2021-07-25
 * @since 2021-07-25 22:28
 */
public interface ServerInfoApi {

    /**
     * 获取所有 server 信息
     * @return List<ServerDto>
     */
    List<ServerDto> getAllServer();
}
