package com.numberbox.mathinfo.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.numberbox.mathinfo.entity.MathResource;

public interface MathResourceRepository extends JpaRepository<MathResource, Integer> {

	@EntityGraph(attributePaths = { "mathResourceCate" }) // n+1 문제 해결
	public List<MathResource> findByUserUniqId(UUID userUniqId);

	@Query(value = "SELECT distinct m FROM MathResource m INNER JOIN FETCh MathResourceCate m2 on m.resourceNo=m2.resourceNo where m2.mainCateNo =:mainCateNo", nativeQuery = false)
	public List<MathResource> findByMainCateNo(@Param("mainCateNo") int mainCateNo);

	@EntityGraph(attributePaths = { "mathResourceCate" }) // n+1 문제 해결
	public Page<MathResource> findDistinctByMathResourceCateMainCateNo(int mainCateNo, Pageable page);

	@EntityGraph(attributePaths = { "mathResourceCate" }) // n+1 문제 해결
	public Page<MathResource> findByUserUniqIdOrderBySysCreateDateDesc(UUID userUniqId, Pageable page);

	public MathResource findByResourceNo(int resourceNo);

	public int deleteByResourceNo(int resourceNo);

	public int deleteByUserUniqId(UUID userUniqId);

}
