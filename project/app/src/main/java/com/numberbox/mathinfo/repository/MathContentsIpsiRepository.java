package com.numberbox.mathinfo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.numberbox.mathinfo.entity.MathContentsIpsi;

public interface MathContentsIpsiRepository extends JpaRepository<MathContentsIpsi, Integer> {

	@Query(value = "SELECT distinct impYear FROM MathContentsIpsi mci", nativeQuery = false)
	public List<Integer> takeImpYearDistinct();

	@Query(value = "SELECT distinct impMonth FROM MathContentsIpsi mci WHERE mci.impYear =:impYear ", nativeQuery = false)
	public List<Integer> takeImpYearDistinctByImpYear(@Param("impYear") int impYear);

	public List<MathContentsIpsi> findByContentsNo(int contentsNo);

	@Query(value = "SELECT COUNT(m)>1 FROM MathContentsIpsi m WHERE m.contentsNo =:contentsNo", nativeQuery = false)
	public boolean exsistOverOneByContentsNo(@Param("contentsNo") int contentsNo);

	@Transactional
	@Modifying // select 문이 아님을 나타낸다
	@Query(value = "UPDATE MathContentsIpsi mci set mci.impYear=:impYear, mci.impMonth=:impMonth where mci.contentsNo=:contentsNo", nativeQuery = false)
	public int updateImpYearAndImpMonthByContentsNo(@Param("impYear") int impYear, @Param("impMonth") int impMonth,
			@Param("contentsNo") int contentsNo);
}
