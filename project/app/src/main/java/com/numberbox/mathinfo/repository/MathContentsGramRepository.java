package com.numberbox.mathinfo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.numberbox.mathinfo.entity.MathContentsGrammer;

public interface MathContentsGramRepository extends JpaRepository<MathContentsGrammer, Integer> {

	public void deleteByContentsNo(int contentsNo);

}
