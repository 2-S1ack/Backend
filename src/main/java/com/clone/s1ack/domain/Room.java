package com.clone.s1ack.domain;

import com.clone.s1ack.domain.base.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Getter
@AllArgsConstructor
@Entity
@Builder
@NoArgsConstructor
public class Room extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String desUsername;

    @NotBlank
    private String username;

    public Room(String desUsername, String username) {
        this.desUsername = desUsername;
        this.username = username;
    }
}
