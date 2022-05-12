package com.numberbox.mathinfo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.numberbox.mathinfo.entity.MathResourceCate;

public interface MathResourceCateRepository extends JpaRepository <MathResourceCate, Integer> {
	
	@Query(value = "SELECT distinct new com.numberbox.mathinfo.entity.MathResourceCate(m.resourceNo, m.mainCateNo, m.mathResource) FROM MathResourceCate m INNER JOIN m.mathResource where m.mainCateNo =:mainCateNo", nativeQuery = false)
	public List<MathResourceCate> findByMainCateNo(@Param("mainCateNo") int mainCateNo);
	
}
