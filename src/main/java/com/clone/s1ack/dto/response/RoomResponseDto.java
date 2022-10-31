package com.clone.s1ack.dto.response;

import com.clone.s1ack.domain.Message;
import com.clone.s1ack.domain.Room;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RoomResponseDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FindOneResponseDto {

        // 송신자 수신자 내용 생성시간 수정시간 룸ID
        private String username;
        private String desUsername;
        private List<FindOneMessageResponseDto> content = new ArrayList<>();


        public FindOneResponseDto(Room findRoom, List<Message> messages) {
            this.username = findRoom.getUsername();
            this.desUsername = findRoom.getDesUsername();
            this.content = addContent(messages);
        }


        public List<FindOneMessageResponseDto> addContent(List<Message> messages) {
            for (Message message : messages) {
                this.content.add(new FindOneMessageResponseDto(message.getCreatedAt(),
                                                               message.getModifiedAt(),
                                                               message.getContent()));
            }
            return null;
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FindOneMessageResponseDto {
        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;
        private String content;
    }
}
