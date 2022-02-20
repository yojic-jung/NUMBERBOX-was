package com.moonsabu.mathinfo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.moonsabu.mathinfo.domain.MathTypeDomain;
import com.moonsabu.mathinfo.entity.MathTypeInfo;

public interface MathTypeRepository extends JpaRepository <MathTypeInfo, MathTypeDomain> {
	
	@Query(value = "SELECT typeInfo FROM MathTypeInfo typeInfo where typeInfo.mathTypeDomain.unitUniqNo =:uniqNo",nativeQuery = false)
	public List<MathTypeInfo> findByUnitUniqNo(@Param("uniqNo") String uniqNo);
}