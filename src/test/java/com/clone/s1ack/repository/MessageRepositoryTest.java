package com.clone.s1ack.repository;

import com.clone.s1ack.dto.response.FindAllMessageInOneRoomResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
class MessageRepositoryTest {

    @Autowired
    MessageRepository messageRepository;

    @Test
    public void findOneRoomAllMsg() throws Exception {
        // given
        List<FindAllMessageInOneRoomResponseDto> roomAllMsg = messageRepository.findOneRoomAllMsg(15L);

        // when
        for (FindAllMessageInOneRoomResponseDto findAllMessageInOneRoomResponseDto : roomAllMsg) {
            System.out.println("findAllMessageInOneRoomResponseDto = " + findAllMessageInOneRoomResponseDto);
        }

        // then
    }


}