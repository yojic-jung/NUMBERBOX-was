package com.kamcci.numberbox.mathinfo.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kamcci.numberbox.mathinfo.domain.MathConLikeDomain;
import com.kamcci.numberbox.mathinfo.entity.MathConLikeInfo;

public interface MathConLikeInfoRepository extends JpaRepository<MathConLikeInfo, MathConLikeDomain> {

	public MathConLikeInfo findByMathConLikeDomainContentsNoAndMathConLikeDomainUserUniqId(int contentsNo,
			UUID userUniqId);

	public List<MathConLikeInfo> findByMathConLikeDomainContentsNoInAndMathConLikeDomainUserUniqId(
			List<Integer> contentsNo, UUID userUniqId);

	public int deleteByMathConLikeDomainContentsNoAndMathConLikeDomainUserUniqId(int contentsNo, UUID userUniqId);

	public int deleteByMathConLikeDomainContentsNo(int contentsNo);

	public int deleteByMathConLikeDomainUserUniqId(UUID userUniqId);

}
