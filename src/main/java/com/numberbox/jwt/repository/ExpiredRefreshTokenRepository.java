package com.numberbox.jwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.numberbox.jwt.entity.ExpiredRefreshToken;

public interface ExpiredRefreshTokenRepository extends JpaRepository<ExpiredRefreshToken, Long> {  
	   boolean existsByToken(String token);  
	}
