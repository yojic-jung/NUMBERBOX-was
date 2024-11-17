package com.kamcci.numberbox.mathinfo.dto;

import com.kamcci.numberbox.mathinfo.domain.MathConLikeDomain;
import com.kamcci.numberbox.mathinfo.entity.MathConLikeInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MathConLikeInfoDto {

	MathConLikeDomain mathConLikeDomain;

	public MathConLikeInfo toEntity() {
		return MathConLikeInfo.builder().mathConLikeDomain(mathConLikeDomain).build();
	}
}
