package com.numberbox.mathinfo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.numberbox.mathinfo.entity.MathContents;

public interface MathContentsRepository extends JpaRepository <MathContents, Integer> {
	
	public List<MathContents> findByUnitUniqNoAndWorkMemOrderBySysCreateDateDesc(int unitUniqNo, String workMem);
	
}
