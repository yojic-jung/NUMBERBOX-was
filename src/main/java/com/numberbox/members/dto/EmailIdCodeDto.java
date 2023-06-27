package com.numberbox.members.dto;

import java.time.LocalDateTime;

import com.numberbox.members.entity.EmailIdCode;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmailIdCodeDto {
	private String email;
	private String idCode;
    private LocalDateTime sysCreateTime;
    
    public EmailIdCode toEntity() {
		return EmailIdCode.builder().email(email).idCode(idCode).sysCreateTime(sysCreateTime)
				.build();
	}
}
