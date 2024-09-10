package com.kamcci.numberbox.mathdocs.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.kamcci.numberbox.mathdocs.entity.MathDocsPaper;

public interface MathDocsPaperRepository extends JpaRepository<MathDocsPaper, Integer> {

	public Page<MathDocsPaper> findByUserUniqIdAndDocsErrSttsNotInOrderBySysCreateDateDesc(UUID userUniqId,
			List<Integer> docsErrStts, Pageable page);

	public MathDocsPaper findByDocsNo(int docsNo);

	public int deleteByDocsNo(int docsNo);

	public int deleteByUserUniqId(UUID userUniqId);

	public int deleteByDocsErrSttsAndSysCreateDateLessThan(int errStts, LocalDateTime day);

}
