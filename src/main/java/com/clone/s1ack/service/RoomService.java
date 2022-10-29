package com.clone.s1ack.service;

import com.clone.s1ack.domain.Member;
import com.clone.s1ack.domain.MemberRoom;
import com.clone.s1ack.domain.Room;
import com.clone.s1ack.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;

    /**
     * 모든 대화 목록 불러오기
     */
    public List<Room> findAllRoom() {
        //채팅방 최근 생성 순으로 반환
        List<Room> rooms = roomRepository.findAllByOrderByModifiedAtDesc();

        return rooms;
    }

    /**
     * 특정 대화 방 조회하기
     */
    public Room findOneRoom(Long roomId) {
        return roomRepository.findById(roomId).orElseThrow(
                () -> new RuntimeException("존재하지 않는 방입니다.")
        );
    }

    /**
     * 대화 방 생성하기
     */
    //채팅방 생성
    public MemberRoom createRoom(Member member, String desUserEmail) {
        Room room = new Room(1L,"yaho");
        return new MemberRoom(1L, member, room, desUserEmail);
    }
}
