package com.numberbox.mathinfo.controller;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.numberbox.mathinfo.dto.MathContentsDto;
import com.numberbox.mathinfo.entity.MathContents;
import com.numberbox.mathinfo.service.MathContentsInfoService;

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
	
	@PostMapping("/registerContents")
	public HashMap<String, Object> datatest(@ModelAttribute MathContentsDto mathContentsDto, HttpServletRequest request) throws IllegalArgumentException, IllegalAccessException, IllegalStateException, IOException {
		String path = request.getSession().getServletContext().getRealPath("/static");	//임시용, 배포 이후 프로젝트 바깥 경로로 설정하는게 좋음(배포용 개발용 따로 관리 필요)
		boolean isSaved = mathContentsInfoService.registerContents(mathContentsDto, path);
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("saveSuccess",isSaved);
		return map;
	}
	
	@PostMapping("/takeContents")
	public HashMap<String, Object> takeContents(@ModelAttribute MathContentsDto mathContentsDto) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("isSearched", true);
		map.put("mathContents", mathContentsInfoService.takeContents(mathContentsDto));
		return map;
	}
	
	
	@GetMapping("/takeMyContents")
	public HashMap<String, Object> takeMyContents(HttpServletRequest request) {
		String contentsNo = (String)request.getParameter("contentsno");
		HashMap<String, Object> map = new HashMap<String, Object>();
		MathContents mathContents = mathContentsInfoService.takeMyContents(Integer.parseInt(contentsNo));
		map.put("myContents", mathContents);
		map.put("myUnitInfo", mathContentsInfoService.takeUnitInfoByUnitUniqNo(mathContents.getUnitUniqNo()));
		map.put("myTypeInfo", mathContentsInfoService.takeMathTypeInfo(Integer.toString(mathContents.getUnitUniqNo())));
		return map;
	}
	
	
	@GetMapping("/myUnitTypeInfo")
	public HashMap<String, Object> myUnitTypeInfo(HttpServletRequest request) {
		int unitUniqNo = Integer.parseInt(request.getParameter("unitUniqNo"));
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("myUnitInfo", mathContentsInfoService.takeUnitInfoByUnitUniqNo(unitUniqNo));
		map.put("myTypeInfo", mathContentsInfoService.takeMathTypeInfo(Integer.toString(unitUniqNo)));
		return map;
	}
	
	@PostMapping("/changeConOrSolImg")
	public HashMap<String, Object> changeConOrSolImg(@ModelAttribute MathContentsDto mathContentsDto, HttpServletRequest request) throws IllegalStateException, IOException {
		String path = request.getSession().getServletContext().getRealPath("/static");	//임시용, 배포 이후 프로젝트 바깥 경로로 설정하는게 좋음(배포용 개발용 따로 관리 필요)
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("updateCond", mathContentsInfoService.changeConOrSolImg(mathContentsDto, path));
		return map;
	}
	
	@PostMapping("/delConOrSolImg")
	public HashMap<String, Object> delConOrSolImg(@RequestParam int contentsNo, @RequestParam String conOrSol, HttpServletRequest request) {
		String path = request.getSession().getServletContext().getRealPath("/static");	//임시용, 배포 이후 프로젝트 바깥 경로로 설정하는게 좋음(배포용 개발용 따로 관리 필요)
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("updateCond", mathContentsInfoService.delConOrSolImg(contentsNo, conOrSol, path));
		return map;
	}
}
