package com.numberbox.members.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.numberbox.members.entity.AccessLogInfo;

public interface AccessLogInfoRepository extends JpaRepository <AccessLogInfo, Integer> {
	
	@Query(value ="SELECT COUNT(DISTINCT user_uniq_id, date_format(login_time, '%Y-%m-%d')) FROM access_log_info WHERE login_time>= :minLoginTime and login_time<= :maxLoginTime and user_uniq_id not in (:managerList)", nativeQuery = true)
	public int countDistinctUserUniqIdByLoginTimeBetweenAndUserUniqIdNotIn(@Param("minLoginTime") LocalDateTime minLoginTime, @Param("maxLoginTime") LocalDateTime maxLoginTime, @Param("managerList") List<UUID> managerList);
	
}
