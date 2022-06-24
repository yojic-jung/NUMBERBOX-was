package com.numberbox.mathinfo.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.numberbox.mathinfo.entity.MathContents;
import com.numberbox.mathinfo.entity.MathContentsComp;
import com.numberbox.mathinfo.entity.MathContentsLicense;
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
	String userUniqIdStr;
	
	String contents;
	MultipartFile contentsImgFile;
	String contentsImg;
	String solution;
	MultipartFile solutionImgFile;
	String solutionImg;
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
	
	int quesLevel;
	int ansExistStts;
	int svcPosbStts;
	int contentsClassify;
	int orgContentsNo;
	int transConCnt;
	
	// comp 테이블 필드값
	int mathContentsCompSeqNo;
	String orgSrcRef;
	int orgSrcNo;
	int orgSrcPage;
	String copyrightYear;
	String mathTypeClassify;
	
	// license 테이블 필드값
	int onlineLicStts;
	int perLicStts;
	int perLicPrice;
	int entLicStts;
	int entLicPrice;
	int shareStts;
	
	LocalDateTime sysCreateDate;
	LocalDateTime sysUpdateDate;
	
	MathTypeInfo mathTypeInfo;
	
	public MathContents toEntity() {
		return MathContents.builder().contentsNo(contentsNo).unitUniqNo(unitUniqNo).typeNo(typeNo).contents(contents).contentsImg(contentsImg)
				.solution(solution).solutionImg(solutionImg).imgPath(imgPath).solutionImgPath(solutionImgPath).firNo(firNo).secNo(secNo).thrNo(thrNo).fourNo(fourNo).fifNo(fifNo)
				.multiChoiceType(multiChoiceType).answer(answer).choiceAnswer(choiceAnswer).userUniqId(userUniqId)
				.quesLevel(quesLevel).ansExistStts(ansExistStts).svcPosbStts(svcPosbStts).contentsClassify(contentsClassify).orgContentsNo(orgContentsNo).transConCnt(transConCnt)
				.mathTypeInfo(mathTypeInfo)
				.build();
	}
	
	public MathContentsComp toCompEntity() {
		return MathContentsComp.builder().seqNo(mathContentsCompSeqNo).contentsNo(contentsNo).userUniqId(userUniqId)
				.orgSrcRef(orgSrcRef).orgSrcNo(orgSrcNo).orgSrcPage(orgSrcPage).copyrightYear(copyrightYear).mathTypeClassify(mathTypeClassify)
				.build();
	}
	
	public MathContentsLicense toLicenseEntity() {
		return MathContentsLicense.builder().contentsNo(contentsNo).onlineLicStts(onlineLicStts).perLicStts(perLicStts).perLicPrice(perLicPrice)
				.entLicStts(entLicStts).entLicPrice(entLicPrice).shareStts(shareStts).build();
	}
}
