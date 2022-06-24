package com.numberbox.mathinfo.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.numberbox.mathinfo.entity.MathConRepoInfo;

public interface MathConRepoInfoRepository extends JpaRepository <MathConRepoInfo, Integer> {

	public MathConRepoInfo findByMathConRepoDomainContentsNoAndMathConRepoDomainUserUniqId(int contentsNo, UUID userUniqId);
	
	public List<MathConRepoInfo> findByMathConRepoDomainContentsNoInAndMathConRepoDomainUserUniqId(List<Integer> contentsNo, UUID userUniqId);
	
	public List<MathConRepoInfo> findByMathConRepoDomainUserUniqId(UUID userUniqId);
	
	public int deleteByMathConRepoDomainContentsNoAndMathConRepoDomainUserUniqId(int contentsNo, UUID userUniqId);
	
	public int deleteByMathConRepoDomainContentsNo(int contentsNo);
}
