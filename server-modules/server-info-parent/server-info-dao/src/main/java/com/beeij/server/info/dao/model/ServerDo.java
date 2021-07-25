package com.beeij.server.info.dao.model;

import com.beeij.justlazy.mysql.common.SuperModel;

/**
 * 描述
 *
 * @author ye
 * @version 1.0.0, 2021-07-25
 * @since 2021-07-25 22:57
 */
public class ServerDo extends SuperModel {
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
