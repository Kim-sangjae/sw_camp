package com.sangjae.chap04realtime.section05.stomp;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class StompChatController {

    private final StompChatService chatService;

    /* 사용자가 채팅방에 입장할 때 호출하는 메시지 핸들러
     * 클라이언트는 /app/chat/join로 메시지를 전송 */
    @MessageMapping("/chat/join")
    public void join(ChatMessage message) {
        chatService.joinRoom(message.getRoomId(), message.getSender());
    }

    /* 사용자가 채팅방을 퇴장할 때 호출하는 메시지 핸들러 */
    @MessageMapping("/chat/leave")
    public void leave(ChatMessage message) {
        chatService.leaveRoom(message.getRoomId(), message.getSender());
    }

    /* 일반 채팅 메시지 전송 핸들러 */
    @MessageMapping("/chat/send")
    public void send(ChatMessage message) {
        message.setType(MessageType.CHAT);
        chatService.sendChatMessage(message);
    }
}
