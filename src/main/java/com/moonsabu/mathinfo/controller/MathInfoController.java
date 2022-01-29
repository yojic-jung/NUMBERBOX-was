package com.moonsabu.mathinfo.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.moonsabu.mathinfo.service.MathContentsInfoService;

@RestController
public class MathInfoController {
	
	@Autowired
	MathContentsInfoService mathContentsInfoService;
	
	@GetMapping("/unitTypeInfo")
	public HashMap<String, Object> contentsInfo() {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("mathSubjectInfo", mathContentsInfoService.takeMathSubjectInfo());
		map.put("mathFirUnitInfo", mathContentsInfoService.takeMathFirUnitInfo());
		map.put("mathSecUnitInfo", mathContentsInfoService.takeMathSecUnitInfo());
		map.put("mathThrUnitInfo", mathContentsInfoService.takeMathThrUnitInfo());
		map.put("mathTypeInfo", mathContentsInfoService.takeMathTypeInfo());
		return map;
	}
}
