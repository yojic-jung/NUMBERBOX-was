package com.numberbox.mathdocs.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.numberbox.mathdocs.entity.MathDocsUsage;

public interface MathDocsUsageRepository  extends JpaRepository <MathDocsUsage, Integer> {
	

}	
