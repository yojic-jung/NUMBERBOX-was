package com.numberbox.common.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.numberbox.common.entity.TmpImgFileInfo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TmpImgFileInfoDto {
	Long seqNo;

	@JsonIgnore
	UUID userUniqId;

	/*
	 * 10 : 수식편집기 이미지 비동기 업로드 또는 base64 복붙으로 들어오는 경우 (저장 폴더 루트 경로는 editorImgUpld) 
	 * 11 : hwpToHtml 파일변환에서 들어오는 경우(저장 폴더 루트 경로는 hwpToHtml)
	 */
	int actionId;

	/*
	 * [imgPathCode 생성 규칙 ]
	 * 1. imgPathCode는 8자리 코드 
	 * 2. 처음 두자리는 actionId 
	 * 3. 뒤 6자리는 연월(yyyyMM 형식)
	 */
	int imgPathCode;

	String imgPath;

	String imgFileName;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy년 MM월 dd일", timezone = "Asia/Seoul")
	LocalDateTime sysCreateDate;

	public TmpImgFileInfo toEntity() {
		return TmpImgFileInfo.builder().userUniqId(userUniqId).actionId(actionId).imgPathCode(imgPathCode)
				.imgPath(imgPath).imgFileName(imgFileName).build();
	}
}
