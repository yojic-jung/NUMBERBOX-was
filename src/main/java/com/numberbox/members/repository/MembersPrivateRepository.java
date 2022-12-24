package com.numberbox.members.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.numberbox.common.util.CustomTenFieldDto;
import com.numberbox.members.entity.MembersPrivate;

public interface MembersPrivateRepository extends JpaRepository <MembersPrivate, UUID> {

	//나이대별 회원가입자 수
	@Query(value = "SELECT " + 
			"new com.numberbox.common.util.CustomTenFieldDto(SUBSTRING(mp.birth, 1, 2) AS nbCol1, " + 
			"count(*) AS nbCol2, " + 
			"0 AS nbCol3, " + 
			"0 AS nbCol4, " + 
			"0 AS nbCol5, "+
			"0 AS nbCol6,"+ 
			"0 AS nbCol7, " + 
			"0 AS nbCol8, 0 as nbCol9, 0 as nbCol10) " +
			"FROM MembersPrivate mp where mp.userUniqId not in (SELECT mr.userUniqId FROM MembersRole mr where mr.roleName='ADMIN' or mr.roleName='MANAGER')  Group by SUBSTRING(mp.birth, 1, 2)" , nativeQuery = false)
	public List<CustomTenFieldDto> statisticMembersByAge();
	
	public boolean existsByPhoneNumber(String phoneNumber);
	
	public MembersPrivate findByPhoneNumberAndUserName(String phoneNumber, String userName);
	
	public MembersPrivate findByUserUniqId(UUID userUniqId);
	
}
