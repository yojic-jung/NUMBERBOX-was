package com.kamcci.numberbox.convert.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kamcci.numberbox.convert.entity.HwpConvertContentsStatistic;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HwpConvertContentsStatisticDto {
	Long seqNo;

	Long convertNo;

	@JsonIgnore
	UUID userUniqId;

	String convertFileName;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy년 MM월 dd일", timezone = "Asia/Seoul")
	LocalDateTime sysCreateDate;

	public HwpConvertContentsStatistic toEntity() {
		return HwpConvertContentsStatistic.builder().convertNo(convertNo).userUniqId(userUniqId)
				.convertFileName(convertFileName).build();
	}
}
