package com.sangjae.chap04realtime.section04.websocket;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessage {
    private String roomId;
    private String sender;
    private String content;
}
