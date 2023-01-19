package com.numberbox.mathinfo.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.numberbox.mathinfo.entity.MathContentsIpsi;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MathContentsIpsiDto implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	int seqNo; 
	int contentsNo;
	int manageIns;
	int impYear;
	int impMonth;
	int wrongRatio;
	int paperType;
	int oddQuesNum;
	int evenQuesNum;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMddHHmmss", timezone = "Asia/Seoul")
	Date sysCreateDate;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMddHHmmss", timezone = "Asia/Seoul")
	Date sysUpdateDate;
	
	public MathContentsIpsi toEntity() {
		return MathContentsIpsi.builder().seqNo(seqNo).contentsNo(contentsNo).manageIns(manageIns).impYear(impYear).impMonth(impMonth)
				.wrongRatio(wrongRatio).paperType(paperType).oddQuesNum(oddQuesNum).evenQuesNum(evenQuesNum).build();
	}
}
