package com.numberbox.mathinfo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.numberbox.mathinfo.entity.MathResourceImg;

public interface MathResourceImgRepository extends JpaRepository<MathResourceImg, Integer> {

	public List<MathResourceImg> findByResourceNo(int resourceNo);

	public int deleteByResourceNo(int resourceNo);
}