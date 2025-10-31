package com.sangjae.chap04realtime.section02.longpolling;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.Map;

@RestController
@RequestMapping("/long-polling")
@RequiredArgsConstructor
public class LongPollingConnectController {

    private final Map<String,DeferredResult<String>> deferredResultMap;

    @GetMapping("/connect/{userId}")
    public DeferredResult<String> connect(@PathVariable String userId){
        // 응답시간을 정해놓고 응답할 것이 있으면! 응답
        // 여기선 30초로 설정
        // 30초가 지나도 응답할게 없으면 따로 처리하는 메서드로 제어 가능
        DeferredResult<String> result = new DeferredResult<>(30000L);

        deferredResultMap.put(userId,result);
        // 시간이 지나도 응답할게 없을경우 .onTimeout
        result.onTimeout(()->result.setResult("알림 없음 (타임아웃)"));
        // 응답 시간이 지나면 데이터 삭제
        result.onCompletion(()-> deferredResultMap.remove(userId));

        return result;
    }

}
