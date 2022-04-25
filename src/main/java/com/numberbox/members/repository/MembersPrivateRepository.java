package com.numberbox.members.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.numberbox.members.entity.MembersPrivate;

public interface MembersPrivateRepository extends JpaRepository <MembersPrivate, UUID> {

	public boolean existsByPhoneNumber(String phoneNumber);
}
