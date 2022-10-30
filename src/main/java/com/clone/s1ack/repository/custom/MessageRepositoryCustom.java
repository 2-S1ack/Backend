package com.clone.s1ack.repository.custom;

import com.clone.s1ack.domain.Message;

import java.util.List;

public interface MessageRepositoryCustom {
    List<Message> findByKeyword(String keyword);
}
