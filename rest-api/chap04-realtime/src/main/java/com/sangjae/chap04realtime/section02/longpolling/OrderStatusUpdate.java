package com.sangjae.chap04realtime.section02.longpolling;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class OrderStatusUpdate {
    private final String userId;
    private final String status;
}
