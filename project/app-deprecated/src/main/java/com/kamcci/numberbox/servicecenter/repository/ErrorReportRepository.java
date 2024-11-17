package com.kamcci.numberbox.servicecenter.repository;

import java.util.List;
import java.util.UUID;

import com.kamcci.numberbox.serivcecenter.entity.ErrorReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ErrorReportRepository extends JpaRepository<ErrorReport, Integer> {

	public ErrorReport findByReportUserAndContentsNoAndErrType(UUID reportUser, int contentsNo, int errType);

	public List<ErrorReport> findByReportUserOrderBySysCreateDateDesc(UUID reportUser);

	public List<ErrorReport> findByReportSttsOrderBySysCreateDateDesc(int reportStts);

	public List<ErrorReport> findByErrTypeOrderBySysCreateDateDesc(int errType);

	public List<ErrorReport> findByReportSttsAndErrTypeOrderBySysCreateDateDesc(int reportStts, int errType);

	public ErrorReport findByReportId(int reportId);
}
