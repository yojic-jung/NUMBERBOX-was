package com.numberbox.mathinfo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.numberbox.mathinfo.entity.MathResourceCate;

public interface MathResourceCateRepository extends JpaRepository <MathResourceCate, Integer> {
	
	public List<MathResourceCate> findByMainCateNo(int mainCateNo);
	
}
