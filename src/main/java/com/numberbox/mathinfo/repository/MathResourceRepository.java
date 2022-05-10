package com.numberbox.mathinfo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.numberbox.mathinfo.entity.MathResource;

public interface MathResourceRepository extends JpaRepository <MathResource, Integer> {
	
	public List<MathResource> findByMainCateNoOrderByDownCntDesc(int mainCateNo);

}
