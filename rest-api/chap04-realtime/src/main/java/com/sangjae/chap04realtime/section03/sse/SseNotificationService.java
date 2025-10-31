package com.sangjae.chap04realtime.section03.sse;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SseNotificationService {

    private final Map<String, SseEmitter> clients;

    public void sendNotification(String clientId, String message) {
        SseEmitter emitter = clients.get(clientId);
        if (emitter != null) {
            try {
                emitter.send(SseEmitter.event()
                        .name("notification")
                        .data("새 알림: " + message));
            } catch (IOException e) {
                clients.remove(clientId);
                emitter.completeWithError(e);
            }
        }
    }
}