package com.gt.service;

import com.gt.entity.TransactionLog;
import com.gt.repository.TransactionLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TransactionLogServiceImpl implements TransactionLogService {

    @Autowired
    private TransactionLogRepository transactionLogRepository;

    @Override
    public List<TransactionLog> getAllLogs() {
        return transactionLogRepository.findAll();
    }

    @Override
    public List<TransactionLog> getLogsByProduct(Long productId) {
        return transactionLogRepository.findByProductId(productId);
    }

    @Override
    public List<TransactionLog> getLogsByUser(Long userId) {
        return transactionLogRepository.findByUserId(userId);
    }
}
