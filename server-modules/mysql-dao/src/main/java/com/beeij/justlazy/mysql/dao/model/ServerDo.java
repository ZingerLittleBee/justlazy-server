package com.beeij.justlazy.mysql.dao.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 服务器信息实体
 *
 * @author ye
 * @version 1.0.0, 2021-05-29
 * @since 2021-05-29 18:07
 */

@Data
@TableName("server")
@EqualsAndHashCode(callSuper = true)
public class ServerDo extends SuperModel{

    /**
     * 别名
     */
    private String nickName;

    /**
     * ssh 用户名
     */
    private String username;

    /**
     * ssh 密码
     */
    private String password;

    /**
     * 密钥
     */
    private String key;


    /**
     * 服务器状态 0 不可用, 1 在线, 2 关机
     */
    private Integer status;


}
