package com.kamcci.numberbox.members.dto;

import com.kamcci.numberbox.members.domain.FollowUsersDomain;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FollowUsersDto {

	long followingUserNo;
	long followerUserNo;

	public FollowUsersDomain toEntity() {
		return FollowUsersDomain.builder().followingUserNo(followingUserNo).followerUserNo(followerUserNo).build();
	}
}
