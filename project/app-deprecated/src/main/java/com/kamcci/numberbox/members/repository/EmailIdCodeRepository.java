package com.kamcci.numberbox.members.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kamcci.numberbox.members.entity.EmailIdCode;

public interface EmailIdCodeRepository extends JpaRepository<EmailIdCode, String> {

	public EmailIdCode findByEmail(String email);

	public int deleteByEmail(String email);
}
