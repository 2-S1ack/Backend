package com.clone.s1ack.repository;

import com.clone.s1ack.domain.Message;
import com.clone.s1ack.repository.custom.MessageRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long>, MessageRepositoryCustom {




}
