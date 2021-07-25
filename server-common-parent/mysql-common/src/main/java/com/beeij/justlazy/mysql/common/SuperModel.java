package com.beeij.justlazy.mysql.common;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * 描述
 *
 * @author ye
 * @version 1.0.0, 2021-05-29
 * @since 2021-05-29 18:04
 */
@Getter
@Setter
public abstract class SuperModel {

    /**
     * 数据库主键
     */
    private Integer id;

    /**
     * 创建时间
     */

    @DateTimeFormat(pattern="yyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @DateTimeFormat(pattern="yyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    /**
     * 删除时间
     */
    @DateTimeFormat(pattern="yyy-MM-dd HH:mm:ss")
    private LocalDateTime deleteTime;
}
