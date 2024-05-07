package com.numberbox.mathinfo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.numberbox.mathinfo.entity.MathContentsComp;

public interface MathContentsCompRepository extends JpaRepository<MathContentsComp, Integer> {

	@Query(value = "SELECT COUNT(m)>1 FROM MathContentsComp m WHERE m.contentsNo =:contentsNo", nativeQuery = false)
	public boolean exsistOverOneByContentsNo(@Param("contentsNo") int contentsNo);

	public List<MathContentsComp> findByContentsNo(int contentsNo);
}
