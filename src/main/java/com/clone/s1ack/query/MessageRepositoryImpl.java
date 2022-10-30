package com.clone.s1ack.query;

import com.clone.s1ack.domain.Message;
import com.clone.s1ack.domain.QMessage;
import com.clone.s1ack.repository.custom.MessageRepositoryCustom;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class MessageRepositoryImpl implements MessageRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Message> findByKeyword(String keyword) {
        QMessage message = QMessage.message;

//      JPAQuery<Message> messages =
        List<Message> messages = jpaQueryFactory.selectFrom(message)
                .where(message.content.contains(keyword))
                .orderBy(message.modifiedAt.desc())
                .fetch();

        return messages;
    }
}
