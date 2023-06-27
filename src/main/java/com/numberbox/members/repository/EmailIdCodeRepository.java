package com.numberbox.members.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.numberbox.members.entity.EmailIdCode;

public interface EmailIdCodeRepository  extends JpaRepository <EmailIdCode, String> {
	
	public EmailIdCode findByEmail(String email);
	
	public int deleteByEmail(String email);
}
