package com.numberbox.mathdocs.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.numberbox.mathdocs.entity.MathDocsUsage;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MathDocsUsageDto {

	int docsNo;
	String contentsNoList;
	
	@JsonIgnore
	UUID userUniqId;
	
	String docsGrade;
	String docsTitle;
	String docsSubTitle;
	String docsOwner;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy년 MM월 dd일", timezone = "Asia/Seoul")
	LocalDateTime sysCreateDate;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd", timezone = "Asia/Seoul")
	LocalDateTime sysUpdateDate;
	
	public MathDocsUsage toEntity() {
		return MathDocsUsage.builder().docsNo(docsNo).contentsNoList(contentsNoList).userUniqId(userUniqId).docsGrade(docsGrade)
				.docsTitle(docsTitle).docsSubTitle(docsSubTitle).docsOwner(docsOwner)
				.build();
	}
}
