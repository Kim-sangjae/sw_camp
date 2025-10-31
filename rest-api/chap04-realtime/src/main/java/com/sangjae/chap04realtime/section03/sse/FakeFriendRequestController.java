package com.sangjae.chap04realtime.section03.sse;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sse/friend-request")
public class FakeFriendRequestController {

    private final SseNotificationService sseNotificationService;

    /* 테스트용: 친구 요청 시뮬레이션 */
    @PostMapping("/send")
    public ResponseEntity<String> sendFriendRequest(@RequestBody FriendRequest request) {

        String message = request.getFromUser() + "님이 친구 요청을 보냈습니다.";
        sseNotificationService.sendNotification(request.getToUser(), message);

        return ResponseEntity.ok("친구 요청 전송 완료 (가상)");
    }
}