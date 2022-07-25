package com.numberbox.customcenter.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.numberbox.customcenter.entity.ErrorReport;

public interface ErrorReportRepository extends JpaRepository <ErrorReport, Integer> {

	public ErrorReport findByReportUserAndContentsNoAndErrType(UUID reportUser, int contentsNo, int errType);
}
