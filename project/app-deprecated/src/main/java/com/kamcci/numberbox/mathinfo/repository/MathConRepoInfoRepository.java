package com.kamcci.numberbox.mathinfo.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.kamcci.numberbox.mathinfo.entity.MathConRepoInfo;

public interface MathConRepoInfoRepository extends JpaRepository<MathConRepoInfo, Integer> {

	public MathConRepoInfo findByMathConRepoDomainContentsNoAndMathConRepoDomainUserUniqId(int contentsNo,
			UUID userUniqId);

	public List<MathConRepoInfo> findByMathConRepoDomainContentsNoInAndMathConRepoDomainUserUniqId(
			List<Integer> contentsNo, UUID userUniqId);

	public Page<MathConRepoInfo> findByMathConRepoDomainUserUniqId(UUID userUniqId, Pageable page);

	public int deleteByMathConRepoDomainContentsNoAndMathConRepoDomainUserUniqId(int contentsNo, UUID userUniqId);

	public int deleteByMathConRepoDomainContentsNo(int contentsNo);

	public int deleteByMathConRepoDomainUserUniqId(UUID userUniqId);

}
