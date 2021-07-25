package com.beeij.justlazy.mysql.dao.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.beeij.justlazy.mysql.dao.mapper.UserMapper;
import com.beeij.justlazy.mysql.dao.model.UserDo;
import com.beeij.justlazy.mysql.dao.service.UserService;
import org.springframework.stereotype.Service;

/**
 * 描述
 *
 * @author ye
 * @version 1.0.0, 2021-05-29
 * @since 2021-05-29 18:15
 */

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserDo> implements UserService  {
}
