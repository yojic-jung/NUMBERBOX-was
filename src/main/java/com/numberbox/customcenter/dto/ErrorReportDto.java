package com.numberbox.customcenter.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.numberbox.customcenter.entity.ErrorReport;

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
	
	LocalDateTime sysUpdateDate;
	LocalDateTime sysCreateDate;
	
	public ErrorReport toEntity() {
		return ErrorReport.builder().reportId(reportId).errType(errType).contentsNo(contentsNo).reportUser(reportUser).reportContents(reportContents)
				.replyUser(replyUser).replyContents(replyContents).sysUpdateDate(sysUpdateDate).sysCreateDate(sysCreateDate).build();
	}
}
