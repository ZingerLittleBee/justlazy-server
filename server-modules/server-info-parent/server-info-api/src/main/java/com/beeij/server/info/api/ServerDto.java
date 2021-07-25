package com.beeij.server.info.api;

import lombok.Data;

/**
 * ServerDto
 *
 * @author ye
 * @version 1.0.0, 2021-07-25
 * @since 2021-07-25 22:29
 */

@Data
public class ServerDto {

    /**
     * id
     */
    String id;
    /**
     * ip
     */
    String ip;

    /**
     * 端口号
     */
    int port;

    /**
     * 用户名
     */
    String username;

    /**
     * 密码
     */
    String password;

    /**
     * 密钥
     */
    String secretKey;

    /**
     * 是否可用
     */
    Boolean enable;
}
