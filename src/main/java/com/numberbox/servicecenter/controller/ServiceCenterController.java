package com.numberbox.servicecenter.controller;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.numberbox.servicecenter.dto.ErrorReportDto;
import com.numberbox.servicecenter.service.ServiceCenterService;

@RestController
@RequestMapping("/serviceCenter")
public class ServiceCenterController {
	
	@Autowired
	ServiceCenterService customCenterService;
	
	@GetMapping("/takeErrReport")
	public HashMap<String, Object> takeErrReport(HttpServletRequest request) {
		int contentsNo = Integer.parseInt(request.getParameter("contentsNo"));
		int errType = Integer.parseInt(request.getParameter("errType"));
		HashMap<String, Object> map = customCenterService.takeErrReport(contentsNo, errType);
		return map;
	}
	
	@GetMapping("/takeMyErrReport")
	public HashMap<String, Object> takeMyErrReport(HttpServletRequest request) {
		HashMap<String, Object> map = customCenterService.takeMyErrReport();
		return map;
	}
	
	
	@PostMapping("/registerError")
	public HashMap<String, Object> contentsInfo(@ModelAttribute ErrorReportDto errorReportDto, HttpServletRequest request) throws IllegalStateException, IOException {
		String path = request.getSession().getServletContext().getRealPath("/static");	//임시용, 배포 이후 프로젝트 바깥 경로로 설정하는게 좋음(배포용 개발용 따로 관리 필요)
		HashMap<String, Object> map = customCenterService.reportError(errorReportDto, path);
		return map;
	}
	
	@GetMapping("/takeErrReportCount")
	public HashMap<String, Object> takeErrReportByAdmin() {
		HashMap<String, Object> map = customCenterService.takeErrReportCount();
		return map;
	}
	
	@GetMapping("/takeErrReportByAdmin")
	public HashMap<String, Object> takeErrReportByAdmin(HttpServletRequest request) {
		int reportStts = Integer.parseInt(request.getParameter("reportStts"));
		HashMap<String, Object> map = customCenterService.takeErrReportByAdmin(reportStts);
		return map;
	}
	
	@GetMapping("/takeErrReportSearchBySttsAndTypeByAdmin")
	public HashMap<String, Object> takeErrReportByAdminV2(HttpServletRequest request) {
		int reportStts = Integer.parseInt(request.getParameter("reportStts"));
		int errType = Integer.parseInt(request.getParameter("errType"));
		HashMap<String, Object> map = customCenterService.takeErrReportByAdmin(reportStts, errType);
		return map;
	}
	
	
	@PostMapping("/replyErrorReport")
	public HashMap<String, Object> replyErrorReport(@ModelAttribute ErrorReportDto errorReportDto) {
		HashMap<String, Object> map = customCenterService.replyErrorReport(errorReportDto);
		return map;
	}
	
}
