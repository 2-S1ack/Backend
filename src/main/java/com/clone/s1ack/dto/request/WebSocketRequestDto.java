package com.clone.s1ack.dto.request;

import com.clone.s1ack.domain.Member;
import com.clone.s1ack.domain.Room;
import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

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
    public static class CreateRoomDto {

        @Column(nullable = false)
        @Email
        private String desEmail;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    @Setter
    public static class MsgContentRequestDto {
        private String name;
//        private String desUsername;
//        private String desEmail;
//        private Long desUserId;
//
//        public MsgContentRequestDto(String name, String desUsername) {
//            this.name = name;
//            this.desUsername = desUsername;
//        }
    }
}
