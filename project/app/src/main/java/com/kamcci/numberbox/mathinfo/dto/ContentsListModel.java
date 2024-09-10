package com.kamcci.numberbox.mathinfo.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContentsListModel {

	int contentsNo;
	int unitUniqNo;
	int typeNo;

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
	String multiChoiceType; // 전체 체크해서 바이트 체크
	String answer;
	String choiceAnswer;

	Integer quesLevel;
	Integer ansExistStts;
	Integer svcPosbStts;
	Integer contentsClassify;
	Integer orgContentsNo;
	Integer transConCnt;

	LocalDateTime sysCreateDate;
	LocalDateTime sysUpdateDate;

	// license 테이블 필드값
	Integer onlineLicStts;
	Integer perLicStts;
	Integer perLicPrice;
	Integer entLicStts;
	Integer entLicPrice;
	Integer shareStts;

	// membersProfile 테이블 필드값
	private long userNo;
	public String nickname;
	public String profileImgName;
	public String profileImgPath;

	// mathUnitInfo 정보
	String subject;
	String firUnit;
	String secUnit;
	String thrUnit;

}
