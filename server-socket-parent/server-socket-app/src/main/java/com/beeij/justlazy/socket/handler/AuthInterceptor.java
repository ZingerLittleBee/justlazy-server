package com.beeij.justlazy.socket.handler;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * WebSocket 认证拦截器
 *
 * @author ye
 * @version 1.0.0, 2021-07-04
 * @since 2021-07-04 10:51
 */
@Slf4j
@Component
public class AuthInterceptor implements HandshakeInterceptor {

    /**
     * 握手前
     *
     * @param request request
     * @param response response
     * @param wsHandler wsHandler
     * @param attributes attributes
     * @return boolean
     * @throws Exception Exception
     */
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        log.debug("开始握手");
        // 获得请求参数
        Map<String, String> paramMap = HttpUtil.decodeParamMap(request.getURI().getQuery(), StandardCharsets.UTF_8);
        String uid = paramMap.get("token");
        if (StrUtil.isNotBlank(uid)) {
            // TODO 验证 token 有效性
            // 放入属性域
            attributes.put("token", uid);
            log.info("请求握手, 用户{},  token: {}", request.getRemoteAddress(), uid);
            return true;
        }
        log.warn("无有效 token, 用户: {}", request.getRemoteAddress());
        return false;
    }

    /**
     * 握手后
     *
     * @param request request
     * @param response response
     * @param wsHandler wsHandler
     * @param exception exception
     */
    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
        log.debug("握手完成");
    }
}

