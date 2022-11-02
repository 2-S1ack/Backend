package com.clone.s1ack.repository;

import com.clone.s1ack.domain.Message;
import com.clone.s1ack.dto.response.FindAllMessageInOneRoomResponseDto;
import com.clone.s1ack.repository.custom.MessageRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long>, MessageRepositoryCustom {
//    List<Message> findByRoomId(Long id);

    @Query(value = "select new com.clone.s1ack.dto.response.FindAllMessageInOneRoomResponseDto(r.username, r.desUsername, m.content, m.createdAt, m.modifiedAt) " +
            "from Message m inner join m.room r " +
            "where m.room.id = r.id and r.id = :roomId")
    List<FindAllMessageInOneRoomResponseDto> findOneRoomAllMsg(@Param("roomId") Long roomId);
}
