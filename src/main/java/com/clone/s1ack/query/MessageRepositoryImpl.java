package com.clone.s1ack.query;

import com.clone.s1ack.domain.Message;
import com.clone.s1ack.domain.QMessage;
import com.clone.s1ack.domain.QRoom;
import com.clone.s1ack.repository.custom.MessageRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class MessageRepositoryImpl implements MessageRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Message> findByKeyword(String keyword) {
        QMessage message = QMessage.message;
        QRoom room = QRoom.room;

//        selectFrom => 이걸 기준으로 조회를 하겠다
        List<Message> messages = jpaQueryFactory.selectFrom(message)

                .leftJoin(message.room,room).fetchJoin()
//                where => 이 기준으로 조건을 먹이겠다 // contains 의미 : 앞뒤 구분없이, 1개의 문자만 똑같아도 포함된 것으로 간주하는 메소드
                .where(message.content.contains(keyword))
//                orderby => 이 기준으로 정렬을 하겠다.
                .orderBy(message.modifiedAt.desc())
//               fetch => 기존에 조인된 것을 다시 해제하는 역할 + querydsl값을 반환해주는 역할
                .fetch();

        return messages;
    }
}
