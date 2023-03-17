package com.numberbox.members.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.numberbox.members.entity.AccessLogInfo;

public interface AccessLogInfoRepository extends JpaRepository <AccessLogInfo, Integer> {
	
	@Query(value ="SELECT COUNT(DISTINCT logInfo.userUniqId) FROM AccessLogInfo logInfo WHERE loginTime>= :minLoginTime and loginTime<= :maxLoginTime and userUniqId not in (:managerList)", nativeQuery = false)
	public int countDistinctUserUniqIdByLoginTimeBetweenAndUserUniqIdNotIn(@Param("minLoginTime") LocalDateTime minLoginTime, @Param("maxLoginTime") LocalDateTime maxLoginTime, @Param("managerList") List<UUID> managerList);
	
	@Query(value ="SELECT COUNT(DISTINCT logInfo.userUniqId) FROM AccessLogInfo logInfo WHERE loginTime> :loginTime and userUniqId not in (:managerList)", nativeQuery = false)
	public int countDistinctUserUniqIdByLoginTimeAfterAndUserUniqIdNotIn(@Param("loginTime") LocalDateTime loginTime, @Param("managerList") List<UUID> managerList);
	
}
