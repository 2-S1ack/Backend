package com.clone.s1ack.domain;

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
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Email
    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column
    private String fileName;

    @Column
    private String url;

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

    public Member(Member member, String fileName, String imageUrl) {
        this.username = member.getUsername();
        this.email = member.getEmail();
        this.password = member.getPassword();
        this.fileName = fileName;
        this.url = imageUrl;
    }

    public void updateMember(String fileName, String imageUrl, String username) {
        this.username = username;
        this.fileName = fileName;
        this.url = imageUrl;
    }
}
