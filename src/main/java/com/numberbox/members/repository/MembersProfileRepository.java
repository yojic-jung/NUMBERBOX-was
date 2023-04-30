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
	
	public int countByProfileTypeAndUserUniqIdNotIn(int profileType, List<UUID> userUniqId);

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
	@Query(value = "UPDATE MembersProfile m set m.unitMappingCnt =:unitMappingCnt where m.userUniqId =:uuid", nativeQuery = false)
	public int changeUnitMappingCnt(@Param("uuid") UUID uuid, @Param("unitMappingCnt") int unitMappingCnt);
	
	@Transactional
	@Modifying // select 문이 아님을 나타낸다
	@Query(value = "UPDATE MembersProfile m set m.aiContentsCnt =:aiContentsCnt where m.userUniqId =:uuid", nativeQuery = false)
	public int changeAiContentsCnt(@Param("uuid") UUID uuid, @Param("aiContentsCnt") int aiContentsCnt);
	
	@Transactional
	@Modifying // select 문이 아님을 나타낸다
	@Query(value = "UPDATE MembersProfile m set m.hwpDownCnt =0, m.unitMappingCnt =0, m.aiContentsCnt =0", nativeQuery = false)
	public int initMemberProfileCnt();
	
	@Transactional
	@Modifying // select 문이 아님을 나타낸다
	@Query(value = "UPDATE MembersProfile m set m.profileType =:profileType where m.userUniqId=:uuid", nativeQuery = false)
	public int registerProfileType(@Param("uuid") UUID uuid, @Param("profileType") int profileType);

	@Transactional
	@Modifying // select 문이 아님을 나타낸다(mysql에서는 안돌아감, mariaDB에서는 돌아감)
	@Query(value = "UPDATE MembersProfile m "
			+ "SET m.profileType=6 where m.userUniqId IN "
			+ "(SELECT A.userUniqId FROM MembersProfile A INNER JOIN MembersPrivate B on A.userUniqId=B.userUniqId "
			+ "WHERE  SUBSTRING(B.birth, 1, 2)=:birthYear and A.profileType=5)", nativeQuery = false)
	public int updateTeenagersProfileTypeToEtc(@Param("birthYear") String birthYear);
	
	@Transactional
	@Modifying // select 문이 아님을 나타낸다(mysql에서는 안돌아감, mariaDB에서는 돌아감)
	@Query(value = "UPDATE MembersProfile m "
			+ "SET m.profileType=5 where m.userUniqId IN "
			+ "(SELECT A.userUniqId FROM MembersProfile A INNER JOIN MembersPrivate B on A.userUniqId=B.userUniqId "
			+ "WHERE  SUBSTRING(B.birth, 1, 2)>:birthYear and SUBSTRING(B.birth, 1, 2)<=:nowYear)", nativeQuery = false)
	public int updateTeenagersProfileTypeToStudent(@Param("birthYear") String birthYear, @Param("nowYear") String nowYear);
	
	
}
