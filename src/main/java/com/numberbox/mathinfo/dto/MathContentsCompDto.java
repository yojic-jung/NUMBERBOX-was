package com.numberbox.mathinfo.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import com.numberbox.mathinfo.entity.MathContentsComp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MathContentsCompDto implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	int seqNo; 
	int contentsNo;
	String orgSrcRef;
	int orgSrcNo;
	Integer orgSrcPage;
	String copyrightYear;
	String mathTypeClassify;
	UUID userUniqId;
	
	Date sysCreateDate;
	Date sysUpdateDate;
	
	public MathContentsComp toEntity() {
		return MathContentsComp.builder().seqNo(seqNo).contentsNo(contentsNo).orgSrcRef(orgSrcRef).orgSrcNo(orgSrcNo).orgSrcPage(orgSrcPage)
				.copyrightYear(copyrightYear).mathTypeClassify(mathTypeClassify).userUniqId(userUniqId).build();
	}
}
