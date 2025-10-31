package com.sangjae.chap04realtime.section05.stomp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessage {
    private String roomId;
    private String sender;
    private String content;
    private MessageType type;
}
