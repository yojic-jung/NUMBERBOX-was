package com.kamcci.numberbox.members.repository;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.kamcci.numberbox.members.entity.AccessLogInfo;

public interface AccessLogInfoRepository extends JpaRepository<AccessLogInfo, Integer> {

	@Query(value = "SELECT " + " COUNT(DISTINCT user_uniq_id, date_format(login_time, '%Y-%m-%d')) "
			+ " FROM access_log_info " + " WHERE login_time>= :minLoginTime and login_time<= :maxLoginTime"
			+ " and user_uniq_id not in (SELECT mr.user_uniq_id FROM members_role mr where mr.role_name='ADMIN' or mr.role_name='MANAGER')", nativeQuery = true)
	public int countDistinctUserUniqIdByLoginTimeBetweenAndUserUniqIdNotIn(
			@Param("minLoginTime") LocalDateTime minLoginTime, @Param("maxLoginTime") LocalDateTime maxLoginTime);

}
