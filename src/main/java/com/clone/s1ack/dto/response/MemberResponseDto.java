package com.clone.s1ack.dto.response;

import com.clone.s1ack.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class MemberResponseDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MemberAuthResponseDto {
        private Long id;
        private String email;
        private String username;

        private String filename;

        public MemberAuthResponseDto(Member savedMember) {
            this.id = savedMember.getId();
            this.email = savedMember.getEmail();
            this.username = savedMember.getUsername();
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AllRoomResponseDto {
        private Long id;
        private String desUsername;
        private String username;
        private String filename;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProfileResponseDto {
        private String username;
        private String filename;
    }

}
