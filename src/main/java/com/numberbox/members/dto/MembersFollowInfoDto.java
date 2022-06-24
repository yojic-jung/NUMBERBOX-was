package com.numberbox.members.dto;

import java.time.LocalDateTime;

import com.numberbox.members.entity.MembersFollowInfo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MembersFollowInfoDto {

	long seqNo;
	FollowUsersDto followUsersDto;
    private LocalDateTime sysCreateDate;
	
    public MembersFollowInfo toEntity() {
		return MembersFollowInfo.builder().followUsers(followUsersDto.toEntity()).sysCreateDate(sysCreateDate)
				.build();
	}
}
