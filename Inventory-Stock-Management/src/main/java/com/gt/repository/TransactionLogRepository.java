package com.gt.repository;

import com.gt.entity.TransactionLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TransactionLogRepository extends JpaRepository<TransactionLog, Long> {
    List<TransactionLog> findByProductId(Long productId);
    List<TransactionLog> findByUserId(Long userId);
}
