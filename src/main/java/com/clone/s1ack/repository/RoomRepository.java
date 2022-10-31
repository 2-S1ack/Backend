package com.clone.s1ack.repository;

import com.clone.s1ack.domain.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long> {

    List<Room> findByUsername(String username);

    Optional<Room> findByUsernameAndId(String username, Long roomId);

}
