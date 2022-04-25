package com.numberbox.members.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.numberbox.members.entity.MembersInfo;

public interface MembersInfoRepository extends JpaRepository <MembersInfo, UUID> {

	public boolean existsByPhoneNumber(String phoneNumber);
}
