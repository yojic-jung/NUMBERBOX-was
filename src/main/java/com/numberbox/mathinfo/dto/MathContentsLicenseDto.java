package com.numberbox.mathinfo.dto;

import java.time.LocalDateTime;

import com.numberbox.mathinfo.entity.MathContentsLicense;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MathContentsLicenseDto {
	
	int contentsNo;
	int onlineLicStts;
	int perLicStts;
	int perLicPrice;
	int entLicStts;
	int entLicPrice;
	int shareStts;
	
	LocalDateTime sysCreateDate;
	LocalDateTime sysUpdateDate;
	
	public MathContentsLicense toEntity() {
		return MathContentsLicense.builder().contentsNo(contentsNo).onlineLicStts(onlineLicStts).perLicStts(perLicStts).perLicPrice(perLicPrice)
				.entLicStts(entLicStts).entLicPrice(entLicPrice).shareStts(shareStts).build();
	}
}
