package com.sangjae.chap04realtime.section04.websocket;

import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
// 비즈니스 로직을 처리하여 실시간 채팅 기능을 지원
public class WebSocketChatService {
    // 메모리 기반 채팅방: roomId -> 세션 목록
    private final Map<String, Set<WebSocketSession>> chatRooms = new HashMap<>();

    public void joinRoom(String roomId, WebSocketSession session, String sender) {
        chatRooms.computeIfAbsent(roomId, k -> new HashSet<>()).add(session);

        // 사용자 이름 저장
        session.getAttributes().put("sender", sender);
        // 채팅방 아이디도 세션 속성에 저장 (퇴장 처리 시 사용)
        session.getAttributes().put("roomId", roomId);

        // 시스템 메시지 전송
        sendToRoom(roomId, "[시스템] " + sender + "님이 입장했습니다.");
    }

    public void sendToRoom(String roomId, String message) {
        Set<WebSocketSession> sessions = chatRooms.get(roomId);
        if (sessions == null) return;

        sessions.removeIf(s -> !s.isOpen()); // 끊긴 세션 제거
        for (WebSocketSession session : sessions) {
            try {
                session.sendMessage(new TextMessage(message));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void leaveRoom(String roomId, WebSocketSession session) {
        Set<WebSocketSession> sessions = chatRooms.get(roomId);
        if (sessions != null && sessions.remove(session)) {
            if (session.isOpen()) {
                try {
                    String user = (String) session.getAttributes().get("sender");
                    String systemMessage = "[시스템] " + user + "님이 퇴장했습니다.";
                    sendToRoom(roomId, systemMessage);
                } catch (Exception ignored) {}
            }
        }
    }
}
