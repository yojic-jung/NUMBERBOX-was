package com.numberbox.mathinfo.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.numberbox.mathinfo.entity.MathResource;

public interface MathResourceRepository extends JpaRepository <MathResource, Integer> {
	
	@Query(value = "SELECT distinct m FROM MathResource m INNER JOIN FETCh MathResourceCate m2 on m.resourceNo=m2.resourceNo where m2.mainCateNo =:mainCateNo", nativeQuery = false)
	public List<MathResource> findByMainCateNo(@Param("mainCateNo") int mainCateNo);
	
	@EntityGraph(attributePaths = {"mathResourceCate"})		//n+1 문제 해결
	public List<MathResource> findDistinctByMathResourceCateMainCateNo(int mainCateNo);
	
	@EntityGraph(attributePaths = {"mathResourceCate"})		//n+1 문제 해결
	public List<MathResource> findByUserUniqIdOrderBySysCreateDateDesc(UUID userUniqId);
	
	public MathResource findByResourceNo(int resourceNo);
	
	public int deleteByResourceNo(int resourceNo);
	
}
