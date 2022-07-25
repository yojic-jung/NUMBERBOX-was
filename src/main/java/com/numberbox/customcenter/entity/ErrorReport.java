package com.numberbox.customcenter.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Builder;
import lombok.Getter;

@Getter
@Entity
public class ErrorReport {
	@Id
	public int reportId;
	
	/*
	 * errType==1 : 문제 오류신고
	 * errType==2 : 컨텐츠 오류신고
	 */
	@Column(length = 1, nullable = false)
	public int errType;
	
	@Column(length = 11, nullable = true, updatable=false)
	int contentsNo;
	
	@Column(columnDefinition = "BINARY(16)", nullable = false, updatable=false)
	public UUID reportUser;
	
	@Column(length = 500, nullable = true)
	public String reportContents;
	
	@Column(columnDefinition = "BINARY(16)", nullable = true, updatable=false)
	public UUID replyUser;
	
	@Column(length = 500, nullable = true)
	public String replyContents;
	
	@Column
	@UpdateTimestamp
	LocalDateTime sysUpdateDate;
	
	@Column(updatable=false)
	@CreationTimestamp
	LocalDateTime sysCreateDate;
	
	public ErrorReport() { }
	
    @Builder
    public ErrorReport(int reportId, int errType, int contentsNo, UUID reportUser, String reportContents, UUID replyUser, String replyContents, LocalDateTime sysUpdateDate, LocalDateTime sysCreateDate) {
        this.reportId = reportId;
        this.errType = errType;
        this.contentsNo = contentsNo;
        this.reportUser = reportUser;
        this.reportContents = reportContents;
        this.replyUser = replyUser;
        this.replyContents = replyContents;
        this.sysUpdateDate = sysUpdateDate;
        this.sysCreateDate = sysCreateDate;
    }
}
