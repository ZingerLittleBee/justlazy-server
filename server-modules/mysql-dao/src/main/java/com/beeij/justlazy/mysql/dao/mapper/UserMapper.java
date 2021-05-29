package com.beeij.justlazy.mysql.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.beeij.justlazy.mysql.dao.model.UserDo;
import org.apache.ibatis.annotations.Mapper;

/**
 * 描述
 *
 * @author ye
 * @version 1.0.0, 2021-05-29
 * @since 2021-05-29 18:14
 */
@Mapper
public interface UserMapper extends BaseMapper<UserDo> {
}
