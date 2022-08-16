package com.numberbox.mathinfo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MathContentsDtoForDocs implements Comparable<MathContentsDtoForDocs>{
	
	@Override
    public int compareTo(MathContentsDtoForDocs mathContents) {
        return Integer.compare(getQuesLevel(), mathContents.getQuesLevel());
    }

	Integer contentsNo;
	Integer unitUniqNo;
	Integer typeNo;
	
	String contents;
	String contentsImg;
	String imgPath;
	String solution;
	String solutionImg;
	String solutionImgPath;
	String firNo;
	String secNo;
	String thrNo;
	String fourNo;
	String fifNo;
	String multiChoiceType;		//전체 체크해서 바이트 체크
	String answer;
	String choiceAnswer;
	
	Integer quesLevel;
	Integer ansExistStts;
	Integer contentsClassify;
	
	String subject;
	String firUnit;
	String secUnit;
	String thrUnit;
	
	String quesType;
}
