package com.numberbox.mathinfo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.numberbox.mathinfo.entity.MathUnitInfo;

public interface MathUnitKeywordRepository extends JpaRepository<MathUnitInfo, Integer> {

	@Query(value = "SELECT muk.unit_uniq_no FROM math_unit_keyword muk WHERE (muk.keyword REGEXP :keyword)", nativeQuery = true)
	public List<Integer> findByKeywordRegexp(@Param("keyword") String keyword);
}
