package com.sangjae.chap04realtime.section05.stomp;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class StompChatService {
    private final SimpMessagingTemplate messagingTemplate;
    // 채팅방 관리: roomId -> 해당 방에 입장한 사용자들의 집합
    private final Map<String, Set<String>> chatRooms = new HashMap<>();

    /* 사용자가 채팅방에 입장할 때 처리 */
    public void joinRoom(String roomId, String user) {
        chatRooms.computeIfAbsent(roomId, k -> new HashSet<>()).add(user);
        // 입장 시 시스템 메시지 전송
        sendSystemMessage(roomId, user + "님이 입장했습니다.");
    }

    /* 사용자가 채팅방에서 퇴장할 때 처리 */
    public void leaveRoom(String roomId, String user) {
        Set<String> users = chatRooms.get(roomId);
        if (users != null && users.remove(user)) {
            sendSystemMessage(roomId, user + "님이 퇴장했습니다.");
            if (users.isEmpty()) {
                chatRooms.remove(roomId);
            }
        }
    }

    /* 채팅 메시지 전송 (일반 사용자 메시지) */
    public void sendChatMessage(ChatMessage message) {
        messagingTemplate.convertAndSend("/topic/chat/" + message.getRoomId(), message);
    }

    /* 시스템 메시지 전송 */
    public void sendSystemMessage(String roomId, String content) {
        ChatMessage systemMessage = new ChatMessage();
        systemMessage.setRoomId(roomId);
        systemMessage.setSender("SYSTEM");
        systemMessage.setContent(content);
        systemMessage.setType(MessageType.SYSTEM);
        messagingTemplate.convertAndSend("/topic/chat/" + roomId, systemMessage);
    }

}
