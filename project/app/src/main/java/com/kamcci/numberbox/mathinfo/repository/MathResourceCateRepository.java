package com.kamcci.numberbox.mathinfo.repository;

import com.kamcci.numberbox.mathinfo.entity.MathResourceCate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MathResourceCateRepository extends JpaRepository<MathResourceCate, Integer> {

	public int deleteByResourceNo(int resourceNo);

}
