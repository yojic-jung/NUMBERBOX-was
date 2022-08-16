package com.numberbox.mathdocs.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.numberbox.mathdocs.entity.MathDocsPaper;

public interface MathDocsPaperRepository  extends JpaRepository <MathDocsPaper, Integer> {

	public List<MathDocsPaper> findByUserUniqIdAndDocsErrSttsNotOrderBySysCreateDateDesc(UUID userUniqId, int docsErrStts);
	
	public MathDocsPaper findByDocsNo(int docsNo);
	
	public int deleteByDocsNo(int docsNo);
	
	public int deleteByUserUniqId(UUID userUniqId);
}	
