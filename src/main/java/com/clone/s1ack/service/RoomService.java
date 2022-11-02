package com.clone.s1ack.service;

import com.clone.s1ack.domain.Member;
import com.clone.s1ack.domain.Message;
import com.clone.s1ack.domain.Room;
import com.clone.s1ack.dto.response.FindAllMessageInOneRoomResponseDto;
import com.clone.s1ack.dto.response.RoomResponseDto;
import com.clone.s1ack.repository.MemberRepository;
import com.clone.s1ack.repository.MessageRepository;
import com.clone.s1ack.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.clone.s1ack.dto.request.WebSocketRequestDto.*;
import static com.clone.s1ack.dto.response.MemberResponseDto.*;
import static com.clone.s1ack.dto.response.RoomResponseDto.*;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RoomService {

    private final RoomRepository roomRepository;
    private final MemberRepository memberRepository;
    private final MessageRepository messageRepository;

    /**
     * 모든 대화 목록 불러오기
     */
    public List<AllRoomResponseDto> findAllRoom(Member member) {
        // 로그인한 사용자의 정보를 로드
        Member findMember = isExistMember(member);

        // 검색 조건: 채팅방 최근 생성 순 + 해당 사용자와 일치하는 메시지의 desUsername
        // 로그인한 사용자와 동일한 룸의 desUsername을 읽어온다

        List<Room> rooms = roomRepository.findByUsername(findMember.getUsername());
        List<AllRoomResponseDto> responseDto = new ArrayList<>();

        for (Room room : rooms) {
            responseDto.add(new AllRoomResponseDto(room.getId(), room.getDesUsername(),findMember.getUsername(),findMember.getFileName()));
        }
        return responseDto;
    }

    /**
     * 특정 대화 방 조회하기
     */
    public FindOneRoomResponseDto findOneRoom(Member member, Long roomId) {
        isExistMember(member);

        // 1. 특정 대화 방 찾기
        Room findRoom = roomRepository.findByUsernameAndId(member.getUsername(), roomId).orElseThrow(
                () -> new RuntimeException("존재하지 않는 방입니다.")
        );

        // 2. 특정 대화방의 내용을 찾기
//        List<Message> messages = messageRepository.findByRoomId(findRoom.getId());
        List<FindAllMessageInOneRoomResponseDto> roomAllMsg = messageRepository.findOneRoomAllMsg(findRoom.getId());

        // 송신자 수신자 내용 생성시간 수정시간 룸ID
//        return new FindOneResponseDto(findRoom, messages, member.getFileName());
        return new FindOneRoomResponseDto(findRoom, member.getFileName(), roomAllMsg);
    }

    /**
     * 대화 방 생성하기
     */
    @Transactional
    public createRoomResponseDto createRoom(Member member, CreateRoomDto createRoomDto) {
        isExistMember(member);

        log.info("===============");
        log.info("createRoomDto.getDesEmail() = {}", createRoomDto.getDesEmail());
        log.info("member.getEmail() = {}", member.getEmail());
        log.info("===============");

        Member findMember = memberRepository.findByEmail(createRoomDto.getDesEmail()).orElseThrow(
                () -> new RuntimeException("존재하지 않은 사용자 입니다.")
        );


        Room room = new Room(findMember.getUsername(), member.getUsername());
        roomRepository.save(room);

        return new createRoomResponseDto(room.getUsername(), room.getDesUsername(), room.getId());
    }

    private Member isExistMember(Member member) {
        return memberRepository.findByUsername(member.getUsername()).orElseThrow(
                () -> new RuntimeException("로그인이 필요합니다."))
                ;}

    @Transactional
    public List<Message> searchMessage(String message) {

        return messageRepository.findByKeyword(message);
    }

    @Transactional
    public String deleteRoom(Long roomId, String username) {
        Member Username = memberRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("권한이 없는 유저입니다."));
        roomRepository.deleteById(roomId);
        return "룸 삭제가 완료되었습니다";
    }
}
