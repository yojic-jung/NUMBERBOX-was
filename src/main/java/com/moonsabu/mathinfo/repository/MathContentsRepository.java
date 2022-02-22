package com.moonsabu.mathinfo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.moonsabu.mathinfo.entity.MathContents;

public interface MathContentsRepository extends JpaRepository <MathContents, Integer> {

	
}
