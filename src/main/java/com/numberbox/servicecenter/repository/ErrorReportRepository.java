package com.numberbox.servicecenter.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.numberbox.serivcecenter.entity.ErrorReport;

public interface ErrorReportRepository extends JpaRepository <ErrorReport, Integer> {

	public ErrorReport findByReportUserAndContentsNoAndErrType(UUID reportUser, int contentsNo, int errType);
	
	public List<ErrorReport> findByReportUserOrderBySysCreateDateDesc(UUID reportUser);
	
	public int countByReportSttsAndErrType(int reportStts, int errType);
	
	public List<ErrorReport> findByErrTypeOrderBySysCreateDateDesc(int errType);
	
	public List<ErrorReport> findByReportSttsAndErrTypeOrderBySysCreateDateDesc(int reportStts, int errType);
	
	public ErrorReport findByReportId(int reportId);
}
