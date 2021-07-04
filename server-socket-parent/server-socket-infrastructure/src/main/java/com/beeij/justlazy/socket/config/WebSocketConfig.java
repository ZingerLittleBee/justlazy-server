package com.beeij.justlazy.socket.config;

import com.beeij.justlazy.socket.constant.WebSocketConstant;
import com.beeij.justlazy.socket.handler.AuthInterceptor;
import com.beeij.justlazy.socket.handler.DashboardHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * WebSocketConfig
 *
 * @author ye
 * @version 1.0.0, 2021-07-04
 * @since 2021-07-04 09:52
 */
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final DashboardHandler dashboardHandler;
    private final AuthInterceptor authInterceptor;

    @Autowired
    public WebSocketConfig(DashboardHandler dashboardHandler, AuthInterceptor authInterceptor) {
        this.dashboardHandler = dashboardHandler;
        this.authInterceptor = authInterceptor;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry
                .addHandler(dashboardHandler, WebSocketConstant.DashboardChannel)
                .addInterceptors(authInterceptor)
                .setAllowedOrigins("*");
    }
}