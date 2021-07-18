package com.beeij.justlazy.socket.domian;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 描述
 *
 * @author ye
 * @version 1.0.0, 2021-07-04
 * @since 2021-07-04 17:20
 */
@Slf4j
public class WsSessionManager {
    /**
     * 保存连接 session 的地方
     */
    private static final ConcurrentHashMap<String, WebSocketSession> SESSION_POOL = new ConcurrentHashMap<>();

    /**
     * SESSION_POOL 是否为空
     * @return boolean
     */
    public static boolean isEmpty() {
        return SESSION_POOL.isEmpty();
    }

    /**
     * 添加 session
     *
     * @param key key
     */
    public static void add(String key, WebSocketSession session) {
        // 添加 session
        SESSION_POOL.put(key, session);
    }

    /**
     * 删除 session,会返回删除的 session
     *
     * @param key key
     * @return WebSocketSession
     */
    public static WebSocketSession remove(String key) {
        // 删除 session
        return SESSION_POOL.remove(key);
    }

    /**
     * 删除并同步关闭连接
     *
     * @param key key
     */
    public static void removeAndClose(String key) {
        WebSocketSession session = remove(key);
        if (session != null) {
            try {
                // 关闭连接
                session.close();
            } catch (IOException e) {
                log.warn("WebSocket 连接关闭失败", e);
            }
        }
    }

    /**
     * 获得 session
     * @param key key
     * @return WebSocketSession
     */
    public static WebSocketSession get(String key) {
        // 获得 session
        return SESSION_POOL.get(key);
    }

    /**
     * 获取所有 session
     * @return List<WebSocketSession>
     */
    public static List<WebSocketSession> getAllSession() {
        return new ArrayList<>(SESSION_POOL.values());
    }
}
