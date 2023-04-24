package com.numberbox.mathinfo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MathUnitInfoDto implements Comparable<MathUnitInfoDto> {
	
	@Override
    public int compareTo(MathUnitInfoDto muiDto) {
        return this.unitUniqNo-muiDto.getUnitUniqNo();
    }
	public int unitUniqNo;
	public String subject;
	public String firUnit;
	public String secUnit;
	public String thrUnit;
}
