package com.numberbox.mathinfo.service;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
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
import com.numberbox.mathinfo.entity.MathUnitInfo;
import com.numberbox.mathinfo.repository.FormulKeyRepository;
import com.numberbox.mathinfo.repository.MathContentsRepository;
import com.numberbox.mathinfo.repository.MathTypeRepository;
import com.numberbox.mathinfo.repository.MathUnitRepository;
import com.numberbox.members.entity.Members;
import com.numberbox.members.entity.MembersNo;
import com.numberbox.members.entity.MembersRole;
import com.numberbox.security.util.StaticSecurityUtil;

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
	public boolean registerContents(MathContentsDto mathContentsDto, String path, String accessToken) throws IllegalStateException, IOException {

		MembersNo membersNo = StaticSecurityUtil.getMembersNo();
		long userNo = membersNo.getUserNo();
		List<MembersRole> roleList =  StaticSecurityUtil.getMembers().getRole();
		if(mathContentsDto.getUserNo() != 0) {
			//관리자 아닌 경우 자신이 만든 문제 외의 문제 수정 금지
			boolean isAdmin = false;
			for(MembersRole role : roleList) {
				if(role.getRoleName().equals("ADMIN")) isAdmin=true;
			}
			if(!isAdmin) {
				if(mathContentsDto.getUserNo() != userNo) {
					return false;
				}
			}
		}
		
		//수정모드 아닌 경우에만 userNo를 제작자로 셋팅, 수정모드인 경우에는 원본 제작자 그대로
		if(mathContentsDto.getContentsNo()==0) {
			//default값 설정
			mathContentsDto.setUserNo(userNo);
		}
		
		mathContentsDto.setLikeCnt(0);
		mathContentsDto.setHateCnt(0);
		mathContentsDto.setDownCnt(0);
		mathContentsDto.setSvcPosbStts(0);
		
		//객관식 정답 없는 경우 주관식문제로 설정(정답 입력 이후 주관식 및 객관식 분류, 문제만 입력했을땐 주관식으로 우선 등록, 객관식 주관식 둘다 있을시 객관식으로 적용)
		if(mathContentsDto.getChoiceAnswer()==null) {
			mathContentsDto.setMultiChoiceType("E");
		}else {
			mathContentsDto.setMultiChoiceType("M");
		}

		//정답 존재유무 상태코드 설정, 0은 미존재, 1은 존재
		if((mathContentsDto.getAnswer()!=null && !mathContentsDto.getAnswer().isEmpty()) || mathContentsDto.getChoiceAnswer()!=null) {
			mathContentsDto.setAnsExistStts(1);
		}else {
			mathContentsDto.setAnsExistStts(0);
		}
		
		//수정모드 아닌 경우
		if(mathContentsDto.getContentsNo()==0) {
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
				
				File file = new File(path+"/solutionImg" , fileName);
				mathContentsDto.getSolutionImg().transferTo(file);
				mathContentsDto.setSolutionImgPath("/webapp/static/solutionImg/");
				mathContentsDto.setSolutionImgName(fileName);
			}else {
				mathContentsDto.setSolutionImgName(null);
			}
		}
		
		
		//판별 필요 multiChoiceType, ansExistStts
		MathContents contents = mathContentsRepository.save(mathContentsDto.toEntity());
		boolean isSuccess = entityManager.contains(contents);
		return isSuccess;
	}
	
	@Transactional
	public List<MathContents> takeContents(MathContentsDto mathContentsDto) {
		Members members = StaticSecurityUtil.getMembers();
		MembersNo membersNo = StaticSecurityUtil.getMembersNo();
		long userNo = membersNo.getUserNo();
		List<MembersRole> roleList = members.getRole();
		boolean isAdmin = false;
		for(MembersRole role : roleList) {
			if(role.getRoleName().equals("ADMIN")) {
				isAdmin = true;
			}
		}
		
		List<MathContents> list = null;
		if(isAdmin) {
			list =  mathContentsRepository.findByUnitUniqNoOrderBySysCreateDateDesc(mathContentsDto.getUnitUniqNo());
		}else {
			list =  mathContentsRepository.findByUnitUniqNoAndUserNoOrderBySysCreateDateDesc(mathContentsDto.getUnitUniqNo(), userNo);
		}
		
		return list;
	}
	
	public HashMap<String, Object> takeMyContents(int contentsNo){
		MathContents mathContents = mathContentsRepository.findByContentsNo(contentsNo);
		long contentsUserNo = mathContents.getUserNo();
		MembersNo membersNo = StaticSecurityUtil.getMembersNo();
		long userNo = membersNo.getUserNo();
		
		//관리자 아닌 경우 자신이 만든 문제 외의 문제 수정 금지
		List<MembersRole> roleList =  StaticSecurityUtil.getMembers().getRole();
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("myContents", mathContents);
		boolean isAdmin = false;
		for(MembersRole role : roleList) {
			if(role.getRoleName().equals("ADMIN")) isAdmin=true;
		}
		if(isAdmin) {
			map.put("existMsg", false);
		}else {
			if(contentsUserNo == userNo) {
				map.put("existMsg", false);
			}else {
				map.put("existMsg", true);
				map.put("serverMsg", "본인이 만든 문제 외의 문제는 수정할 수 없습니다.");
				map.put("myContents", null);
			}
		}
		return map;
	}
	
	public MathUnitInfo takeUnitInfoByUnitUniqNo(int unitUniqNo){
		return mathUnitRepository.findByUnitUniqNo(unitUniqNo);
	}	
	
	public int changeConOrSolImg(MathContentsDto mathContentsDto, String path, long userNo) throws IllegalStateException, IOException{
		Members members = StaticSecurityUtil.getMembers();
		MembersNo membersNo = StaticSecurityUtil.getMembersNo();
		List<MembersRole> roleList = members.getRole();
		boolean isAdmin = false;
		for(MembersRole role : roleList) {
			if(role.getRoleName().equals("ADMIN")) {
				isAdmin = true;
			}
		}
		if(!isAdmin) {
			if(userNo != membersNo.getUserNo()) {
				return -1;
			}
		}
		
		Random random1 = new Random();
		if(mathContentsDto.getContentsImg()!=null && !mathContentsDto.getContentsImg().isEmpty()) {
			long currentTime1 = System.currentTimeMillis();
			int randomValue1 = random1.nextInt(100);

			String fileName = Long.toString(currentTime1) + "_"+randomValue1+"_"+mathContentsDto.getContentsImg().getOriginalFilename();
			
			File file = new File(path+"/contentsImg" , fileName);
			mathContentsDto.getContentsImg().transferTo(file);
			mathContentsDto.setImgPath("/webapp/static/contentsImg/");
			mathContentsDto.setContentsImgName(fileName);
			return mathContentsRepository.changeConImg(mathContentsDto.getContentsNo(), "/webapp/static/contentsImg/", mathContentsDto.getContentsImgName());
		}		
		if(mathContentsDto.getSolutionImg()!=null && !mathContentsDto.getSolutionImg().isEmpty()) {
			long currentTime1 = System.currentTimeMillis();
			int randomValue1 = random1.nextInt(100);

			String fileName = Long.toString(currentTime1) + "_"+randomValue1+"_"+mathContentsDto.getSolutionImg().getOriginalFilename();
			
			File file = new File(path+"/solutionImg" , fileName);
			mathContentsDto.getSolutionImg().transferTo(file);
			mathContentsDto.setSolutionImgPath("/webapp/static/solutionImg/");
			mathContentsDto.setSolutionImgName(fileName);
			return mathContentsRepository.changeSolImg(mathContentsDto.getContentsNo(), "/webapp/static/solutionImg/", mathContentsDto.getSolutionImgName());
		}
		return 0;
		
	}	
	
	
	public int delConOrSolImg(int contentsNo, String conOrSol, String path, long userNo){
		Members members = StaticSecurityUtil.getMembers();
		MembersNo membersNo = StaticSecurityUtil.getMembersNo();
		List<MembersRole> roleList = members.getRole();
		boolean isAdmin = false;
		for(MembersRole role : roleList) {
			if(role.getRoleName().equals("ADMIN")) {
				isAdmin = true;
			}
		}
		if(!isAdmin) {
			if(userNo != membersNo.getUserNo()) {
				return -1;
			}
		}
		if(conOrSol.equals("contentsImg")) {
			MathContents mathContents = mathContentsRepository.findByContentsNo(contentsNo);
			File file = new File(path+"/contentsImg/"+mathContents.getContentsImg());
			file.delete();
			return mathContentsRepository.changeConImg(contentsNo, null, null);
		}
		else {
			MathContents mathContents = mathContentsRepository.findByContentsNo(contentsNo);
			File file = new File(path+"/solutionImg/"+mathContents.getSolutionImg());
			file.delete();
			return mathContentsRepository.changeSolImg(contentsNo, null, null);
		} 
		
	}	
	
	
}
