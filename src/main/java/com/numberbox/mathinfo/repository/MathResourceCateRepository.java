package com.numberbox.mathinfo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.numberbox.mathinfo.entity.MathResourceCate;


public interface MathResourceCateRepository extends JpaRepository <MathResourceCate, Integer> {
	
	public int deleteByResourceNo(int resourceNo);
	
}
