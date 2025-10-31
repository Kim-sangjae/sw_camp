package com.sangjae.chap04realtime.section02.longpolling;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class LongPollingNotificationService {

    private final Map<String, DeferredResult<String>> deferredResults;

    public void sendNotification(String userId, String message) {
        DeferredResult<String> result = deferredResults.get(userId); // key값

        if(result == null) return;

        boolean ok = result.setResult("새 알림 : " + message);
        if(!ok){
            deferredResults.remove(userId);
        }
    }




}
