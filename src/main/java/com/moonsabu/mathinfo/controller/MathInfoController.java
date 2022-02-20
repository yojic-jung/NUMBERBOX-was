package com.moonsabu.mathinfo.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.moonsabu.mathinfo.service.MathContentsInfoService;

@RestController
public class MathInfoController {
	
	@Autowired
	MathContentsInfoService mathContentsInfoService;
	
	@GetMapping("/unitInfo")
	public HashMap<String, Object> contentsInfo() {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("mathSubjectInfo", mathContentsInfoService.takeMathSubjectInfo());
		map.put("mathFirUnitInfo", mathContentsInfoService.takeMathFirUnitInfo());
		map.put("mathSecUnitInfo", mathContentsInfoService.takeMathSecUnitInfo());
		map.put("mathThrUnitInfo", mathContentsInfoService.takeMathThrUnitInfo());
		//map.put("mathTypeInfo", mathContentsInfoService.takeMathTypeInfo());
		return map;
	}
	
	@GetMapping("/typeInfo")
	public HashMap<String, Object> typeInfo(HttpServletRequest request) {
		String unitUniqNo = (String) request.getParameter("unitUniqNo");
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("mathTypeInfo", mathContentsInfoService.takeMathTypeInfo(unitUniqNo));
		return map;
	}
	
	
	@GetMapping("/takeShortCutKey")
	public HashMap<String, Object> takeShortCutKey() {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("shortCutKey", mathContentsInfoService.takeShortCutKey());
		map.put("shortCutKeyHigh1", mathContentsInfoService.takeShortCutKeyHigh1());
		map.put("shortCutKeyEtc", mathContentsInfoService.takeShortCutKeyEtc());
		
		return map;
	}
}
