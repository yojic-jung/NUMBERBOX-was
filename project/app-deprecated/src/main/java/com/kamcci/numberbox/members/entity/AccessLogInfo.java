package com.kamcci.numberbox.members.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class AccessLogInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int logInfoNo;

    private UUID userUniqId;

    private String browserInfo;

    private String osInfo;

    private String clientIp;

    @CreationTimestamp
    private LocalDateTime loginTime;
}
