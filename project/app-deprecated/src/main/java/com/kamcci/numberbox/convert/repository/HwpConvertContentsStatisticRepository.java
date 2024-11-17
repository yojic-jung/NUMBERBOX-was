package com.kamcci.numberbox.convert.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.kamcci.numberbox.common.util.CustomTenFieldDto;
import com.kamcci.numberbox.convert.entity.HwpConvertContentsStatistic;

public interface HwpConvertContentsStatisticRepository extends JpaRepository<HwpConvertContentsStatistic, Long> {

	// 프로필별 학습지 사용 빈도
	@Query(value = "SELECT " + "new com.kamcci.common.util.CustomTenFieldDto("
			+ "COUNT(CASE WHEN B.profileType=0 THEN 1 END) as nbCol1, "
			+ "COUNT(CASE WHEN B.profileType=1 THEN 1 END) as nbCol2, "
			+ "COUNT(CASE WHEN B.profileType=2 THEN 1 END) as nbCol3, "
			+ "COUNT(CASE WHEN B.profileType=3 THEN 1 END) as nbCol4, "
			+ "COUNT(CASE WHEN B.profileType=4 THEN 1 END) as nbCol5, "
			+ "COUNT(CASE WHEN B.profileType=5 THEN 1 END) as nbCol6, "
			+ "COUNT(CASE WHEN B.profileType=6 THEN 1 END) as nbCol7, " + "0 as nbCol8, 0 as nbCol9, 0 as nbCol10)"
			+ "FROM " + "HwpConvertContentsStatistic as A, " + "MembersProfile as B "
			+ "WHERE A.userUniqId=B.userUniqId "
			+ "and A.userUniqId not in (SELECT mr.userUniqId FROM MembersRole mr where mr.roleName='ADMIN' or mr.roleName='MANAGER')", nativeQuery = false)
	public List<CustomTenFieldDto> statisticConvertContentsByProfile();

	public int deleteByConvertNo(Long convertNo);
}
