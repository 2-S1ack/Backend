package com.clone.s1ack.controller;

import com.clone.s1ack.domain.Member;
import com.clone.s1ack.domain.MemberRoom;
import com.clone.s1ack.domain.Room;
import com.clone.s1ack.repository.MemberRepository;
import com.clone.s1ack.security.user.UserDetailsImpl;
import com.clone.s1ack.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.LinkedHashMap;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/chat")
public class RoomController {

    private final RoomService roomService;
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    private Member member = null;


    /**
     * 필요한 API
     * 1. 모든 대화방 목록 조회
     * 2. 특정 대화방 목록 조회
     * 3. 새로운 채팅방 생성
     */

    /**
     * 1. 모든 대화방 목록 조회
     */
    @GetMapping("/rooms")
    @ResponseBody
    public List<Room> rooms() {
        System.out.println("RoomController.rooms");
        /**
         * return ResponseDto.success(blabla);
         */
        return roomService.findAllRoom();
    }

    /**
     * 2. 특정 대화방 목록 조회
     */
    @GetMapping("/room/{roomId}")
    @ResponseBody
    public Room roomInfo(@PathVariable Long roomId) {
        System.out.println("RoomController.roomInfo");
        return roomService.findOneRoom(roomId);
    }

    /**
     * 3. 새로운 채팅방 생성 (새대화 -> 팀원 추가)
     */
    @PostMapping("/room")
    @ResponseBody
    public MemberRoom createRoom(
//            @AuthenticationPrincipal UserDetailsImpl userDetails,
                                 @RequestBody String desUserEmail,
                                 Model model) {
        System.out.println("RoomController.createRoom");
        /**
         * 팀원 추가를 눌렀을 때 수행됨
         */
        return roomService.createRoom(member, desUserEmail);
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
