package com.numberbox.members.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
