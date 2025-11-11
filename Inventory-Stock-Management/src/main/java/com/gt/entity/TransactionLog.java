package com.gt.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "transaction_logs")
public class TransactionLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productId;
    private Long userId;

    @Enumerated(EnumType.STRING)
    private ChangeType changeType;

    private int quantityChanged;

    private LocalDateTime createdAt;

    public TransactionLog() {}

    public TransactionLog(Long productId, Long userId, ChangeType changeType, int quantityChanged) {
        this.productId = productId;
        this.userId = userId;
        this.changeType = changeType;
        this.quantityChanged = quantityChanged;
        this.createdAt = LocalDateTime.now();
    }

    public enum ChangeType {
        INCREASE,
        DECREASE
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public ChangeType getChangeType() { return changeType; }
    public void setChangeType(ChangeType changeType) { this.changeType = changeType; }

    public int getQuantityChanged() { return quantityChanged; }
    public void setQuantityChanged(int quantityChanged) { this.quantityChanged = quantityChanged; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
