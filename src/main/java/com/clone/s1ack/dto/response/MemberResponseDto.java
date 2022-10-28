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
        private String username;

        public MemberAuthResponseDto(Member findMember) {
            this.username = findMember.getUsername();
        }
    }


}
