package com.sangjae.chap04realtime.section02.longpolling;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/long-polling/order-status")
public class FakeOrderStatusController {

    private final LongPollingNotificationService longPollingNotificationService;

    // 테스트용 : 주문 상태 변경 시뮬레이션
    @PostMapping("/update")
    public ResponseEntity<String> updateOrderStatus(@RequestBody OrderStatusUpdate update){

        String message = update.getUserId() + "님의 주문이 [" + update.getStatus() + "] 상태로 변경되었습니다";

        longPollingNotificationService.sendNotification(update.getUserId(),message);

        return ResponseEntity.ok("주문 상태 변경 알림 전송 완료 (가상) ");
    }

}
