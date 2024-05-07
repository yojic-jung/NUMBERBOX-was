package com.numberbox.jwt.repository;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.numberbox.jwt.entity.RefreshTokenInfo;

public interface RefreshTokenInfoRepository extends JpaRepository<RefreshTokenInfo, Long> {

	UUID findUserUniqIdByToken(String token);

	boolean existsByTokenAndUserUniqId(String token, UUID userUniqId);

	int deleteByToken(String token);

	int deleteByTokenCreateDateLessThan(LocalDateTime day);
}
