package com.numberbox.customcenter.service;

import java.util.HashMap;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.numberbox.customcenter.dto.ErrorReportDto;
import com.numberbox.customcenter.entity.ErrorReport;
import com.numberbox.customcenter.repository.ErrorReportRepository;
import com.numberbox.members.entity.Members;
import com.numberbox.security.util.StaticSecurityUtil;

@Service
public class CustomCenterService {
	@PersistenceContext
    EntityManager entityManager;

	@Autowired
	ErrorReportRepository errorReportRepository;
	
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
			map.put("existErrReport", errorReportDto);
		}else {
			map.put("existErrReport", existErr);
		}
		
		return map;
	}
	
	@Transactional
	public HashMap<String, Object> reportError(ErrorReportDto errorReportDto) {
		Members members = StaticSecurityUtil.getMembers();
		UUID userUniqId = members.getUserUniqId();
		errorReportDto.setReportUser(userUniqId);
		
		ErrorReport existErr = errorReportRepository.findByReportUserAndContentsNoAndErrType(userUniqId, errorReportDto.getContentsNo(), errorReportDto.getErrType());
		if(existErr != null) {
			errorReportDto.setReportId(existErr.getReportId());
		}
		ErrorReport errorReport = errorReportDto.toEntity();
		
		ErrorReport err = errorReportRepository.save(errorReport);
		boolean isSuccess = entityManager.contains(err);
		HashMap<String, Object> map = new HashMap<String, Object>();
		if(isSuccess) {
			map.put("isSuccess", true);
		}else {
			map.put("isSuccess", false);
		}
		return map;
	}

}
