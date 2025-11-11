package com.gt.controller;

import com.gt.entity.TransactionLog;
import com.gt.service.TransactionLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionLogController {

    @Autowired
    private TransactionLogService transactionLogService;

    // Admin: View all transaction logs
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<TransactionLog> getAllLogs() {
        return transactionLogService.getAllLogs();
    }

    //Admin: View logs by product ID
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/product/{productId}")
    public List<TransactionLog> getLogsByProduct(@PathVariable Long productId) {
        return transactionLogService.getLogsByProduct(productId);
    }

    // Admin: View logs by user ID
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/user/{userId}")
    public List<TransactionLog> getLogsByUser(@PathVariable Long userId) {
        return transactionLogService.getLogsByUser(userId);
    }
}
