package com.numberbox.mathinfo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.numberbox.mathinfo.domain.MathTypeDomain;
import com.numberbox.mathinfo.entity.MathTypeInfo;

public interface MathTypeRepository extends JpaRepository<MathTypeInfo, MathTypeDomain> {

	@Query(value = "SELECT typeInfo FROM MathTypeInfo typeInfo where typeInfo.mathTypeDomain.unitUniqNo =:uniqNo order by typeOrder asc", nativeQuery = false)
	public List<MathTypeInfo> findByUnitUniqNoOrderByTypeOrderAsc(@Param("uniqNo") String uniqNo);

	public MathTypeInfo findByMathTypeDomainUnitUniqNoAndMathTypeDomainTypeNo(String uniqNo, String typeNo);

	@Query(value = "select typeInfo FROM MathTypeInfo typeInfo where CONCAT(typeInfo.mathTypeDomain.unitUniqNo, ',', typeInfo.mathTypeDomain.typeNo) in (:unitUniqNoAndTypeNoList)", nativeQuery = false)
	public List<MathTypeInfo> findByMathTypeDomainUnitUniqNoAndMathTypeDomainTypeNoIn(
			@Param("unitUniqNoAndTypeNoList") List<String> unitUniqNoAndTypeNoList);

	public List<MathTypeInfo> findByMathTypeDomainUnitUniqNoInOrderByMathTypeDomainUnitUniqNoAscTypeOrderAsc(
			List<String> unitUniqNoList);

	public int deleteByMathTypeDomainUnitUniqNoAndMathTypeDomainTypeNo(String unitUniqNo, String typeNo);

	public List<MathTypeInfo> findByMathTypeDomainUnitUniqNo(String unitUniqNo);

}