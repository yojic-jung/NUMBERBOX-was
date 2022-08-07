package com.numberbox.servicecenter.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.numberbox.mathdocs.dto.MathDocsPaperDto;
import com.numberbox.mathdocs.entity.MathDocsPaper;
import com.numberbox.mathdocs.repository.MathDocsPaperRepository;
import com.numberbox.members.entity.Members;
import com.numberbox.security.util.StaticSecurityUtil;
import com.numberbox.serivcecenter.entity.ErrorReport;
import com.numberbox.servicecenter.dto.ErrorReportDto;
import com.numberbox.servicecenter.repository.ErrorReportRepository;

@Service
public class ServiceCenterService {
	
	@PersistenceContext
    EntityManager entityManager;

	@Autowired
	ErrorReportRepository errorReportRepository;
	
	@Autowired
	MathDocsPaperRepository mathDocsPaperRepository;
	
	@Autowired
	ModelMapper modelMapper;
	
	public HashMap<String, Object> takeErrReport(int contentsNo, int errType){
		Members members = StaticSecurityUtil.getMembers();
		UUID userUniqId = members.getUserUniqId();
		ErrorReport existErr = errorReportRepository.findByReportUserAndContentsNoAndErrType(userUniqId, contentsNo, errType);
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		if(existErr != null) {
			ErrorReportDto errorReportDto = modelMapper.map(existErr, ErrorReportDto.class);
			errorReportDto.setReportUser(null);
			errorReportDto.setReplyUser(null);
			map.put("existErrReport", errorReportDto);
		}else {
			map.put("existErrReport", existErr);
		}
		
		return map;
	}
	
	
	public HashMap<String, Object> takeMyErrReport(){
		Members members = StaticSecurityUtil.getMembers();
		UUID userUniqId = members.getUserUniqId();
		List<ErrorReport> existErr = errorReportRepository.findByReportUserOrderBySysCreateDateDesc(userUniqId);
		
		List<ErrorReportDto> list = new ArrayList<>();
		for(ErrorReport errReport : existErr) {
			ErrorReportDto errorReportDto = modelMapper.map(errReport, ErrorReportDto.class);
			errorReportDto.setReportUser(null);
			errorReportDto.setReplyUser(null);
			list.add(errorReportDto);
		}
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("myErrReport", list);
		
		return map;
	}
	
	@Transactional
	public HashMap<String, Object> reportError(ErrorReportDto errorReportDto, String path) throws IllegalStateException, IOException {
		Members members = StaticSecurityUtil.getMembers();
		UUID userUniqId = members.getUserUniqId();
		errorReportDto.setReportUser(userUniqId);
		/*(
		if(errorReportDto.getErrType() == 1 || errorReportDto.getErrType() == 2) {	//문제 및 컨텐츠 오류 신고는 같은 문제 재신고시 신고내용 수정
			ErrorReport existErr = errorReportRepository.findByReportUserAndContentsNoAndErrType(userUniqId, errorReportDto.getContentsNo(), errorReportDto.getErrType());
			if(existErr != null) {
				errorReportDto.setReportId(existErr.getReportId());
			}
		}
		*/
		
		
		Random random1 = new Random();
		long currentTime1 = System.currentTimeMillis();
		int randomValue1 = random1.nextInt(100);
		//첫번째 이미지 파일 있으면 이미지 파일 등록
		if(errorReportDto.getFirstImgFile() != null && !errorReportDto.getFirstImgFile().isEmpty()) {
			String fileName = Long.toString(currentTime1) + "_"+randomValue1+"_"+errorReportDto.getFirstImgFile().getOriginalFilename();
			
			File file = new File(path+"/svcCenterImg" , fileName);
			errorReportDto.getFirstImgFile().transferTo(file);
			errorReportDto.setFirstImgPath("/webapp/static/svcCenterImg/");
			errorReportDto.setFirstImgName(fileName);
		}else {
			errorReportDto.setFirstImgPath(null);
			errorReportDto.setFirstImgName(null);
		}
		
		//두번째 이미지 파일 있으면 이미지 파일 등록
		if(errorReportDto.getSecondImgFile() != null && !errorReportDto.getSecondImgFile().isEmpty()) {
			currentTime1 = System.currentTimeMillis();
			randomValue1 = random1.nextInt(100);
			String fileName = Long.toString(currentTime1) + "_"+randomValue1+"_"+errorReportDto.getSecondImgFile().getOriginalFilename();
			
			File file = new File(path+"/svcCenterImg" , fileName);
			errorReportDto.getSecondImgFile().transferTo(file);
			errorReportDto.setSecondImgPath("/webapp/static/svcCenterImg/");
			errorReportDto.setSecondImgName(fileName);
		}else {
			errorReportDto.setSecondImgPath(null);
			errorReportDto.setSecondImgName(null);
		}
		
		
		//세번째 이미지 파일 있으면 이미지 파일 등록
		if(errorReportDto.getThirdImgFile() != null && !errorReportDto.getThirdImgFile().isEmpty()) {
			currentTime1 = System.currentTimeMillis();
			randomValue1 = random1.nextInt(100);
			String fileName = Long.toString(currentTime1) + "_"+randomValue1+"_"+errorReportDto.getThirdImgFile().getOriginalFilename();
			
			File file = new File(path+"/svcCenterImg" , fileName);
			errorReportDto.getThirdImgFile().transferTo(file);
			errorReportDto.setThirdImgPath("/webapp/static/svcCenterImg/");
			errorReportDto.setThirdImgName(fileName);
		}else {
			errorReportDto.setThirdImgPath(null);
			errorReportDto.setThirdImgName(null);
		}
		
		errorReportDto.setReportStts(0);
		
		ErrorReport errorReport = errorReportDto.toEntity();
		
		
		ErrorReport err = errorReportRepository.save(errorReport);
		boolean isSuccess = entityManager.contains(err);
		HashMap<String, Object> map = new HashMap<String, Object>();
		if(isSuccess) {
			if(errorReportDto.getErrType() == 3) {
				MathDocsPaper mathDocs = mathDocsPaperRepository.findByDocsNo(errorReportDto.contentsNo);
				MathDocsPaperDto mathDocsDto = modelMapper.map(mathDocs, MathDocsPaperDto.class);
				if(mathDocsDto.getDocsErrStts() != 2) {
					mathDocsDto.setDocsErrStts(1);
				}
				
				mathDocsPaperRepository.save(mathDocsDto.toEntity());
			}
			map.put("isSuccess", true);
		}else {
			map.put("isSuccess", false);
		}
		return map;
	}
	
	public HashMap<String, Object> takeErrReportCount() {
		HashMap<String, Object> map = new HashMap<String, Object>();
		int oneToOneQuestionCnt = errorReportRepository.countByReportSttsAndErrType(0, 0);
		int conErrCnt = errorReportRepository.countByReportSttsAndErrType(0, 1);
		int resErrCnt = errorReportRepository.countByReportSttsAndErrType(0, 2);
		int mathDocsErrCnt = errorReportRepository.countByReportSttsAndErrType(0, 3);
		map.put("oneToOneQuestionCnt", oneToOneQuestionCnt);
		map.put("conErrCnt", conErrCnt);
		map.put("resErrCnt", resErrCnt);
		map.put("mathDocsErrCnt", mathDocsErrCnt);
		return map;
	}

	public HashMap<String, Object> takeErrReportByAdmin(int reportStts) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		List<ErrorReport> oneToOneErrReportList = errorReportRepository.findByReportSttsAndErrTypeOrderBySysCreateDateDesc(reportStts, 0);
		List<ErrorReport> conErrReportList = errorReportRepository.findByReportSttsAndErrTypeOrderBySysCreateDateDesc(reportStts, 1);
		List<ErrorReport> resErrReportList = errorReportRepository.findByReportSttsAndErrTypeOrderBySysCreateDateDesc(reportStts, 2);
		List<ErrorReport> docsErrReportList = errorReportRepository.findByReportSttsAndErrTypeOrderBySysCreateDateDesc(reportStts, 3);
		
		List<ErrorReportDto> oneToOneList = new ArrayList<>();
		for(ErrorReport errReport : oneToOneErrReportList) {
			ErrorReportDto errorReportDto = modelMapper.map(errReport, ErrorReportDto.class);
			errorReportDto.setReportUser(null);
			errorReportDto.setReplyUser(null);
			oneToOneList.add(errorReportDto);
		}
		
		List<ErrorReportDto> conErrList = new ArrayList<>();
		for(ErrorReport errReport : conErrReportList) {
			ErrorReportDto errorReportDto = modelMapper.map(errReport, ErrorReportDto.class);
			errorReportDto.setReportUser(null);
			errorReportDto.setReplyUser(null);
			conErrList.add(errorReportDto);
		}
		
		List<ErrorReportDto> resErrList = new ArrayList<>();
		for(ErrorReport errReport : resErrReportList) {
			ErrorReportDto errorReportDto = modelMapper.map(errReport, ErrorReportDto.class);
			errorReportDto.setReportUser(null);
			errorReportDto.setReplyUser(null);
			resErrList.add(errorReportDto);
		}
		
		List<ErrorReportDto> docsErrReportListDto = new ArrayList<>();
		for(ErrorReport errReport : docsErrReportList) {
			ErrorReportDto errorReportDto = modelMapper.map(errReport, ErrorReportDto.class);
			errorReportDto.setReportUser(null);
			errorReportDto.setReplyUser(null);
			docsErrReportListDto.add(errorReportDto);
		}
		
		map.put("oneToOneList", oneToOneList);
		map.put("conErrList", conErrList);
		map.put("resErrList", resErrList);
		map.put("mathDocsErrList", docsErrReportListDto);
		return map;
	}
	
	public HashMap<String, Object> takeErrReportByAdmin(int reportStts, int errType) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		List<ErrorReport> errReportList = new ArrayList<>();
		if(reportStts == -1) {
			errReportList = errorReportRepository.findByErrTypeOrderBySysCreateDateDesc(errType);
		}else {
			errReportList = errorReportRepository.findByReportSttsAndErrTypeOrderBySysCreateDateDesc(reportStts, errType);
		}
		List<ErrorReportDto> reportList = new ArrayList<>();
		for(ErrorReport errReport : errReportList) {
			ErrorReportDto errorReportDto = modelMapper.map(errReport, ErrorReportDto.class);
			errorReportDto.setReportUser(null);
			errorReportDto.setReplyUser(null);
			reportList.add(errorReportDto);
		}
		map.put("errReportList", reportList);
		return map;
	}
	
	@Transactional
	public HashMap<String, Object> replyErrorReport(ErrorReportDto errorReportDto) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		Members members = StaticSecurityUtil.getMembers();
		UUID userUniqId = members.getUserUniqId();
		ErrorReport errorReport= errorReportRepository.findByReportId(errorReportDto.getReportId());
		ErrorReportDto errReportDto = modelMapper.map(errorReport, ErrorReportDto.class);
		errReportDto.setReplyUser(userUniqId);
		errReportDto.setReplyContents(errorReportDto.getReplyContents());
		errReportDto.setReportStts(1);
		errorReportRepository.save(errReportDto.toEntity());
		map.put("isSuccess", true);
		return map;
	}
	
}
