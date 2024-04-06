package com.numberbox.mathdocs.repository;

import com.numberbox.mathdocs.entity.MathDocsUsage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface MathDocsUsageRepository extends JpaRepository<MathDocsUsage, Integer> {

    public int countBySysCreateDateAfterAndUserUniqIdNotIn(LocalDateTime now, List<UUID> uuidList);
// todo jpql 정상적이지 않음 수정 필요(java 17 migration 과정 중)
//    // 프로필별 학습지 사용 빈도
//    @Query(value = "SELECT " + "new com.numberbox.common.util.CustomTenFieldDto("
//            + "COUNT(CASE WHEN B.profileType=0 THEN 1 END) as nbCol1, "
//            + "COUNT(CASE WHEN B.profileType=1 THEN 1 END) as nbCol2, "
//            + "COUNT(CASE WHEN B.profileType=2 THEN 1 END) as nbCol3, "
//            + "COUNT(CASE WHEN B.profileType=3 THEN 1 END) as nbCol4, "
//            + "COUNT(CASE WHEN B.profileType=4 THEN 1 END) as nbCol5, "
//            + "COUNT(CASE WHEN B.profileType=5 THEN 1 END) as nbCol6, "
//            + "COUNT(CASE WHEN B.profileType=6 THEN 1 END) as nbCol7, " + "0 as nbCol8, 0 as nbCol9, 0 as nbCol10)"
//            + "FROM " + "MathDocsUsage as A, " + "MembersProfile as B " + "WHERE A.userUniqId=B.userUniqId "
//            + "and A.userUniqId not in (SELECT mr.userUniqId FROM MembersRole mr where mr.roleName='ADMIN' or mr.roleName='MANAGER')", nativeQuery = false)
//    public List<CustomTenFieldDto> statisticMathDocsUsageByProfile();
//
//    // 요일별 학습지 사용 빈도
//    @Query(value = "SELECT " + "new com.numberbox.common.util.CustomTenFieldDto("
//            + "COUNT(CASE WHEN WEEKDAY(sysCreateDate)=0 THEN '월요일' END) as nbCol1, "
//            + "COUNT(CASE WHEN WEEKDAY(sysCreateDate)=1 THEN '화요일' END) as nbCol2, "
//            + "COUNT(CASE WHEN WEEKDAY(sysCreateDate)=2 THEN '수요일' END) as nbCol3, "
//            + "COUNT(CASE WHEN WEEKDAY(sysCreateDate)=3 THEN '목요일' END) as nbCol4, "
//            + "COUNT(CASE WHEN WEEKDAY(sysCreateDate)=4 THEN '금요일' END) as nbCol5, "
//            + "COUNT(CASE WHEN WEEKDAY(sysCreateDate)=5 THEN '토요일' END) as nbCol6, "
//            + "COUNT(CASE WHEN WEEKDAY(sysCreateDate)=6 THEN '일요일' END) as nbCol7, "
//            + "0 as nbCol8, 0 as nbCol9, 0 as nbCol10) " + "FROM MathDocsUsage "
//            + "where userUniqId not in (SELECT mr.userUniqId FROM MembersRole mr where mr.roleName='ADMIN' or mr.roleName='MANAGER')", nativeQuery = false)
//    public List<CustomTenFieldDto> statisticMathDocsUsageByDayOfWeek();

    // 프로필/요일별 학습지 사용 빈도
//	@Query(value = "SELECT " + "new com.numberbox.common.util.CustomTenFieldDto(" + "(CASE "
//			+ "	WHEN B.profileType=0 THEN '미등록' " + "	WHEN B.profileType=1 THEN '원장' "
//			+ "	WHEN B.profileType=2 THEN '강사' " + "	WHEN B.profileType=3 THEN '교사' "
//			+ "	WHEN B.profileType=4 THEN '학부모' " + "   WHEN B.profileType=5 THEN '학생' "
//			+ "	WHEN B.profileType=6 THEN '기타' " + "END) as nbCol1, "
//			+ "COUNT(CASE when WEEKDAY(A.sysCreateDate)=0 then 1 end) as nbCol2, "
//			+ "COUNT(CASE when WEEKDAY(A.sysCreateDate)=1 then 1 end) as nbCol3, "
//			+ "COUNT(CASE when WEEKDAY(A.sysCreateDate)=2 then 1 end) as nbCol4, "
//			+ "COUNT(CASE when WEEKDAY(A.sysCreateDate)=3 then 1 end) as nbCol5, "
//			+ "COUNT(CASE when WEEKDAY(A.sysCreateDate)=4 then 1 end) as nbCol6, "
//			+ "COUNT(CASE when WEEKDAY(A.sysCreateDate)=5 then 1 end) as nbCol7, "
//			+ "COUNT(CASE when WEEKDAY(A.sysCreateDate)=6 then 1 end) as nbCol8, " + "0 as nbCol9, 0 as nbCol10)"
//			+ "FROM  " + "MathDocsUsage as A, " + "MembersProfile as B " + "WHERE A.userUniqId=B.userUniqId "
//			+ "and A.userUniqId not in (SELECT mr.userUniqId FROM MembersRole mr where mr.roleName='ADMIN' or mr.roleName='MANAGER')"
//			+ "GROUP BY B.profileType", nativeQuery = false)
//	public List<CustomTenFieldDto> statisticMathDocsUsageByProfileDayOfWeek();
//
//	@Query(value = "SELECT new com.numberbox.common.util.CustomTenFieldDto("
//			+ " CONCAT(YEAR(mdu.sysCreateDate), '년 ', MONTH(mdu.sysCreateDate), '월') as nbCol1, count(*) as nbCol2,"
//			+ " 0 as nbCol3, 0 as nbCol4, 0 as nbCol5, 0 as nbCol6, 0 as nbCol7, 0 as nbCol8, 0 as nbCol9, 0 as nbCol10) "
//			+ " FROM MathDocsUsage as mdu"
//			+ " where mdu.userUniqId not in (SELECT mr.userUniqId FROM MembersRole mr where mr.roleName='ADMIN' or mr.roleName='MANAGER') "
//			+ " Group by YEAR(mdu.sysCreateDate), MONTH(mdu.sysCreateDate) order by mdu.sysCreateDate ASC", nativeQuery = false)
//	public List<CustomTenFieldDto> countMathDocsUsageGroupBySysCreateDateMonth();

}
