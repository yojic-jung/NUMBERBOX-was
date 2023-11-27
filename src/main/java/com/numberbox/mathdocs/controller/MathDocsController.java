package com.numberbox.mathdocs.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.numberbox.common.util.CustomTenFieldDto;
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
		HashMap<String, Object> mapTmp = new HashMap<String, Object>();
		mapTmp.put("existMsg", true);
		mapTmp.put("serverMsg", "N명의수학 서비스는 폐업으로 인하여  종료 되었습니다.");
		return mapTmp;
		/*
		String unitUniqNoAndTypeNo = (String) request.getParameter("unitUniqNoAndTypeNoList");
		int quesLevel = Integer.parseInt(request.getParameter("quesLevel"));
		int conCnt = Integer.parseInt(request.getParameter("conCnt"));
		List<MathContentsDto> list = mathDocsSevice.takeMathSubjectInfo(unitUniqNoAndTypeNo, quesLevel, conCnt);
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("mathContentsList", list);
		return map;
		*/
	}

	@GetMapping("/mathDocsIpsi")
	public HashMap<String, Object> ipsiContentsInfo(HttpServletRequest request) {
		HashMap<String, Object> mapTmp = new HashMap<String, Object>();
		mapTmp.put("existMsg", true);
		mapTmp.put("serverMsg", "N명의수학 서비스는 폐업으로 인하여  종료 되었습니다.");
		return mapTmp;
		/*
		String unitUniqNoAndTypeNo = (String) request.getParameter("unitUniqNoAndTypeNoList");
		String quesLevel = (String) request.getParameter("quesLevel");
		int conCnt = Integer.parseInt(request.getParameter("conCnt"));
		int wrongRatioMin = Integer.parseInt(request.getParameter("wrongRatioMin"));
		int wrongRatioMax = Integer.parseInt(request.getParameter("wrongRatioMax"));
		int ipsiYearMin = Integer.parseInt(request.getParameter("ipsiYearMin"));
		int ipsiYearMax = Integer.parseInt(request.getParameter("ipsiYearMax"));
		String ipsiMonth = (String) request.getParameter("ipsiMonth");
		List<MathContentsDto> list = mathDocsSevice.takeMathIpsiContents(unitUniqNoAndTypeNo, quesLevel, conCnt,
				wrongRatioMin, wrongRatioMax, ipsiYearMin, ipsiYearMax, ipsiMonth);
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("mathContentsList", list);
		return map;
		*/
	}

	@GetMapping("/similarContents")
	public HashMap<String, Object> similarContents(HttpServletRequest request) {
		int unitUniqNo = Integer.parseInt(request.getParameter("unitUniqNo").trim());
		int typeNo = Integer.parseInt(request.getParameter("typeNo").trim());
		int contentsClassify = Integer.parseInt(request.getParameter("contentsClassify").trim());

		List<MathContentsDto> list = mathDocsSevice.takeSimilarContents(unitUniqNo, typeNo, contentsClassify);
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("mathSimilarConList", list);
		return map;
	}

	@PostMapping("/registerMathDocsPaper")
	public HashMap<String, Object> registerMathDocsPaper(MathDocsPaperDto mathDocsPaperDto,
			HttpServletRequest request) {
		HashMap<String, Object> map = mathDocsSevice.registerMathDocsPaper(mathDocsPaperDto);
		return map;
	}

	@PostMapping("/registerMathDocsUsage")
	public HashMap<String, Object> registerMathDocsUsage(MathDocsUsageDto mathDocsUsageDto,
			HttpServletRequest request) {
		HashMap<String, Object> map = mathDocsSevice.registerMathDocsUsage(mathDocsUsageDto);
		return map;
	}

	@GetMapping("/myMathDocs")
	public HashMap<String, Object> myMathDocs(HttpServletRequest request) {
		HashMap<String, Object> map = mathDocsSevice.myMathDocs(Integer.parseInt(request.getParameter("curPageNum")),
				Integer.parseInt(request.getParameter("pageVolume")));
		return map;
	}

	@GetMapping("/delMyMathDocs")
	public HashMap<String, Object> delMyMathDocs(HttpServletRequest request) {
		String docsNo = (String) request.getParameter("docsNo");
		HashMap<String, Object> map = mathDocsSevice.delMyMathDocs(Integer.parseInt(docsNo));
		return map;
	}

	@GetMapping("/mathDocsByMyMathDocsPage")
	public HashMap<String, Object> mathDocsByMyMathDocsPage(HttpServletRequest request) {
		String docsNo = (String) request.getParameter("docsNo");
		HashMap<String, Object> map = mathDocsSevice.mathDocsByMyMathDocsPage(Integer.parseInt(docsNo));
		return map;
	}

	@GetMapping("/mathDocsUsageStatistic")
	public HashMap<String, Object> mathDocsUsageStatistic() {
		HashMap<String, Object> map = new HashMap<>();
		List<CustomTenFieldDto> list = mathDocsSevice.mathDocsUsageStatistic();
		List<CustomTenFieldDto> list2 = mathDocsSevice.mathDocsUsageStatisticByProfile();
		List<CustomTenFieldDto> list3 = mathDocsSevice.mathDocsUsageStatisticByProfileAndDay();
		List<CustomTenFieldDto> list4 = mathDocsSevice.mathDocsUsageStatisticByDayOfWeek();
		List<CustomTenFieldDto> list5 = mathDocsSevice.countMathDocsUsageGroupBySysCreateDateMonth();

		map.put("docsUsage", list);
		map.put("docsUsageByProfile", list2);
		map.put("docsUsageByProfileAndDay", list3);
		map.put("docsUsageByDay", list4);
		map.put("docsUsageByMonth", list5);
		return map;
	}

}
