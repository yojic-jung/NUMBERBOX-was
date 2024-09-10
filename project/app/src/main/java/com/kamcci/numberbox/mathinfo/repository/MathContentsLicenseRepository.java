package com.kamcci.numberbox.mathinfo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kamcci.numberbox.mathinfo.entity.MathContentsLicense;

public interface MathContentsLicenseRepository extends JpaRepository<MathContentsLicense, Integer> {

	public List<MathContentsLicense> findByContentsNo(int contentsNo);

	public int deleteByContentsNo(int contentsNo);
}
