package com.clone.s1ack.controller;

import com.clone.s1ack.dto.ResponseDto;
import com.clone.s1ack.service.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import static com.clone.s1ack.dto.request.WebSocketRequestDto.MsgContentRequestDto;
import static com.clone.s1ack.dto.response.WebSocketResponseDto.MsgContentResponseDto;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MessageController {

//    private final SimpMessageSendingOperations sendingOperations;
    private final MessageService messageService;

    // messageMapping 메서드가 모두 완수되고,
    // @SendTo 어노테이션 경로가 app.js측 경로와 맞물려서 수신됨.

    // @MessageMapping에서 variable을 추출할 때는 @DestinationVariable을 사용한다.
    @MessageMapping("/chat/message/{roomId}") // /pub/chat/message/1 => 송신메시지
    @SendTo("/sub/chat/room/{roomId}")
    @ResponseBody
    public ResponseDto<MsgContentResponseDto> requiredMessage(MsgContentRequestDto msg,
                                                              @DestinationVariable String roomId) {
        return ResponseDto.success(messageService.sendMessage(msg, roomId));
    }

    /**
     * 테스트 용도
     */
//    @MessageMapping("/hello") // 목적지가 path와 일치하는 메시지를 수신했을경우 해당 메서드를 호출
//    @SendTo("/topic/greetings") // 해당 path의 모든 구독자들에게 반환값이 브로드캐스트된다
//    public String greeting(@RequestBody HelloMessage helloMessage) throws Exception {
//        System.out.println("helloMessage.getName() = " + helloMessage.getName());
//        Thread.sleep(3000); // 3초 쉬었다가 전환되도록 지연처리
//        return HtmlUtils.htmlEscape(helloMessage.getName());
//
//        return new Greeting(HtmlUtils.htmlEscape(helloMessage.getName()));
//    }
}
