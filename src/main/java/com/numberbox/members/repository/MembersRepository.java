package com.numberbox.members.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.numberbox.common.util.CustomTenFieldDto;
import com.numberbox.members.entity.Members;

public interface MembersRepository extends JpaRepository<Members, UUID> {
	// 사용자 조회
	@Query(value = "SELECT " + "new com.numberbox.common.util.CustomTenFieldDto( A.email as nbCol1, C.birth as nbCol2, "
			+ "(CASE " + "	WHEN B.profileType=0 THEN '미등록' " + "	WHEN B.profileType=1 THEN '원장' "
			+ "	WHEN B.profileType=2 THEN '강사' " + "	WHEN B.profileType=3 THEN '교사' "
			+ "	WHEN B.profileType=4 THEN '학부모' " + "   WHEN B.profileType=5 THEN '학생' "
			+ "	WHEN B.profileType=6 THEN '기타' " + "END) as nbCol3, "
			+ "A.lastLoginDate as nbCol4, A.signupDate as nbCol5,"
			+ "0 as nbCol6, 0 as nbCol7, 0 as nbCol8, 0 as nbCol9, 0 as nbCol10)" + "FROM  "
			+ "Members as A, MembersProfile as B, MembersPrivate as C  " + "where "
			+ "A.userUniqId=B.userUniqId and A.userUniqId = C.userUniqId and A.humanStatus != 3 order by A.signupDate desc", nativeQuery = false)
	public List<CustomTenFieldDto> lastSignupUserLimit(Pageable page);

	// 월별 재로그인 사용자 비율
	@Query(value = "SELECT  " + "ROUND(count(case when signupDate!=lastLoginDate then 1 End)/count(*)*100) " + "FROM  "
			+ "Members "
			+ "WHERE signupDate>:minDate and signupDate<:maxDate and userUniqId not in (:uuidList)", nativeQuery = false)
	public Long reLoginRatioPerMonth(@Param("maxDate") LocalDateTime maxDate, @Param("minDate") LocalDateTime minDate,
			@Param("uuidList") List<UUID> uuidList);

	// 시간대별 가입자수
	@Query(value = "SELECT "
			+ "new com.numberbox.common.util.CustomTenFieldDto(count(CASE WHEN HOUR(m.signupDate)<3 THEN 1 END) AS nbCol1, "
			+ "count(CASE WHEN HOUR(m.signupDate)<6 and HOUR(m.signupDate)>=3 THEN 1 END) AS nbCol2, "
			+ "count(CASE WHEN HOUR(m.signupDate)<9 and HOUR(m.signupDate)>=6 THEN 1 END) AS nbCol3, "
			+ "count(CASE WHEN HOUR(m.signupDate)<12 and HOUR(m.signupDate)>=9 THEN 1 END) AS nbCol4, "
			+ "count(CASE WHEN HOUR(m.signupDate)<15 and HOUR(m.signupDate)>=12 THEN 1 END) AS nbCol5, "
			+ "count(CASE WHEN HOUR(m.signupDate)<18 and HOUR(m.signupDate)>=15 THEN 1 END) AS nbCol6, "
			+ "count(CASE WHEN HOUR(m.signupDate)<21 and HOUR(m.signupDate)>=18 THEN 1 END) AS nbCol7, "
			+ "count(CASE WHEN HOUR(m.signupDate)<24 and HOUR(m.signupDate)>=21 THEN 1 END) AS nbCol8,  "
			+ "0 as nbCol9, 0 as nbCol10) " + "FROM Members as m "
			+ "WHERE m.userUniqId not in (SELECT mr.userUniqId FROM MembersRole mr where mr.roleName='ADMIN' or mr.roleName='MANAGER')", nativeQuery = false)
	public List<CustomTenFieldDto> statisticMembersCntGrouBySignupDateHour();

	public int countBySignupDateAfterAndUserUniqIdNotIn(LocalDateTime now, List<UUID> uuidList);

	public int countByLastLoginDateAfterAndUserUniqIdNotIn(LocalDateTime now, List<UUID> uuidList);

	// 프로필에 따른 시간대별 가입자수
	@Query(value = "SELECT " + "new com.numberbox.common.util.CustomTenFieldDto("
			+ "(CASE WHEN B.profileType=0 THEN '미등록' " + "WHEN B.profileType=1 THEN '원장' "
			+ "WHEN B.profileType=2 THEN '강사' " + "WHEN B.profileType=3 THEN '교사' " + "WHEN B.profileType=4 THEN '학부모' "
			+ "WHEN B.profileType=5 THEN '학생' " + "WHEN B.profileType=6 THEN '기타' END)" + " as nbCol1, "
			+ "count(CASE WHEN HOUR(A.signupDate)<3 THEN 1 END) AS nbCol2, "
			+ "count(CASE WHEN HOUR(A.signupDate)<6 and HOUR(A.signupDate)>=3 THEN 1 END) AS nbCol3, "
			+ "count(CASE WHEN HOUR(A.signupDate)<9 and HOUR(A.signupDate)>=6 THEN 1 END) AS nbCol4, "
			+ "count(CASE WHEN HOUR(A.signupDate)<12 and HOUR(A.signupDate)>=9 THEN 1 END) AS nbCol5, "
			+ "count(CASE WHEN HOUR(A.signupDate)<15 and HOUR(A.signupDate)>=12 THEN 1 END) AS nbCol6, "
			+ "count(CASE WHEN HOUR(A.signupDate)<18 and HOUR(A.signupDate)>=15 THEN 1 END) AS nbCol7, "
			+ "count(CASE WHEN HOUR(A.signupDate)<21 and HOUR(A.signupDate)>=18 THEN 1 END) AS nbCol8, "
			+ "count(CASE WHEN HOUR(A.signupDate)<24 and HOUR(A.signupDate)>=21 THEN 1 END) AS nbCol9, "
			+ "0 as nbCol10) " + "FROM  " + "Members as A, " + "MembersProfile as B "
			+ "where A.userUniqId=B.userUniqId "
			+ "and A.userUniqId not in (SELECT mr.userUniqId FROM MembersRole mr where mr.roleName='ADMIN' or mr.roleName='MANAGER')"
			+ "group by B.profileType " + "order by B.profileType ", nativeQuery = false)
	public List<CustomTenFieldDto> statisticMembersByHourGrouByProfileType();

	public boolean existsByEmail(String email);

	public int countByTmpPassword(boolean tmpPassword);

	public Members findByUserUniqId(UUID id);

	public Members findByEmail(String email);

	public List<Members> findTop10000ByTmpPassword(boolean tmpPassword);

	public List<Members> findByHumanStatusAndLastLoginDateLessThan(int humanStatus, LocalDateTime time);

	@Transactional
	@Modifying // select 문이 아님을 나타낸다
	@Query(value = "UPDATE Members m set m.failCount = :failCnt where m.userUniqId = :userUniqId", nativeQuery = false)
	public int increaseFailCount(@Param("userUniqId") UUID userUniqId, @Param("failCnt") int failCnt);

	@Transactional
	@Modifying // select 문이 아님을 나타낸다
	@Query(value = "UPDATE Members m set m.lastLoginDate= :now, m.failCount = 0 where m.userUniqId = :userUniqId", nativeQuery = false)
	public int initLastLoginDate(@Param("userUniqId") UUID userUniqId, @Param("now") LocalDateTime now);

	@Transactional
	@Modifying // select 문이 아님을 나타낸다
	@Query(value = "UPDATE Members m set m.humanStatus = 0 where m.userUniqId = :userUniqId", nativeQuery = false)
	public int initHumanStatus(@Param("userUniqId") UUID userUniqId);

	@Transactional
	@Modifying // select 문이 아님을 나타낸다
	@Query(value = "UPDATE Members m set m.lastFailTime =:now where m.userUniqId = :userUniqId", nativeQuery = false)
	public int initLastFailTime(@Param("userUniqId") UUID userUniqId, @Param("now") LocalDateTime now);

	@Query(value = "SELECT new com.numberbox.common.util.CustomTenFieldDto("
			+ " CONCAT(YEAR(m.signupDate), '년 ', MONTH(m.signupDate), '월') as nbCol1, count(*) as nbCol2,"
			+ " 0 as nbCol3, 0 as nbCol4, 0 as nbCol5, 0 as nbCol6, 0 as nbCol7, 0 as nbCol8, 0 as nbCol9, 0 as nbCol10) "
			+ " FROM Members as m where m.signupDate>='2022-11-01' and "
			+ " m.userUniqId not in (SELECT mr.userUniqId FROM MembersRole mr where mr.roleName='ADMIN' or mr.roleName='MANAGER')"
			+ " GROUP BY YEAR(m.signupDate), MONTH(m.signupDate) order by m.signupDate ASC", nativeQuery = false)
	public List<CustomTenFieldDto> countMembersGroupBySysCreateDateMonth();
}
