package com.sangjae.chap04realtime.section03.sse;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class FriendRequest {
    private final String fromUser;
    private final String toUser;
}