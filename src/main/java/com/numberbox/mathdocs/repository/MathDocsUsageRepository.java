package com.numberbox.mathdocs.repository;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;

import com.numberbox.mathdocs.entity.MathDocsUsage;

public interface MathDocsUsageRepository  extends JpaRepository <MathDocsUsage, Integer> {
	
	public int countBySysCreateDateAfter(LocalDateTime now);
}	
