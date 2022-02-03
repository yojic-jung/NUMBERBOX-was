package com.moonsabu.mathinfo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moonsabu.mathinfo.entity.FormulKey;
import com.moonsabu.mathinfo.entity.MathTypeInfo;
import com.moonsabu.mathinfo.entity.MathUnitInfoGroup;
import com.moonsabu.mathinfo.repository.FormulKeyRepository;
import com.moonsabu.mathinfo.repository.MathTypeRepository;
import com.moonsabu.mathinfo.repository.MathUnitRepository;

@Service
public class MathContentsInfoService {
	
	@Autowired
	MathUnitRepository mathUnitRepository;
	@Autowired
	MathTypeRepository mathTypeRepository;
	@Autowired
	FormulKeyRepository formulKeyRepository;
	
	public List<MathUnitInfoGroup> takeMathSubjectInfo(){
		return mathUnitRepository.selectSubjectInfo();
	}
	
	public List<MathUnitInfoGroup> takeMathFirUnitInfo(){
		return mathUnitRepository.selectFirUnitInfo();
	}
	
	public List<MathUnitInfoGroup> takeMathSecUnitInfo(){
		return mathUnitRepository.selectSecUnitInfo();
	}
	
	public List<MathUnitInfoGroup> takeMathThrUnitInfo(){
		return mathUnitRepository.selectThrUnitInfo();
	}
	
	public List<MathTypeInfo> takeMathTypeInfo(){
		return mathTypeRepository.findAll();
	}
	
	public List<FormulKey> takeShortCutKey(){
		return formulKeyRepository.findAll();
	}
	
}
