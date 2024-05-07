package com.numberbox.mathinfo.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.numberbox.members.dto.MembersProfileDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MathContentsModel {

	int contentsNo;
	int unitUniqNo;
	int typeNo;
	@JsonIgnore
	UUID userUniqId;

	String contents;
	String contentsImg;

	String solution;
	String solutionImg;

	String imgPath;

	String solutionImgPath;

	String firNo;
	String secNo;
	String thrNo;
	String fourNo;
	String fifNo;

	String multiChoiceType;

	String answer;
	String choiceAnswer; // 전체 체크해서 바이트 체크

	String orgSrcRef;
	int orgSrcNo;
	int quesLevel;
	int ansExistStts;
	int svcPosbStts;
	int contentsClassify;
	int orgContentsNo;
	int transConCnt;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMddHHmmss", timezone = "Asia/Seoul")
	LocalDateTime sysCreateDate;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMddHHmmss", timezone = "Asia/Seoul")
	LocalDateTime sysUpdateDate;

	MembersProfileDto membersProfile;
	MathUnitInfoDto mathUnitInfo;
	MathTypeInfoDto mathTypeInfo;
	List<MathContentsIpsiDto> mathContentsIpsi;
	List<MathContentsCompDto> mathContentsComp;
	List<MathContentsLicenseDto> mathContentsLicense;
}
