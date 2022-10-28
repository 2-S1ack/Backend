package com.clone.s1ack.domain;

import com.clone.s1ack.domain.base.BaseTimeEntity;
import com.clone.s1ack.dto.request.MemberRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email
    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    public Member(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public Member(MemberRequestDto.MemberSignupRequestDto memberSignupRequestDto) {
        this.username = memberSignupRequestDto.getUsername();
        this.email = memberSignupRequestDto.getEmail();
        this.password = memberSignupRequestDto.getPassword();
    }
}
