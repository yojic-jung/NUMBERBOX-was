package com.numberbox.jwt.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.numberbox.jwt.entity.RefreshTokenInfo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RefreshTokenInfoRepository extends JpaRepository<RefreshTokenInfo, Long> {

	@Query("SELECT r.userUniqId FROM RefreshTokenInfo r WHERE r.token = :token")
	UUID findUserUniqIdByToken(@Param("token") String token);

	boolean existsByTokenAndUserUniqId(String token, UUID userUniqId);

	int deleteByToken(String token);

	int deleteByTokenCreateDateLessThan(LocalDateTime day);
}
