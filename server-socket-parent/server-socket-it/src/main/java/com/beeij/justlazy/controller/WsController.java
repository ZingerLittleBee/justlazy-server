package com.beeij.justlazy.controller;

import org.springframework.stereotype.Controller;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 描述
 *
 * @author ye
 * @version 1.0.0, 2021-07-04
 * @since 2021-07-04 09:24
 */

@ServerEndpoint(value = "/message_websocket")
@Controller
public class WsController {

    final ConcurrentHashMap<Object, Session> sessionPool = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session) throws IOException {
        System.out.println("session open. ID:" + session.getId());
        sessionPool.put(session.getId(), session);
        session.getBasicRemote().sendText("Connect Success");

        new Thread(() -> {
            Session session1 = sessionPool.get(session.getId());
            while (session1.isOpen()) {
                try {
                        session1.getBasicRemote().sendText("you sessionis is" + session1.getId());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(Session session) {
        System.out.println("session close. ID:" + session.getId());
    }

    /**
     * 收到客户端消息后调用的方法
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("get client msg. ID:" + session.getId() + ". msg:" + message);
    }

    /**
     * 发生错误时调用
     */
    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }

}