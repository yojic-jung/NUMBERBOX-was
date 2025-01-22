package com.numberbox.jwt.repository;

import com.numberbox.jwt.entity.RefreshTokenInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.UUID;

public interface RefreshTokenInfoRepository extends JpaRepository<RefreshTokenInfo, Long> {

    UUID findUserUniqIdByToken(String token);

    boolean existsByTokenAndUserUniqId(String token, UUID userUniqId);

    int deleteByToken(String token);

    int deleteByTokenCreateDateLessThan(LocalDateTime day);
}
