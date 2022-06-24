package com.numberbox.members.domain;

import java.io.Serializable;

import javax.persistence.Embeddable;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter 
@Setter 
public class FollowUsersDomain  implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	long followingUserNo;
	long followerUserNo;
	
	public FollowUsersDomain() {}
	
	@Builder
	public FollowUsersDomain(long followingUserNo, long followerUserNo) { 
		this.followingUserNo = followingUserNo;
		this.followerUserNo = followerUserNo; 
	}
}
