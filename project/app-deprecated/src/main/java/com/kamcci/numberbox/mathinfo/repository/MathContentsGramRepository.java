package com.kamcci.numberbox.mathinfo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kamcci.numberbox.mathinfo.entity.MathContentsGrammer;

public interface MathContentsGramRepository extends JpaRepository<MathContentsGrammer, Integer> {

	public void deleteByContentsNo(int contentsNo);

}
