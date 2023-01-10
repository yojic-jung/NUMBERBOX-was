package com.numberbox.mathinfo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.numberbox.mathinfo.entity.MathContentsIpsi;

public interface MathContentsIpsiRepository extends JpaRepository <MathContentsIpsi, Integer> {
	
	@Query(value ="SELECT distinct impYear FROM MathContentsIpsi mci", nativeQuery = false)
	public List<Integer> takeImpYearDistinct();
	
	@Query(value ="SELECT distinct impMonth FROM MathContentsIpsi mci WHERE mci.impYear =:impYear ", nativeQuery = false)
	public List<Integer> takeImpYearDistinctByImpYear(@Param("impYear") int impYear);
	
	public List<MathContentsIpsi> findByContentsNo(int contentsNo);
	
}
