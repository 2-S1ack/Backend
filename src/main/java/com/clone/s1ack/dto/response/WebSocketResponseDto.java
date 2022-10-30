package com.clone.s1ack.dto.response;

import com.clone.s1ack.domain.Message;
import com.clone.s1ack.domain.Room;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.clone.s1ack.dto.request.WebSocketRequestDto.MsgContentRequestDto;

public class WebSocketResponseDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MsgContentResponseDto {

        private Long roomId;
        private MsgContentRequestDto msg;
        private Room room;
        private Message message;

    }
}
