package com.moonsabu.mathinfo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.moonsabu.mathinfo.domain.MathTypeDomain;
import com.moonsabu.mathinfo.entity.MathTypeInfo;

public interface MathTypeRepository extends JpaRepository <MathTypeInfo, MathTypeDomain> {


	
}	
