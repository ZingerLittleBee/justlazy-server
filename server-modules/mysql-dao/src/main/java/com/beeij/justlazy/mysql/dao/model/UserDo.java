package com.beeij.justlazy.mysql.dao.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 描述
 *
 * @author ye
 * @version 1.0.0, 2021-05-29
 * @since 2021-05-29 18:06
 */

@Data
@TableName("user")
@EqualsAndHashCode(callSuper = true)
public class UserDo extends SuperModel{

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 昵称
     */
    private String name;
}
