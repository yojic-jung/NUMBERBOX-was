package com.kamcci.numberbox.mathinfo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kamcci.numberbox.mathinfo.entity.MathResourceMenu;

public interface MathResourceMenuRepository extends JpaRepository<MathResourceMenu, Integer> {

	public List<MathResourceMenu> findAllByOrderByAlignOrderAsc();
}
