package com.project.shop.common.outboxmessagerelay;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface OutboxRepository extends JpaRepository<Outbox ,Long> {
    //10초 이후 생성된 이벤트 polling하여 outbox를 조회
    List<Outbox> findAllByCreatedAtLessThanEqualOrderByCreatedAtAsc(
            LocalDateTime from,
            Pageable pageable
    );
}
