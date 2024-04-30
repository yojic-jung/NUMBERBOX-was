package com.numberbox.members.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.numberbox.members.entity.MembersRole;

public interface MembersRoleRepository extends JpaRepository<MembersRole, Long> {

	@Transactional
	@Modifying // select 문이 아님을 나타낸다
	@Query(value = "UPDATE MembersRole m set m.enabled = false where m.userUniqId = :userUniqId", nativeQuery = false)
	public int disableMember(@Param("userUniqId") UUID userUniqId);

	@Transactional
	@Modifying // select 문이 아님을 나타낸다
	@Query(value = "UPDATE MembersRole m set m.enabled = true where m.userUniqId = :userUniqId")
	public int ableMember(@Param("userUniqId") UUID userUniqId);

	public List<MembersRole> findByUserUniqId(UUID uuid);

	public List<MembersRole> findByRoleNameIn(List<String> roleNameList);

}
