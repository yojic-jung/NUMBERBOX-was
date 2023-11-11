package com.numberbox.serivcecenter.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Builder;
import lombok.Getter;

@Getter
@Entity
public class ErrorReport {
	@Id
	public int reportId;

	/*
	 * errType==0 : 기타 오류신고 errType==1 : 문제 오류신고 errType==2 : 컨텐츠 오류신고 errType==3 :
	 * 학습지 오류신고 errType==4 : 문제 만들기 페이지 오류신고 errType==5 : hwp to web 파일변환기 오류신고
	 */
	@Column(length = 1, nullable = false)
	public int errType;

	@Column(length = 11, nullable = true, updatable = false)
	int contentsNo;

	@JsonIgnore
	@Column(columnDefinition = "BINARY(16)", nullable = false, updatable = false)
	public UUID reportUser;

	@Column(length = 500, nullable = true)
	public String reportContents;

	@JsonIgnore
	@Column(columnDefinition = "BINARY(16)", nullable = true)
	public UUID replyUser;

	@Column(length = 500, nullable = true)
	public String replyContents;

	@Column(length = 7, nullable = false)
	public String osInfo;
	@Column(length = 7, nullable = false)
	public String browser;

	@Column(length = 30, nullable = true)
	public String firstImgPath;
	@Column(length = 70, nullable = true)
	public String firstImgName;

	@Column(length = 30, nullable = true)
	public String secondImgPath;
	@Column(length = 70, nullable = true)
	public String secondImgName;

	@Column(length = 30, nullable = true)
	public String thirdImgPath;
	@Column(length = 70, nullable = true)
	public String thirdImgName;

	/*
	 * 접수 : 0 답변완료 : 1
	 */
	@Column(length = 1, nullable = false)
	public int reportStts;

	@Column
	@UpdateTimestamp
	LocalDateTime sysUpdateDate;

	@Column(updatable = false)
	@CreationTimestamp
	LocalDateTime sysCreateDate;

	public ErrorReport() {
	}

	@Builder
	public ErrorReport(int reportId, int errType, int contentsNo, UUID reportUser, String reportContents,
			UUID replyUser, String replyContents, String osInfo, String browser, String firstImgPath,
			String firstImgName, String secondImgPath, String secondImgName, String thirdImgPath, String thirdImgName,
			int reportStts, LocalDateTime sysUpdateDate, LocalDateTime sysCreateDate) {
		this.reportId = reportId;
		this.errType = errType;
		this.contentsNo = contentsNo;
		this.reportUser = reportUser;
		this.reportContents = reportContents;
		this.replyUser = replyUser;
		this.replyContents = replyContents;
		this.osInfo = osInfo;
		this.browser = browser;
		this.firstImgPath = firstImgPath;
		this.firstImgName = firstImgName;
		this.secondImgPath = secondImgPath;
		this.secondImgName = secondImgName;
		this.thirdImgPath = thirdImgPath;
		this.thirdImgName = thirdImgName;
		this.reportStts = reportStts;
		this.sysUpdateDate = sysUpdateDate;
		this.sysCreateDate = sysCreateDate;
	}
}
