package com.numberbox.members.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.numberbox.members.entity.AccessLogInfo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccessLogInfoDto {
	int logInfoNo;
	private UUID userUniqId;
	private String browserInfo;
	private String osInfo;
	private String clientIp;
	private LocalDateTime loginTime;

	public AccessLogInfo toEntity() {
		return AccessLogInfo.builder().userUniqId(userUniqId).browserInfo(browserInfo).osInfo(osInfo).clientIp(clientIp)
				.build();
	}
}
