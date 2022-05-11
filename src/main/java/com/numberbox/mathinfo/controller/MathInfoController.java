package com.numberbox.mathinfo.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.numberbox.mathinfo.dto.MathContentsDto;
import com.numberbox.mathinfo.dto.MathResourceDto;
import com.numberbox.mathinfo.entity.MathContents;
import com.numberbox.mathinfo.entity.MathResourceCate;
import com.numberbox.mathinfo.entity.MathResourceMenu;
import com.numberbox.mathinfo.service.MathContentsInfoService;
import com.numberbox.mathinfo.service.MathResourceService;

@RestController
@RequestMapping("/mathInfo")
public class MathInfoController {
	
	@Autowired
	MathContentsInfoService mathContentsInfoService;
	@Autowired
	MathResourceService mathResourceService;
	
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
	public HashMap<String, Object> registerContents(@ModelAttribute MathContentsDto mathContentsDto, HttpServletRequest request) throws IllegalArgumentException, IllegalAccessException, IllegalStateException, IOException {
		String accessToken = (String)request.getHeader("access-token");
		String path = request.getSession().getServletContext().getRealPath("/static");	//임시용, 배포 이후 프로젝트 바깥 경로로 설정하는게 좋음(배포용 개발용 따로 관리 필요)
		boolean isSaved = mathContentsInfoService.registerContents(mathContentsDto, path, accessToken);
		HashMap<String, Object> map = new HashMap<String, Object>();
		if(isSaved) {
			map.put("saveSuccess",isSaved);
		}else {
			map.put("existMsg", true);
			map.put("serverMsg", "본인이 만든 문제 외의 문제는 수정할 수 없습니다.");
		}
		return map;
	}
	
	@PostMapping("/takeContents")
	public HashMap<String, Object> takeContents(@ModelAttribute MathContentsDto mathContentsDto, HttpServletRequest request) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("isSearched", true);
		List<MathContents> list = mathContentsInfoService.takeContents(mathContentsDto);
		map.put("mathContents", list);
		return map;
	}
	
	@GetMapping("/takeMyContents")
	public HashMap<String, Object> takeMyContents(HttpServletRequest request) {
		String contentsNo = (String)request.getParameter("contentsno");
		HashMap<String, Object> map = mathContentsInfoService.takeMyContents(Integer.parseInt(contentsNo));
		//본인이 만든 문제인 경우
		if(!(boolean)map.get("existMsg")) {
			MathContents mathContents = (MathContents)map.get("myContents");
			map.put("myUnitInfo", mathContentsInfoService.takeUnitInfoByUnitUniqNo(mathContents.getUnitUniqNo()));
			map.put("myTypeInfo", mathContentsInfoService.takeMathTypeInfo(Integer.toString(mathContents.getUnitUniqNo())));
		}
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
		int updateCond = mathContentsInfoService.changeConOrSolImg(mathContentsDto, path, mathContentsDto.getUserNo());
		if(updateCond == -1) {
			map.put("existMsg", true);
			map.put("serverMsg", "본인이 만든 문제에 등록된 이미지가 아닌 경우 이미지 수정이 불가능합니다.");
		}
		map.put("updateCond", updateCond);
		return map;
	}
	
	@PostMapping("/delConOrSolImg")
	public HashMap<String, Object> delConOrSolImg(@RequestParam int contentsNo, @RequestParam String conOrSol, @RequestParam long userNo, HttpServletRequest request) {
		String path = request.getSession().getServletContext().getRealPath("/static");	//임시용, 배포 이후 프로젝트 바깥 경로로 설정하는게 좋음(배포용 개발용 따로 관리 필요)
		HashMap<String, Object> map = new HashMap<String, Object>();
		int updateCond = mathContentsInfoService.delConOrSolImg(contentsNo, conOrSol, path, userNo);
		if(updateCond == -1) {
			map.put("existMsg", true);
			map.put("serverMsg", "본인이 만든 문제에 등록된 이미지가 아닌 경우 이미지 수정이 불가능합니다.");
		}
		map.put("updateCond", updateCond);
		return map;
	}
	
	@GetMapping("/takeResourceMenu")
	public HashMap<String, Object> takeResourceMenu() {
		HashMap<String, Object> map = new HashMap<String, Object>();
		List<MathResourceMenu> resourceMenuList = mathResourceService.takeResourceMenu();
		map.put("resourceMenuList", resourceMenuList);
		return map;
	}
	
	@PostMapping("/registerResource")
	public HashMap<String, Object> registerResource(MathResourceDto mathResourceDto, HttpServletRequest request) throws IllegalStateException, IOException {
		HashMap<String, Object> map = new HashMap<String, Object>();
		String path = request.getSession().getServletContext().getRealPath("/static");	//임시용, 배포 이후 프로젝트 바깥 경로로 설정하는게 좋음(배포용 개발용 따로 관리 필요)
		mathResourceService.registerResource(path, mathResourceDto);
		map.put("isSuccess", true);
		return map;
	}
	
	@GetMapping("/takeResource")
	public HashMap<String, Object> takeResource(@RequestParam int mainCateNo) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		List<MathResourceCate> resourceMenuList = mathResourceService.takeResource(mainCateNo);
		map.put("resourceList", resourceMenuList);
		return map;
	}
	
	
}
