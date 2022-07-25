package com.numberbox.customcenter.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.numberbox.customcenter.dto.ErrorReportDto;
import com.numberbox.customcenter.service.CustomCenterService;

@RestController
@RequestMapping("/customCenter")
public class CustomCenterController {
	
	@Autowired
	CustomCenterService customCenterService;
	
	@GetMapping("/takeErrReport")
	public HashMap<String, Object> takeErrReport(HttpServletRequest request) {
		int contentsNo = Integer.parseInt(request.getParameter("contentsNo"));
		int errType = Integer.parseInt(request.getParameter("errType"));
		HashMap<String, Object> map = customCenterService.takeErrReport(contentsNo, errType);
		return map;
	}
	
	@PostMapping("/registerError")
	public HashMap<String, Object> contentsInfo(@ModelAttribute ErrorReportDto errorReportDto, HttpServletRequest request) {
		HashMap<String, Object> map = customCenterService.reportError(errorReportDto);
		return map;
	}
}
