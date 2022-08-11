package com.numberbox.members.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.numberbox.members.entity.Members;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MembersDto {
	@JsonIgnore
	private UUID userUniqId;
	private String email;
    private String password;
    private boolean humanStatus;
    private boolean tmpPassword;
    private int failCount;
    private LocalDateTime lastFailTime;
    
    
    private String userName;
    private String phoneNumber;
    private String birth;
    
    private LocalDateTime signupDate;
    private LocalDateTime lastLoginDate;
    
    public Members toEntity() {
		return Members.builder().userUniqId(userUniqId).email(email).password(password)
				.humanStatus(humanStatus).tmpPassword(tmpPassword).failCount(failCount).lastFailTime(lastFailTime).signupDate(signupDate).lastLoginDate(lastLoginDate)
				.build();
	}
}
