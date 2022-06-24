package com.numberbox.mathinfo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.numberbox.mathinfo.entity.MathResource;

public interface MathResourceRepository extends JpaRepository <MathResource, Integer> {
	
	@Query(value = "SELECT distinct m FROM MathResource m INNER JOIN FETCh MathResourceCate m2 on m.resourceNo=m2.resourceNo where m2.mainCateNo =:mainCateNo", nativeQuery = false)
	public List<MathResource> findByMainCateNo(@Param("mainCateNo") int mainCateNo);
	
	public List<MathResource> findByMathResourceCateMainCateNo(int mainCateNo);
}
