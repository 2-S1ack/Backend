package com.clone.s1ack.service;

import com.clone.s1ack.domain.Message;
import com.clone.s1ack.domain.Room;
import com.clone.s1ack.repository.MessageRepository;
import com.clone.s1ack.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RoomService {

    private final RoomRepository roomRepository;

    private final MessageRepository messageRepository;

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
    @Transactional
    public Room createRoom() {
        Room room = new Room();

        roomRepository.save(room);

        return room;
    }

    @Transactional
    public List<Message> searchMessage(String message) {

        return messageRepository.findByKeyword(message);
    }

    /**
     * 대화 방 입장하기
     */




}
