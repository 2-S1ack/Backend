package com.clone.s1ack.controller;

import com.clone.s1ack.domain.Message;
import com.clone.s1ack.dto.ResponseDto;
import com.clone.s1ack.security.user.UserDetailsImpl;
import com.clone.s1ack.service.RoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.clone.s1ack.dto.request.WebSocketRequestDto.CreateRoomDto;
import static com.clone.s1ack.dto.response.MemberResponseDto.AllRoomResponseDto;
import static com.clone.s1ack.dto.response.RoomResponseDto.FindOneResponseDto;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/chat")
@Slf4j
public class RoomController {

    private final RoomService roomService;

    /**
     * 1. 모든 대화방 목록 조회
     *
     * @return
     */
    @GetMapping("/room")
    @ResponseBody
    public ResponseDto<List<AllRoomResponseDto>> rooms(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        log.info("RoomController.rooms");
        return ResponseDto.success(roomService.findAllRoom(userDetails.getMember()));
    }

    /**
     * 2. 특정 대화방 목록 조회
     */
    @GetMapping("/room/{roomId}")
    @ResponseBody
    public ResponseDto<FindOneResponseDto> roomInfo(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long roomId) {
        log.info("RoomController.roomInfo");
        return ResponseDto.success(roomService.findOneRoom(userDetails.getMember(), roomId));
    }

    /**
     * 3. 새로운 채팅방 생성 (새대화 -> 팀원 추가)
     */
    @PostMapping("/room")
    @ResponseBody
    public ResponseDto<Long> createRoom(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody @Valid CreateRoomDto createRoomDto) {
        log.info("RoomController.createRoom");
        return roomService.createRoom(userDetails.getMember(), createRoomDto);
    }

    /**
     * 검색 내용 찾기
     */
    @GetMapping("/chat")
    public List<Message> searchMessage(@RequestParam String message) {
        log.info("RoomController.searchMessage");
        return roomService.searchMessage(message);
    }

    @DeleteMapping("/room/{roomId}")
    public ResponseDto<String> deleteRoom(@PathVariable Long roomId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseDto.success(roomService.deleteRoom(roomId, userDetails.getUsername()));
    }

//    @GetMapping("/room/enter/{roomId}") // /root/enter/1
//    @ResponseBody
//    public String roomEnter(@PathVariable Long roomId, @RequestParam String msg) {
//        System.out.println("RoomController.roomInfo");
//        System.out.println("roomId = " + roomId);
//        System.out.println("msg = " + msg);
//
//        return "roomEnter";
//    }
}
