package com.clone.s1ack.service;

import com.clone.s1ack.domain.Member;
import com.clone.s1ack.domain.Message;
import com.clone.s1ack.domain.Room;
import com.clone.s1ack.repository.MemberRepository;
import com.clone.s1ack.repository.MessageRepository;
import com.clone.s1ack.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.clone.s1ack.dto.request.WebSocketRequestDto.MsgContentRequestDto;
import static com.clone.s1ack.dto.response.WebSocketResponseDto.MsgContentResponseDto;

@RequiredArgsConstructor
@Service
@Slf4j
@Transactional(readOnly = true)
public class MessageService {

    private final MessageRepository messageRepository;
    private final RoomRepository roomRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public MsgContentResponseDto sendMessage(Member member, MsgContentRequestDto msg, String roomId) {
        log.info("MessageService.sendMessage");
        // roomRepository에서 converedRommId값을 기준으로 조회하고, 있으면 findRoom 변수에 할당해준다. (예외처리도 해줌)
        Room findRoom = roomRepository.findById(Long.valueOf(roomId)).orElseThrow(
                () -> new RuntimeException("존재하지 않는 방입니다.")
        );

        Member findMember = memberRepository.findById(member.getId()).orElseThrow(
                () -> new RuntimeException("존재하지 않는 유저입니다.")
        );

        // @Builder 찾아보기
        Message message = new Message(msg.getName(), findRoom, findMember, findRoom.getDesUsername());
        messageRepository.save(message);

        return new MsgContentResponseDto(findRoom.getId(), msg, findRoom, message);
//        sendingOperations.convertAndSend("/sub/chat/room/{roomId}", msg);
    }
}
