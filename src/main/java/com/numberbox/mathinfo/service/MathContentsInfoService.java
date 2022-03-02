package com.numberbox.mathinfo.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.numberbox.mathinfo.dto.MathContentsDto;
import com.numberbox.mathinfo.dto.MathUnitInfoGroup;
import com.numberbox.mathinfo.entity.FormulKey;
import com.numberbox.mathinfo.entity.MathContents;
import com.numberbox.mathinfo.entity.MathTypeInfo;
import com.numberbox.mathinfo.repository.FormulKeyRepository;
import com.numberbox.mathinfo.repository.MathContentsRepository;
import com.numberbox.mathinfo.repository.MathTypeRepository;
import com.numberbox.mathinfo.repository.MathUnitRepository;

@Service
public class MathContentsInfoService {
	
	@PersistenceContext
    EntityManager entityManager;

	@Autowired
	MathUnitRepository mathUnitRepository;
	@Autowired
	MathTypeRepository mathTypeRepository;
	@Autowired
	FormulKeyRepository formulKeyRepository;
	@Autowired
	MathContentsRepository mathContentsRepository;
	
	public List<MathUnitInfoGroup> takeMathSubjectInfo(){
		return mathUnitRepository.selectSubjectInfo();
	}
	
	public List<MathUnitInfoGroup> takeMathFirUnitInfo(){
		return mathUnitRepository.selectFirUnitInfo();
	}
	
	public List<MathUnitInfoGroup> takeMathSecUnitInfo(){
		return mathUnitRepository.selectSecUnitInfo();
	}
	
	public List<MathUnitInfoGroup> takeMathThrUnitInfo(){
		return mathUnitRepository.selectThrUnitInfo();
	}
	
	public List<MathTypeInfo> takeMathTypeInfo(String unitUniqNo){
		return mathTypeRepository.findByUnitUniqNo(unitUniqNo);
	}
	
	public List<FormulKey> takeShortCutKey(){
		return formulKeyRepository.findByClassification("main");
	}
	
	public List<FormulKey> takeShortCutKeyHigh1(){
		return formulKeyRepository.findByClassification("high1");
	}
	
	public List<FormulKey> takeShortCutKeyEtc(){
		return formulKeyRepository.findByClassification("etc");
	}
	
	@Transactional
	public boolean registerContents(MathContentsDto mathContentsDto, String path) throws IllegalStateException, IOException {
		//default값 설정
		mathContentsDto.setLikeCnt(0);
		mathContentsDto.setHateCnt(0);
		mathContentsDto.setDownCnt(0);
		mathContentsDto.setSvcPosbStts(0);
		
		//객관식 정답 없는 경우 주관식문제로 설정(정답 입력 이후 주관식 및 객관식 분류, 문제만 입력했을땐 주관식으로 우선 등록)
		if(mathContentsDto.getChoiceAnswer()==null) {
			mathContentsDto.setMultiChoiceType("E");
		}else {
			mathContentsDto.setMultiChoiceType("M");
		}

		//정답 존재유무 상태코드 설정, 0은 미존재, 1은 존재
		if(mathContentsDto.getAnswer()!=null && !mathContentsDto.getAnswer().isEmpty()) {
			mathContentsDto.setAnsExistStts(1);
		}else {
			mathContentsDto.setAnsExistStts(0);
		}
		
		//이미지파일 저장
		Random random1 = new Random();
		if(mathContentsDto.getContentsImg()!=null && !mathContentsDto.getContentsImg().isEmpty()) {
			long currentTime1 = System.currentTimeMillis();
			int randomValue1 = random1.nextInt(100);

			String fileName = Long.toString(currentTime1) + "_"+randomValue1+"_"+mathContentsDto.getContentsImg().getOriginalFilename();
			
			File file = new File(path+"/contentsImg" , fileName);
			mathContentsDto.getContentsImg().transferTo(file);
			mathContentsDto.setImgPath("/webapp/static/contentsImg/");
			mathContentsDto.setContentsImgName(fileName);
		}else {
			mathContentsDto.setContentsImgName(null);
		}
		
		if(mathContentsDto.getSolutionImg()!=null && !mathContentsDto.getSolutionImg().isEmpty()) {
			long currentTime1 = System.currentTimeMillis();
			int randomValue1 = random1.nextInt(100);

			String fileName = Long.toString(currentTime1) + "_"+randomValue1+"_"+mathContentsDto.getSolutionImg().getOriginalFilename();
			
			File file = new File(path+"/contentsImg" , fileName);
			mathContentsDto.getSolutionImg().transferTo(file);
			mathContentsDto.setImgPath("/webapp/static/contentsImg/");
			mathContentsDto.setSolutionImgName(fileName);
		}else {
			mathContentsDto.setSolutionImgName(null);
		}
		
		//판별 필요 multiChoiceType, ansExistStts
		MathContents contents = mathContentsRepository.save(mathContentsDto.toEntity());
		boolean isSuccess = entityManager.contains(contents);
		return isSuccess;
	}
	
	public List<MathContents> takeContents(MathContentsDto mathContentsDto){
		return mathContentsRepository.findByUnitUniqNoAndWorkMemOrderBySysCreateDateDesc(mathContentsDto.getUnitUniqNo(), mathContentsDto.getWorkMem());
	}
}
