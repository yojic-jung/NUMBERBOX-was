package com.numberbox.members.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.numberbox.members.entity.MembersProfile;

public interface MembersProfileRepository extends JpaRepository <MembersProfile, Long> {

	public MembersProfile findByUserUniqId(UUID id);
	
	public MembersProfile findByUserNo(long userNo);
	
	public List<MembersProfile> findByUserNoIn(List<Long> userNo);
	
	@Transactional
	@Modifying // select 문이 아님을 나타낸다
	@Query(value = "UPDATE MembersProfile m set m.profileImgPath =:profileImgPath, m.profileImgName =:profileImgName where m.userUniqId =:uuid", nativeQuery = false)
	public int changeProfileImg(@Param("uuid") UUID uuid, @Param("profileImgPath") String profileImgPath, @Param("profileImgName") String profileImgName);

	@Transactional
	@Modifying // select 문이 아님을 나타낸다
	@Query(value = "UPDATE MembersProfile m set m.nickname =:nickname where m.userUniqId =:uuid", nativeQuery = false)
	public int changeNickname(@Param("uuid") UUID uuid, @Param("nickname") String nickname);
	
	@Transactional
	@Modifying // select 문이 아님을 나타낸다
	@Query(value = "UPDATE MembersProfile m set m.hwpDownCnt =:hwpDownCnt where m.userUniqId =:uuid", nativeQuery = false)
	public int changeHwpDownCnt(@Param("uuid") UUID uuid, @Param("hwpDownCnt") int hwpDownCnt);
	
	@Transactional
	@Modifying // select 문이 아님을 나타낸다
	@Query(value = "UPDATE MembersProfile m set m.hwpDownCnt =0", nativeQuery = false)
	public int initHwpDownCnt();
}
