package com.beeij.justlazy.socket.handler;

import com.beeij.justlazy.socket.domian.WsSessionManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.time.LocalDateTime;

/**
 * DashboardHandler
 *
 * @author ye
 * @version 1.0.0, 2021-07-04
 * @since 2021-07-04 17:11
 */

@Slf4j
@Component
public class DashboardHandler extends TextWebSocketHandler {
    /**
     * socket 建立成功事件
     *
     * @param session session
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        Object token = session.getAttributes().get("token");
        log.info("WebSocket 连接已建立, uri: {}, token: {}", session.getUri(), token);
        if (token != null) {
            // 用户连接成功，放入在线用户缓存
            WsSessionManager.add(token.toString(), session);
        } else {
            log.error("用户登录已经失效!");
        }
        // 开启发送 Dashboard 信息线程
    }

    /**
     * 接收消息事件
     *
     * @param session session
     * @param message message
     * @throws Exception Exception
     */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // 获得客户端传来的消息
        String payload = message.getPayload();
        Object token = session.getAttributes().get("token");
        log.debug("server 接收到 {}, 发送的 {}", token, payload);
        session.sendMessage(new TextMessage("server 发送给 " + token + " 消息 " + payload + " " + LocalDateTime.now()));
    }

    /**
     * socket 断开连接时
     *
     * @param session session
     * @param status status
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        Object token = session.getAttributes().get("token");
        if (token != null) {
            // 用户退出，移除缓存
            log.debug("用户: {}, 退出连接", token);
            WsSessionManager.remove(token.toString());
        }
    }
}
