package com.clone.s1ack.repository;

import com.clone.s1ack.domain.Message;
import com.clone.s1ack.repository.custom.MessageRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long>, MessageRepositoryCustom {

    List<Message> findAllByDesUsername(String desUsername);
    List<Message> findByRoomId(Long id);


//    @Query("select from Member m, Room r where ")
}
