package com.numberbox.mathdocs.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.numberbox.mathdocs.dto.MathDocsPaperDto;
import com.numberbox.mathdocs.dto.MathDocsUsageDto;
import com.numberbox.mathdocs.service.MathDocsSevice;
import com.numberbox.mathinfo.dto.MathContentsDto;

@RestController
@RequestMapping("/mathDocs")
public class MathDocsController {

	@Autowired
	MathDocsSevice mathDocsSevice;
	
	@GetMapping("/mathDocs")
	public HashMap<String, Object> contentsInfo(HttpServletRequest request) {
		String unitUniqNoAndTypeNo = (String)request.getParameter("unitUniqNoAndTypeNoList");
		int quesLevel = Integer.parseInt(request.getParameter("quesLevel"));
		int conCnt = Integer.parseInt(request.getParameter("conCnt"));
		List<MathContentsDto> list = mathDocsSevice.takeMathSubjectInfo(unitUniqNoAndTypeNo, quesLevel, conCnt);
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("mathContentsList", list);
		return map;
	}
	
	@GetMapping("/similarContents")
	public HashMap<String, Object> similarContents(HttpServletRequest request) {
		int unitUniqNo = Integer.parseInt(request.getParameter("unitUniqNo").trim());
		int typeNo = Integer.parseInt(request.getParameter("typeNo").trim());
		List<MathContentsDto> list = mathDocsSevice.takeSimilarContents(unitUniqNo, typeNo);
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("mathSimilarConList", list);
		return map;
	}
	
	@PostMapping("/registerMathDocsPaper")
	public HashMap<String, Object> registerMathDocsPaper(MathDocsPaperDto mathDocsPaperDto, HttpServletRequest request) {
		HashMap<String, Object> map = mathDocsSevice.registerMathDocsPaper(mathDocsPaperDto);
		return map;
	}
	
	@PostMapping("/registerMathDocsUsage")
	public HashMap<String, Object> registerMathDocsUsage(MathDocsUsageDto mathDocsUsageDto, HttpServletRequest request) {
		HashMap<String, Object> map = mathDocsSevice.registerMathDocsUsage(mathDocsUsageDto);
		return map;
	}
	
	@GetMapping("/myMathDocs")
	public HashMap<String, Object> myMathDocs(HttpServletRequest request) {
		HashMap<String, Object> map = mathDocsSevice.myMathDocs();
		return map;
	}
	
	@GetMapping("/delMyMathDocs")
	public HashMap<String, Object> delMyMathDocs(HttpServletRequest request) {
		String docsNo = (String)request.getParameter("docsNo");
		HashMap<String, Object> map = mathDocsSevice.delMyMathDocs(Integer.parseInt(docsNo));
		return map;
	}
	
	@GetMapping("/mathDocsByMyMathDocsPage")
	public HashMap<String, Object> mathDocsByMyMathDocsPage(HttpServletRequest request) {
		String docsNo = (String)request.getParameter("docsNo");
		HashMap<String, Object> map = mathDocsSevice.mathDocsByMyMathDocsPage(Integer.parseInt(docsNo));
		return map;
	}
	
}
