package com.clone.s1ack.dto.response;

import com.clone.s1ack.domain.Room;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

public class RoomResponseDto {
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FindOneRoomResponseDto {
        // 송신자 수신자 룸ID / 내용 생성시간 수정시간
        private String username;
        private String desUsername;
        private Long roodId;
        private String filename;
        private List<FindAllMessageInOneRoomResponseDto> content = new ArrayList<>();

        public FindOneRoomResponseDto(Room room, String filename, List<FindAllMessageInOneRoomResponseDto> content) {
            this.username = room.getUsername();
            this.desUsername = room.getDesUsername();
            this.roodId = room.getId();
            this.filename = filename;
            this.content = content;
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class createRoomResponseDto {
        private String username;
        private String desUsername;
        private Long roodId;
    }
}


//    @Getter
//    @NoArgsConstructor
//    @AllArgsConstructor
//    public static class FindOneResponseDto {
//
//        // 송신자 수신자 룸ID / 내용 생성시간 수정시간
//        private String username;
//        private String desUsername;
//        private Long roodId;
//        private List<FindOneMessageResponseDto> content = new ArrayList<>();
//
//        private String filename;
//
//        public FindOneResponseDto(Room findRoom, List<Message> messages, String filename) {
//            this.username = findRoom.getUsername();
//            this.desUsername = findRoom.getDesUsername();
//            this.roodId = findRoom.getId();
//            this.filename = filename;
//            addContent(messages);
//        }
//
//        public void addContent(List<Message> messages) {
//            for (Message message : messages) {
//                this.content.add(new FindOneMessageResponseDto(message.getCreatedAt(),
//                                                                message.getModifiedAt(),
//                                                                message.getContent()));
//            }
//        }
//    }
//
//    @Getter
//    @NoArgsConstructor
//    @AllArgsConstructor
//    public static class FindOneMessageResponseDto {
//        private LocalDateTime createdAt;
//        private LocalDateTime modifiedAt;
//        private String content;
//    }

