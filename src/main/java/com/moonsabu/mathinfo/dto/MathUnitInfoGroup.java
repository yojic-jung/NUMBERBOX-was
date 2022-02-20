package com.moonsabu.mathinfo.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MathUnitInfoGroup {

	public int unitUniqNo;
	
	public String parentVal;
	
	public String mainVal;
	
    public MathUnitInfoGroup(int unitUniqNo, String mainVal) {
        this.unitUniqNo = unitUniqNo;
        this.mainVal = mainVal;
    }
    
    public MathUnitInfoGroup(int unitUniqNo, String parentVal, String mainVal) {
        this.unitUniqNo = unitUniqNo;
        this.parentVal = parentVal;
        this.mainVal = mainVal;
    }
    
}
