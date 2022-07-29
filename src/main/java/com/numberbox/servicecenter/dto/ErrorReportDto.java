package com.numberbox.servicecenter.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.numberbox.serivcecenter.entity.ErrorReport;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorReportDto {
	public int reportId;
	public int errType;
	public int contentsNo;
	public UUID reportUser;
	public String reportContents;
	public UUID replyUser;
	public String replyContents;
	
	MultipartFile firstImgFile;
	public String firstImgPath;
	public String firstImgName;
	
	MultipartFile secondImgFile;
	public String secondImgPath;
	public String secondImgName;
	
	MultipartFile thirdImgFile;
	public String thirdImgPath;
	public String thirdImgName;
	
	public int reportStts;
	
	LocalDateTime sysUpdateDate;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
	LocalDateTime sysCreateDate;
	
	public ErrorReport toEntity() {
		return ErrorReport.builder().reportId(reportId).errType(errType).contentsNo(contentsNo).reportUser(reportUser).reportContents(reportContents)
				.replyUser(replyUser).replyContents(replyContents).firstImgPath(firstImgPath).firstImgName(firstImgName).secondImgPath(secondImgPath).secondImgName(secondImgName)
				.thirdImgPath(thirdImgPath).thirdImgName(thirdImgName).reportStts(reportStts).sysUpdateDate(sysUpdateDate).sysCreateDate(sysCreateDate)
				.build();
	}
}
