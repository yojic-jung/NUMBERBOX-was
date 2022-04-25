package com.numberbox.members.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.numberbox.members.entity.MembersNo;

public interface MembersNoRepository extends JpaRepository <MembersNo, Long> {

	public MembersNo findByUserUniqId(UUID id);
	
}
