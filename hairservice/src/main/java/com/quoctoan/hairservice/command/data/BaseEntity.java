package com.quoctoan.hairservice.command.data;



import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import java.io.Serializable;
import java.time.LocalDateTime;

public class BaseEntity implements Serializable {

    private static final long serialVersionUID = 6272286591828701741L;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @CreationTimestamp
    @Column(name = "updated_at",nullable = false, updatable = false)
    private LocalDateTime updatedAt;
}
