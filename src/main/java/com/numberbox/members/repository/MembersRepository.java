package com.numberbox.members.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.numberbox.members.entity.Members;

public interface MembersRepository extends JpaRepository <Members, UUID> {

	public boolean existsByEmail(String email);
	
	public int countByTmpPassword(boolean tmpPassword);

	public Members findByUserUniqId(UUID id);
	
	public Members findByEmail(String email);
	
	public List<Members> findTop10000ByTmpPassword(boolean tmpPassword);
	
	public List<Members> findByHumanStatusAndLastLoginDateLessThan(int humanStatus, LocalDateTime time);
	
	
	@Transactional
	@Modifying // select 문이 아님을 나타낸다
	@Query(value = "UPDATE members m set m.password =:password where m.user_uniq_id = :userUniqId", nativeQuery = true)
	public int changePassword(@Param("userUniqId")UUID userUniqId, @Param("password")String password);
	
	@Transactional
	@Modifying // select 문이 아님을 나타낸다
	@Query(value = "UPDATE members m set m.fail_count = m.fail_count+1 where m.user_uniq_id = :userUniqId", nativeQuery = true)
	public int increaseFailCount(@Param("userUniqId")UUID userUniqId);
	
	@Transactional
	@Modifying // select 문이 아님을 나타낸다
	@Query(value = "UPDATE members m set last_login_date=now() ,m.fail_count = 0 where m.user_uniq_id = :userUniqId", nativeQuery = true)
	public int initLastLoginDate(@Param("userUniqId")UUID userUniqId);
	
	@Transactional
	@Modifying // select 문이 아님을 나타낸다
	@Query(value = "UPDATE members m set m.human_status = 0 where m.user_uniq_id = :userUniqId", nativeQuery = true)
	public int initHumanStatus(@Param("userUniqId")UUID userUniqId);
	
	
	@Transactional
	@Modifying // select 문이 아님을 나타낸다
	@Query(value = "UPDATE members m set m.last_fail_time = now() where m.user_uniq_id = :userUniqId", nativeQuery = true)
	public int initLastFailTime(@Param("userUniqId")UUID userUniqId);
}
