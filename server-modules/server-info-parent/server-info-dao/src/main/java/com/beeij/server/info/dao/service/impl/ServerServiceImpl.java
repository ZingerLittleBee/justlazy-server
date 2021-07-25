package com.beeij.server.info.dao.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.beeij.server.info.dao.mapper.ServerMapper;
import com.beeij.server.info.dao.model.ServerDo;
import com.beeij.server.info.dao.service.ServerService;
import org.springframework.stereotype.Service;

/**
 * 描述
 *
 * @author ye
 * @version 1.0.0, 2021-07-25
 * @since 2021-07-25 22:57
 */
@Service
public class ServerServiceImpl extends ServiceImpl<ServerMapper, ServerDo> implements ServerService{
}
