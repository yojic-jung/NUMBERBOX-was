package com.kamcci.numberbox.members.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kamcci.numberbox.members.domain.FollowUsersDomain;
import com.kamcci.numberbox.members.entity.MembersFollowInfo;

public interface MembersFollowInfoRepository extends JpaRepository<MembersFollowInfo, FollowUsersDomain> {

	public MembersFollowInfo findByFollowUsersFollowingUserNoAndFollowUsersFollowerUserNo(long followingUserNo,
			long followerUserNo);

	public List<MembersFollowInfo> findByFollowUsersFollowingUserNo(long followingUserNo);

	public List<MembersFollowInfo> findByFollowUsersFollowerUserNo(long followingUserNo);

	public int deleteByFollowUsersFollowingUserNoAndFollowUsersFollowerUserNo(long followingUserNo,
			long followerUserNo);

	public int deleteByFollowUsersFollowingUserNo(long followingUserNo);

	public int deleteByFollowUsersFollowerUserNo(long followerUserNo);
}
