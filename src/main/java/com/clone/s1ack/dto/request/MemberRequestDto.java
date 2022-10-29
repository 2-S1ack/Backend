package com.clone.s1ack.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class MemberRequestDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MemberSignupRequestDto {

//        private static final String PASSWORD_REGEX = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$%^&*()+|=])[A-Za-z\\d~!@#$%^&*()+|=]{8,16}";
        @NotBlank
        @Email
        @Column(nullable = false, unique = true)
        private String email;

        @NotBlank(message = "Username은 공백일 수 없습니다.")
        @Pattern(regexp = "[a-zA-Z0-9]{4,12}", message = "닉네임양식을 확인해주세요!")
        @Column(nullable = false, unique = true)
        private String username;

//        @Pattern(regexp = PASSWORD_REGEX, message = "패스워드는 무조건 영문, 숫자, 특수문자를 1글자 이상 포함해야 합니다.")
        @NotBlank(message = "Password는 공백일 수 없습니다.")
        private String password;

        @NotBlank
        private String passwordConfirm;

        public void setEncodePassword(String encodedPassword) {
            this.password = encodedPassword;
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MemberLoginRequestDto {
        @NotBlank
        @Email
        @Column(nullable = false, unique = true)
        private String email;

        @NotBlank(message = "Password는 공백일 수 없습니다.")
        private String password;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MemberSignUpDuplicateEmailDto {
        @NotBlank
        @Email
        @Column(nullable = false, unique = true)
        private String email;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MemberSignUpDuplicateUsernameDto {
        @NotBlank(message = "Username은 공백일 수 없습니다.")
        @Pattern(regexp = "[a-zA-Z0-9]{4,12}", message = "닉네임양식을 확인해주세요!")
        @Column(nullable = false, unique = true)
        private String username;
    }

}
