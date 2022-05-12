package com.numberbox.mathinfo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.numberbox.mathinfo.entity.MathContents;

public interface MathContentsRepository extends JpaRepository <MathContents, Integer> {
	
	public List<MathContents> findByUnitUniqNoOrderBySysCreateDateDesc(int unitUniqNo);
	
	public List<MathContents> findByUnitUniqNoAndUserNoOrderBySysCreateDateDesc(int unitUniqNo, long userNo);
	
	public MathContents findByContentsNo(int findByContentsNo);
	
	@Transactional
	@Modifying // select 문이 아님을 나타낸다
	@Query(value = "UPDATE MathContents m set m.imgPath =:imgpath, m.contentsImg =:contentsImg where m.contentsNo =:contentsNo", nativeQuery = false)
	public int changeConImg(@Param("contentsNo")int contentsNo, @Param("imgpath")String imgpath, @Param("contentsImg")String contentsImg);
	
	@Transactional
	@Modifying // select 문이 아님을 나타낸다
	@Query(value = "UPDATE MathContents m set m.solutionImgPath =:solutionImgpath, m.solutionImg =:solutionImg where m.contentsNo =:contentsNo", nativeQuery = false)
	public int changeSolImg(@Param("contentsNo")int contentsNo, @Param("solutionImgpath")String solutionImgpath, @Param("solutionImg")String solutionImg);

}
