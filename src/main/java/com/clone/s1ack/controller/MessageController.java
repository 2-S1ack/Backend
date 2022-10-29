package com.clone.s1ack.controller;

import com.clone.s1ack.domain.Member;
import com.clone.s1ack.domain.Message;
import com.clone.s1ack.domain.Room;
import com.clone.s1ack.dto.HelloMessage;
import com.clone.s1ack.dto.response.WebSocketResponseDto;
import com.clone.s1ack.repository.MessageRepository;
import com.clone.s1ack.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;

import javax.annotation.PostConstruct;

import static com.clone.s1ack.dto.request.WebSocketRequestDto.*;
import static com.clone.s1ack.dto.response.WebSocketResponseDto.*;

@Controller
@RequiredArgsConstructor
public class MessageController {

    private final SimpMessageSendingOperations sendingOperations;
    private final RoomRepository roomRepository;
    private final MessageRepository messageRepository;

    private final PasswordEncoder passwordEncoder;
    private Member member = null;

    @MessageMapping("/chat/message/{roomId}") // /topic/chat/message/1
    @SendTo("/topic/greetings")
    @ResponseBody
    public MsgContentResponseDto sendMessage(MsgContentRequestDto msg, @DestinationVariable String roomId) {
        System.out.println("==============");
        System.out.println("MessageController.sendMessage");
        System.out.println("msg = " + msg.toString());
        System.out.println("roomId = " + roomId);
        System.out.println("==============");

        Long convertedRoomId = Long.valueOf(roomId);

        Room findRoom = roomRepository.findById(convertedRoomId).orElseThrow(
                () -> new RuntimeException("존재하지 않는 방입니다.")
        );
        Message message = new Message("send Message", findRoom, member);
        messageRepository.save(message);

        return new MsgContentResponseDto(convertedRoomId, msg, findRoom, message);
//        sendingOperations.convertAndSend("/topic/chat/room/1", msg);
    }

    @PostConstruct
    //의존관게 주입완료되면 실행되는 코드
    private void init() {
        System.out.println("MessageController.init");
        member = new Member(1L,"jae", "email@co.kr", passwordEncoder.encode("blabla"));
    }

    @MessageMapping("/hello") // 목적지가 path와 일치하는 메시지를 수신했을경우 해당 메서드를 호출
    @SendTo("/topic/greetings") // 해당 path의 모든 구독자들에게 반환값이 브로드캐스트된다
    public String greeting(@RequestBody HelloMessage helloMessage) throws Exception {
        System.out.println("helloMessage.getName() = " + helloMessage.getName());
        Thread.sleep(3000); // 3초 쉬었다가 전환되도록 지연처리
        return HtmlUtils.htmlEscape(helloMessage.getName());

//        return new Greeting(HtmlUtils.htmlEscape(helloMessage.getName()));
    }
}
