package com.numberbox.mathinfo.controller;

import java.io.FileNotFoundException;
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

import com.numberbox.mathinfo.dto.MathContentsCompListDto;
import com.numberbox.mathinfo.dto.MathContentsDto;
import com.numberbox.mathinfo.dto.MathContentsModel;
import com.numberbox.mathinfo.dto.MathResourceDto;
import com.numberbox.mathinfo.dto.MathTypeInfoListDto;
import com.numberbox.mathinfo.dto.MathTypeInfoModel;
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
		//map.put("mathFirUnitInfo", mathContentsInfoService.takeMathFirUnitInfo());
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
	
	@GetMapping("/typeInfoList")
	public HashMap<String, Object> typeInfoList(HttpServletRequest request) {
		String unitUniqNoList = (String) request.getParameter("unitUniqNoList");
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("mathTypeInfoList", mathContentsInfoService.takeMathTypeInfoList(unitUniqNoList));
		return map;
	}
	
	@GetMapping("/takeShortCutKey")
	public HashMap<String, Object> takeShortCutKey() {
		return mathContentsInfoService.takeShortCutKey();
	}
	
	@PostMapping("/makeContents")
	public HashMap<String, Object> makeContents(@ModelAttribute MathContentsDto mathContentsDto, HttpServletRequest request) throws IllegalArgumentException, IllegalAccessException, IllegalStateException, IOException {
		boolean isUpdtMode = mathContentsDto.getContentsNo() != 0;
		String accessToken = (String)request.getHeader("access-token");
		String path = request.getSession().getServletContext().getRealPath("/static");	//임시용, 배포 이후 프로젝트 바깥 경로로 설정하는게 좋음(배포용 개발용 따로 관리 필요)
		HashMap<String, Object> map = mathContentsInfoService.registerContents(mathContentsDto, path, accessToken, false);
		if(isUpdtMode) {
			MathContentsModel mathContents = mathContentsInfoService.takeMathContents(mathContentsDto.getContentsNo());
			map.put("mathContents", mathContents);
		}
		
		if(!(boolean)map.get("saveSuccess")) {
			map.put("existMsg", true);
			map.put("serverMsg", "본인이 만든 문제 외의 문제는 수정할 수 없습니다.");
		}
		return map;
	}
	
	
	@PostMapping("/registerContents")
	public HashMap<String, Object> registerContents(@ModelAttribute MathContentsDto mathContentsDto, HttpServletRequest request) throws IllegalArgumentException, IllegalAccessException, IllegalStateException, IOException {
		boolean isUpdtMode = mathContentsDto.getContentsNo() != 0;
		String accessToken = (String)request.getHeader("access-token");
		String path = request.getSession().getServletContext().getRealPath("/static");	//임시용, 배포 이후 프로젝트 바깥 경로로 설정하는게 좋음(배포용 개발용 따로 관리 필요)
		HashMap<String, Object> map = mathContentsInfoService.registerContents(mathContentsDto, path, accessToken, true);
		if(isUpdtMode) {
			MathContentsModel mathContents = mathContentsInfoService.takeMathContents(mathContentsDto.getContentsNo());
			map.put("mathContents", mathContents);
		}
		
		if(!(boolean)map.get("saveSuccess")) {
			map.put("existMsg", true);
			map.put("serverMsg", "본인이 만든 문제 외의 문제는 수정할 수 없습니다.");
		}
		return map;
	}
	
	@PostMapping("/takeContentsList")
	public HashMap<String, Object> takeContentsList(@ModelAttribute MathContentsDto mathContentsDto, HttpServletRequest request) {
		HashMap<String, Object> map = mathContentsInfoService.takeContentsList(mathContentsDto, null);
		map.put("isSearched", true);
		return map;
	}
	
	
	@GetMapping("/takeContentsListByContentsNo")
	public HashMap<String, Object> takeContentsListByContentsNo(HttpServletRequest request) {
		String contentsNo = (String)request.getParameter("contentsno");
		HashMap<String, Object>	map = mathContentsInfoService.takeContentsList(null, contentsNo);
		map.put("isSearched", true);
		return map;
	}
	
	@PostMapping("/takeWorkContentsList")
	public HashMap<String, Object> takeWorkContentsList(@ModelAttribute MathContentsDto mathContentsDto, HttpServletRequest request) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("isSearched", true);
		List<MathContentsModel> list = mathContentsInfoService.takeWorkContentsList(mathContentsDto, null);
		map.put("mathContents", list);
		return map;
	}
	
	@GetMapping("/takeWorkContentsListByContentsNo")
	public HashMap<String, Object> takeWorkContentsListByContentsNo(HttpServletRequest request) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		String contentsNo = (String)request.getParameter("contentsno");
		map.put("isSearched", true);
		List<MathContentsModel> list = mathContentsInfoService.takeWorkContentsList(null, contentsNo);
		map.put("mathContents", list);
		return map;
	}
	
	@GetMapping("/takeMyWorkContents")
	public HashMap<String, Object> takeMyContents(HttpServletRequest request) {
		String contentsNo = (String)request.getParameter("contentsno");
		HashMap<String, Object> map = mathContentsInfoService.takeMyWorkContents(Integer.parseInt(contentsNo));
		//본인이 만든 문제인 경우
		if(!(boolean)map.get("existMsg")) {
			MathContentsModel mathContents = (MathContentsModel)map.get("myContents");
			map.put("myUnitInfo", mathContentsInfoService.takeUnitInfoByUnitUniqNo(mathContents.getUnitUniqNo()));
			map.put("myTypeInfo", mathContentsInfoService.takeMathTypeInfo(Integer.toString(mathContents.getUnitUniqNo())));
		}
		return map;
	}
	
	@GetMapping("/takeContentsByContentsNo")
	public HashMap<String, Object> takeWorkContentsForTrans(HttpServletRequest request) {
		String contentsNo = (String)request.getParameter("contentsno");
		HashMap<String, Object> map = mathContentsInfoService.takeContentsByContentsNo(Integer.parseInt(contentsNo));
		return map;
	}
	
	
	@GetMapping("/takeMyContentsList")
	public HashMap<String, Object> takeMyContentsList(HttpServletRequest request) {
		HashMap<String, Object> map = mathContentsInfoService.takeMyContentsList(0);
		return map;
	}
	
	@GetMapping("/takeUserContentsList")
	public HashMap<String, Object> takeUserContentsList(@RequestParam int userNo, HttpServletRequest request) {
		HashMap<String, Object> map = mathContentsInfoService.takeMyContentsList(userNo);
		return map;
	}
	
	@GetMapping("/takeMyRepo")
	public HashMap<String, Object> takeMyRepo(HttpServletRequest request) {
		HashMap<String, Object> map = mathContentsInfoService.takeMyRepo();
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
	
	@GetMapping("/mathTypeInfo")
	public HashMap<String, Object> myUnitTypeInfoOnlyOne(HttpServletRequest request) {
		String unitUniqNo = request.getParameter("unitUniqNo");
		String typeNo = request.getParameter("typeNo");
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("mathTypeInfo", mathContentsInfoService.takeMathTypeInfoOnlyOne(unitUniqNo, typeNo));
		return map;
	}
	
	@PostMapping("/changeConOrSolImg")
	public HashMap<String, Object> changeConOrSolImg(@ModelAttribute MathContentsDto mathContentsDto, HttpServletRequest request) throws IllegalStateException, IOException {
		String path = request.getSession().getServletContext().getRealPath("/static");	//임시용, 배포 이후 프로젝트 바깥 경로로 설정하는게 좋음(배포용 개발용 따로 관리 필요)
		HashMap<String, Object> map = new HashMap<String, Object>();
		int updateCond = mathContentsInfoService.changeConOrSolImg(mathContentsDto, path);
		if(updateCond == -1) {
			map.put("existMsg", true);
			map.put("serverMsg", "본인이 만든 문제에 등록된 이미지가 아닌 경우 이미지 수정이 불가능합니다.");
		}else {
			//모달창 전달 위해 수정된 객체 전달
			MathContentsModel mathContents = mathContentsInfoService.takeMathContents(mathContentsDto.getContentsNo());
			map.put("mathContents", mathContents);
		}
		map.put("updateCond", updateCond);
		return map;
	}
	
	@PostMapping("/delConOrSolImg")
	public HashMap<String, Object> delConOrSolImg(@RequestParam int contentsNo, @RequestParam String conOrSol, HttpServletRequest request) {
		String path = request.getSession().getServletContext().getRealPath("/static");	//임시용, 배포 이후 프로젝트 바깥 경로로 설정하는게 좋음(배포용 개발용 따로 관리 필요)
		HashMap<String, Object> map = new HashMap<String, Object>();
		int updateCond = mathContentsInfoService.delConOrSolImg(contentsNo, conOrSol, path);
		if(updateCond == -1) {
			map.put("existMsg", true);
			map.put("serverMsg", "본인이 만든 문제에 등록된 이미지가 아닌 경우 이미지 수정이 불가능합니다.");
		}else {
			//모달창 전달 위해 수정된 객체 전달
			MathContentsModel mathContents = mathContentsInfoService.takeMathContents(contentsNo);
			map.put("mathContents", mathContents);
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
		String path = request.getSession().getServletContext().getRealPath("/static");	//임시용, 배포 이후 프로젝트 바깥 경로로 설정하는게 좋음(배포용 개발용 따로 관리 필요)
		HashMap<String, Object>  map = mathResourceService.registerResource(path, mathResourceDto);
		return map;
	}
	
	@PostMapping("/updateResource")
	public HashMap<String, Object> updateResource(MathResourceDto mathResourceDto, HttpServletRequest request) throws IllegalStateException, IOException {
		String path = request.getSession().getServletContext().getRealPath("/static");	//임시용, 배포 이후 프로젝트 바깥 경로로 설정하는게 좋음(배포용 개발용 따로 관리 필요)
		HashMap<String, Object>  map = mathResourceService.updateResource(path, mathResourceDto);
		if((boolean)map.get("isSuccess") == true) {
			HashMap<String, Object>  map2 = mathResourceService.takeNewMathResource(mathResourceDto.getResourceNo());
			map.put("newMathResource", map2.get("newMathResource"));
		}
		
		return map;
	}
	
	@GetMapping("/takeResource")
	public HashMap<String, Object> takeResource(@RequestParam int mainCateNo, HttpServletRequest request) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		String path = request.getSession().getServletContext().getRealPath("/static");	//임시용, 배포 이후 프로젝트 바깥 경로로 설정하는게 좋음(배포용 개발용 따로 관리 필요)
		List<MathResourceDto> resourceMenuList = mathResourceService.takeResource(mainCateNo, path);
		map.put("resourceList", resourceMenuList);
		return map;
	}
	
	@GetMapping("/takeResourceByResourceNo")
	public HashMap<String, Object> takeResourceByResourceNo(@RequestParam int resourceNo, HttpServletRequest request) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		String path = request.getSession().getServletContext().getRealPath("/static");	//임시용, 배포 이후 프로젝트 바깥 경로로 설정하는게 좋음(배포용 개발용 따로 관리 필요)
		List<MathResourceDto> resourceMenuList = mathResourceService.takeResourceByResourceNo(resourceNo, path);
		map.put("resourceList", resourceMenuList);
		return map;
	}
	
	@GetMapping("/takeMyResource")
	public HashMap<String, Object> takeMyResource(HttpServletRequest request) throws FileNotFoundException, IOException {
		HashMap<String, Object> map = new HashMap<String, Object>();
		List<MathResourceDto> myResource = mathResourceService.takeMyResource();
		List<MathResourceMenu> resourceMenuList = mathResourceService.takeResourceMenu();
		map.put("myResourceList", myResource);
		map.put("resourceMenuList", resourceMenuList);
		return map;
	}
	
	@GetMapping("/myResourceDel")
	public HashMap<String, Object> myResourceDel(HttpServletRequest request) {
		int resourceNo = Integer.parseInt(request.getParameter("resourceNo"));
		String path = request.getSession().getServletContext().getRealPath("/static");	//임시용, 배포 이후 프로젝트 바깥 경로로 설정하는게 좋음(배포용 개발용 따로 관리 필요)
		HashMap<String, Object> map = mathResourceService.myResourceDel(resourceNo, path);
		return map;
	}
	
	
	@GetMapping("/conSvcSttsChng")
	public HashMap<String, Object> conSvcSttsChng(@RequestParam int contentsNo, @RequestParam int svcStts) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		int isSuccess = mathContentsInfoService.changeSvcStts(contentsNo, svcStts);
		map.put("isSuccess", isSuccess);
		return map;
	}
	
	@PostMapping("/registerCompContents")
	public HashMap<String, Object> registerCompContents(MathContentsCompListDto compContentsList, HttpServletRequest request) throws IllegalStateException, IOException {
		HashMap<String, Object> map = mathContentsInfoService.registerCompContents(compContentsList);
		return map;
	}
	
	@GetMapping("/delCompContents")
	public HashMap<String, Object> delCompContents(@RequestParam int seqNo, @RequestParam int contentsNo) {
		HashMap<String, Object> successObj = mathContentsInfoService.delCompContents(seqNo, contentsNo);
		return successObj;
	}
	
	@GetMapping("/myContentsDel")
	public HashMap<String, Object> myContentsDel(@RequestParam int contentsno) {
		HashMap<String, Object> successObj = mathContentsInfoService.myContentsDel(contentsno);
		return successObj;
	}
	
	@GetMapping("/myRepoDel")
	public HashMap<String, Object> myRepoDel(@RequestParam int contentsno) {
		int isSuccess = mathContentsInfoService.myRepoDel(contentsno);
		HashMap<String, Object> map = new HashMap<String, Object>();
		if(isSuccess==1) {
			map.put("isSuccess", true);
		}else {
			map.put("map.put(key, value);", false);
		}
		return map;
	}
	
	@GetMapping("/likeContents")
	public HashMap<String, Object> likeContents(@RequestParam int contentsno) {
		HashMap<String, Object> successObj = new HashMap<>();
		int success = mathContentsInfoService.likeContents(contentsno);
		successObj.put("isSuccess", success);
		return successObj;
	}
	
	@GetMapping("/putInMyRepo")
	public HashMap<String, Object> putInMyRepo(@RequestParam int contentsno) {
		HashMap<String, Object> successObj = new HashMap<>();
		int success = mathContentsInfoService.putInMyRepo(contentsno);
		successObj.put("isSuccess", success);
		return successObj;
	}
	
	@GetMapping("/takePPtSlideImge")
	public HashMap<String, Object> takePPtSlideImge(HttpServletRequest request) throws FileNotFoundException, IOException {
		int resourceNo = Integer.parseInt(request.getParameter("resourceNo"));
		HashMap<String, Object> successObj = mathResourceService.takePPtSlideImge(resourceNo);
		return successObj;
	}
	
	@PostMapping("/changeQuesType")
	public HashMap<String, Object> chngQuesType(MathTypeInfoModel mathTypeInfoModel) {
		HashMap<String, Object> map = mathContentsInfoService.chngQuesType(mathTypeInfoModel);
		return map;
	}
	
	@GetMapping("/takeConCntByUnitUniqNo")
	public HashMap<String, Object> takeConCntByUnitUniqNo(@RequestParam String unitUniqNo) {
		HashMap<String, Object> map = mathContentsInfoService.takeConCntByUnitUniqNo(unitUniqNo);
		return map;
	}
	
	@GetMapping("/typeDel")
	public HashMap<String, Object> typeDel(@RequestParam String unitUniqNo, @RequestParam String typeNo) {
		HashMap<String, Object> map = mathContentsInfoService.typeDel(unitUniqNo, typeNo);
		return map;
	}
	
	@PostMapping("/mathTypeAdd")
	public HashMap<String, Object> mathTypeAdd(MathTypeInfoModel mathTypeInfoModel) {
		HashMap<String, Object> map = mathContentsInfoService.mathTypeAdd(mathTypeInfoModel);
		return map;
	}
	
	@GetMapping("/contentsMoveFromTo")
	public HashMap<String, Object> contentsMoveFromTo(@RequestParam String fromUnitUniqNo, @RequestParam String fromTypeNo, @RequestParam String toUnitUniqNo, @RequestParam String toTypeNo) {
		HashMap<String, Object> map = mathContentsInfoService.contentsMoveFromTo(fromUnitUniqNo, fromTypeNo, toUnitUniqNo, toTypeNo);
		return map;
	}
	
	@PostMapping("/mathTypeOrderChng")
	public HashMap<String, Object> mathTypeOrderChng(MathTypeInfoListDto mathTypeInfoListDto) {
		HashMap<String, Object> map = mathContentsInfoService.mathTypeOrderChng(mathTypeInfoListDto.getMathTypeInfoModel());
		return map;
	}
	
	
}
