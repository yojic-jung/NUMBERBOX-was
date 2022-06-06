package com.numberbox.mathinfo.dto;

import java.util.Date;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.numberbox.mathinfo.entity.MathContents;
import com.numberbox.mathinfo.entity.MathTypeInfo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MathContentsDto {
	int contentsNo;
	int unitUniqNo;
	int typeNo;
	UUID userUniqId;
	String contents;
	MultipartFile contentsImg;
	String contentsImgName;
	String solution;
	MultipartFile solutionImg;
	String solutionImgName;
	String imgPath;
	String solutionImgPath;
	String firNo;
	String secNo;
	String thrNo;
	String fourNo;
	String fifNo;
	String multiChoiceType;		//전체 체크해서 바이트 체크
	String answer;
	String choiceAnswer;
	int likeCnt;
	int hateCnt;
	int downCnt;
	String orgSrcRef;
	int orgSrcNo;
	int quesLevel;
	int ansExistStts;
	int svcPosbStts;
	Date sysCreateDate;
	Date sysUpdateDate;
	
	MathTypeInfo mathTypeInfo;
	
	
	public MathContents toEntity() {
		return MathContents.builder().contentsNo(contentsNo).unitUniqNo(unitUniqNo).typeNo(typeNo).contents(contents).contentsImg(contentsImgName)
				.solution(solution).solutionImg(solutionImgName).imgPath(imgPath).solutionImgPath(solutionImgPath).firNo(firNo).secNo(secNo).thrNo(thrNo).fourNo(fourNo).fifNo(fifNo)
				.multiChoiceType(multiChoiceType).answer(answer).choiceAnswer(choiceAnswer).userUniqId(userUniqId).likeCnt(likeCnt).hateCnt(hateCnt)
				.downCnt(downCnt).orgSrcRef(orgSrcRef).orgSrcNo(orgSrcNo).quesLevel(quesLevel).ansExistStts(ansExistStts).svcPosbStts(svcPosbStts)
				.mathTypeInfo(mathTypeInfo)
				.build();
	}
}
