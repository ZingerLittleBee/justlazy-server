package com.beeij.justlazy.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * 描述
 *
 * @author ye
 * @version 1.0.0, 2021-07-04
 * @since 2021-07-04 09:52
 */
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Autowired
    private HttpAuthHandler httpAuthHandler;
    @Autowired
    private MyInterceptor myInterceptor;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry
                .addHandler(httpAuthHandler, "myWS")
                .addInterceptors(myInterceptor)
                .setAllowedOrigins("*");
    }
}