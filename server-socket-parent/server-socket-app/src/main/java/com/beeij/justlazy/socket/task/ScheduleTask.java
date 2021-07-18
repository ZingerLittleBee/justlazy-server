package com.beeij.justlazy.socket.task;

import com.beeij.justlazy.socket.domian.WsSessionManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.List;


/**
 * 描述
 *
 * @author ye
 * @version 1.0.0, 2021-07-18
 * @since 2021-07-18 22:56
 */
@Slf4j
@Component
@EnableAsync
public class ScheduleTask {

    @Async("dashboardAsync")
    @Scheduled(fixedRate = 1000)
    public void dashboardScheduleTask() {
        if (!WsSessionManager.isEmpty()) {
            // TODO 获取所有机器 dashboard 信息
            List<WebSocketSession> allSession = WsSessionManager.getAllSession();
            allSession.forEach(a -> {});
        }
    }
}