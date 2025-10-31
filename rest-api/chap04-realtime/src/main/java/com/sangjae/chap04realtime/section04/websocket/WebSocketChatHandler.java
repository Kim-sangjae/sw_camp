package com.sangjae.chap04realtime.section04.websocket;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
@RequiredArgsConstructor
// WebSocket 연결을 처리하고 메시지를 관리하는 역할
public class WebSocketChatHandler extends TextWebSocketHandler {

    private final WebSocketChatService chatService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    // 클라이언트로부터 받은 텍스트 메시지를 처리하는 메소드 (메시지의 내용을 파싱하여 입장, 퇴장, 일반 메시지 등의 처리를 수행)
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        ChatMessage chatMessage = objectMapper.readValue(message.getPayload(), ChatMessage.class);

        // 입장 처리
        if ("[입장]".equals(chatMessage.getContent())) {
            chatService.joinRoom(chatMessage.getRoomId(), session, chatMessage.getSender());
            return;
        }

        // 퇴장 처리
        if ("[퇴장]".equals(chatMessage.getContent())) {
            // 연결을 닫아 afterConnectionClosed 로직을 유도
            session.close();
            return;
        }

        // 일반 메시지 처리
        String formatted = "[" + chatMessage.getSender() + "] " + chatMessage.getContent();
        chatService.sendToRoom(chatMessage.getRoomId(), formatted);
    }

    // WebSocket 연결이 종료된 후 호출되는 메소드 (종료된 세션을 채팅방에서 제거하고, 필요에 따라 퇴장 알림 메시지를 전송하여 다른 사용자에게 통보)
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        // 세션의 속성에서 roomId를 가져와 해당 방에서 퇴장 처리
        String roomId = (String) session.getAttributes().get("roomId");
        if (roomId != null) {
            chatService.leaveRoom(roomId, session);
        }
    }
}
