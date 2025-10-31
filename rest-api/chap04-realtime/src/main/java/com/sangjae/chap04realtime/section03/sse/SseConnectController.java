package com.sangjae.chap04realtime.section03.sse;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/sse")
@RequiredArgsConstructor
public class SseConnectController {

    private final Map<String, SseEmitter> sseEmitters;

    /* 클라이언트가 연결 요청할 때 호출되는 엔드포인트
     * 클라이언트는 고유 ID를 가지고 있어야 함 (ex. 사용자 ID) */
    @GetMapping(value = "/connect/{clientId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter connect(@PathVariable String clientId) {
        SseEmitter emitter = new SseEmitter(60 * 1000L); // 1분 유지
        sseEmitters.put(clientId, emitter);

        emitter.onTimeout(() -> {
            try {
                emitter.send(SseEmitter.event()
                        .name("timeout")
                        .data("연결 유지 시간 초과"));
            } catch (IOException ignored) {}
            emitter.complete();  // 이 호출로 onCompletion() 자동 실행됨
        });

        emitter.onCompletion(() -> sseEmitters.remove(clientId));
        emitter.onError(e -> sseEmitters.remove(clientId));

        try {
            emitter.send(SseEmitter.event().name("connect").data("연결 성공"));
        } catch (IOException e) {
            emitter.completeWithError(e);
        }

        return emitter;
    }
}