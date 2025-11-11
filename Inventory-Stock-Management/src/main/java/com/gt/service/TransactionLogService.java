package com.gt.service;

import com.gt.entity.TransactionLog;
import java.util.List;

public interface TransactionLogService {
    List<TransactionLog> getAllLogs();
    List<TransactionLog> getLogsByProduct(Long productId);
    List<TransactionLog> getLogsByUser(Long userId);
}
