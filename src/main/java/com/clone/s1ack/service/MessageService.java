package com.clone.s1ack.service;

import com.clone.s1ack.domain.Member;
import com.clone.s1ack.domain.Message;
import com.clone.s1ack.domain.Room;
import com.clone.s1ack.repository.MemberRepository;
import com.clone.s1ack.repository.MessageRepository;
import com.clone.s1ack.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

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

    private final PasswordEncoder passwordEncoder;

    private Member member = null;

    @PostConstruct
    //의존관계 주입완료되면 실행되는 코드
    private void init() {
        log.info("MessageService.init");
        member = new Member(1L,"jae", "email@co.kr", passwordEncoder.encode("blabla"));
        memberRepository.save(member);
    }

    @Transactional
    public MsgContentResponseDto sendMessage(MsgContentRequestDto msg, String roomId) {
        log.info("MessageService.sendMessage");
        // roomRepository에서 converedRommId값을 기준으로 조회하고, 있으면 findRoom 변수에 할당해준다. (예외처리도 해줌)
        Room findRoom = roomRepository.findById(Long.valueOf(roomId)).orElseThrow(
                () -> new RuntimeException("존재하지 않는 방입니다.")
        );

        // @Builder 찾아보기
        Message message = new Message(msg.getName(), findRoom, member,"vugil@naver.com");
        messageRepository.save(message);

        return new MsgContentResponseDto(findRoom.getId(), msg, findRoom, message);
//        sendingOperations.convertAndSend("/sub/chat/room/{roomId}", msg);
    }
}
