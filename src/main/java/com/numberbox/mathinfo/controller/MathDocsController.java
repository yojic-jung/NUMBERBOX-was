package com.numberbox.mathinfo.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.numberbox.mathinfo.dto.MathContentsDto;
import com.numberbox.mathinfo.service.MathDocsSevice;

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
	
}
