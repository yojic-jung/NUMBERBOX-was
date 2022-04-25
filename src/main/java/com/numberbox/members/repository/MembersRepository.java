package com.numberbox.members.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.numberbox.members.entity.Members;

public interface MembersRepository extends JpaRepository <Members, UUID> {

	public boolean existsByEmail(String email);

	public Members findByUserUniqId(UUID id);
	
	public Members findByEmail(String email);
	
	@Transactional
	@Modifying // select 문이 아님을 나타낸다
	@Query(value = "UPDATE members m set m.fail_count = m.fail_count+1 where m.user_uniq_id = :userUniqId", nativeQuery = true)
	public int increaseFailCount(@Param("userUniqId")UUID userUniqId);
	
	@Transactional
	@Modifying // select 문이 아님을 나타낸다
	@Query(value = "UPDATE members m set m.fail_count = 0 where m.user_uniq_id = :userUniqId", nativeQuery = true)
	public int initFailCount(@Param("userUniqId")UUID userUniqId);
	
	@Transactional
	@Modifying // select 문이 아님을 나타낸다
	@Query(value = "UPDATE members m set m.last_fail_time = now() where m.user_uniq_id = :userUniqId", nativeQuery = true)
	public int initLastFailTime(@Param("userUniqId")UUID userUniqId);
}
