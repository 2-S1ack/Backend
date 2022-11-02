package com.clone.s1ack.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class FindAllMessageInOneRoomResponseDto {
    private String username;
    private String desUsername;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}