package com.kamcci.numberbox.mathinfo.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kamcci.numberbox.mathinfo.domain.MathConRepoDomain;
import com.kamcci.numberbox.mathinfo.entity.MathConRepoInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MathConRepoInfoDto {

	public MathConRepoDomain mathConRepoDomain;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMddHHmmss", timezone = "Asia/Seoul")
	LocalDateTime sysCreateDate;

	public MathConRepoInfo toEntity() {
		return MathConRepoInfo.builder().mathConRepoDomain(mathConRepoDomain).build();
	}
}
