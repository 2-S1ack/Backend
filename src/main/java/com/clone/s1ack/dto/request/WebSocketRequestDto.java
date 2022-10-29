package com.clone.s1ack.dto.request;

import com.clone.s1ack.domain.Member;
import com.clone.s1ack.domain.Room;
import lombok.*;
import org.springframework.web.socket.WebSocketSession;

import javax.persistence.Column;
import javax.persistence.Lob;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

public class WebSocketRequestDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MessageDto {
        @NotBlank
        @Column(nullable = false)
        private String content;

        @Column(nullable = false)
        private Room room;

        /**
         * 송신자
         * publisher(sender)
         */
        @Column(nullable = false)
        private Member member;

        /**
         * 수신자
         * subscriber
         */
        @Column(nullable = false)
        private String desUsername;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RoomDto {

        @Column(nullable = false)
        private String roomName;

        //WebSocketSession은 Spring에서 Websocket Connection이 맺어진 세션
        private Set<WebSocketSession> sessions = new HashSet<>();
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    @Setter
    public static class MsgContentRequestDto {
        private String name;
    }
}
