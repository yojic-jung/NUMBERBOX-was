package com.numberbox.mathinfo.service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.numberbox.common.util.CustomTenFieldDto;
import com.numberbox.common.util.DeduplicationUtils;
import com.numberbox.common.util.MathProblemAnalyzer;
import com.numberbox.mathinfo.domain.MathConLikeDomain;
import com.numberbox.mathinfo.domain.MathConRepoDomain;
import com.numberbox.mathinfo.domain.MathTypeDomain;
import com.numberbox.mathinfo.dto.ContentsCnt;
import com.numberbox.mathinfo.dto.ContentsListModel;
import com.numberbox.mathinfo.dto.FormulKeyDto;
import com.numberbox.mathinfo.dto.MathConLikeInfoDto;
import com.numberbox.mathinfo.dto.MathConRepoInfoDto;
import com.numberbox.mathinfo.dto.MathContentsCompDto;
import com.numberbox.mathinfo.dto.MathContentsCompListDto;
import com.numberbox.mathinfo.dto.MathContentsDto;
import com.numberbox.mathinfo.dto.MathContentsGrammerDto;
import com.numberbox.mathinfo.dto.MathContentsIpsiDto;
import com.numberbox.mathinfo.dto.MathContentsIpsiListDto;
import com.numberbox.mathinfo.dto.MathContentsLicenseDto;
import com.numberbox.mathinfo.dto.MathContentsListDto;
import com.numberbox.mathinfo.dto.MathContentsModel;
import com.numberbox.mathinfo.dto.MathTypeInfoDto;
import com.numberbox.mathinfo.dto.MathTypeInfoModel;
import com.numberbox.mathinfo.dto.MathUnitInfoDto;
import com.numberbox.mathinfo.dto.MathUnitInfoGroup;
import com.numberbox.mathinfo.entity.FormulKey;
import com.numberbox.mathinfo.entity.MathConLikeInfo;
import com.numberbox.mathinfo.entity.MathConRepoInfo;
import com.numberbox.mathinfo.entity.MathContents;
import com.numberbox.mathinfo.entity.MathContentsComp;
import com.numberbox.mathinfo.entity.MathContentsGrammer;
import com.numberbox.mathinfo.entity.MathContentsIpsi;
import com.numberbox.mathinfo.entity.MathContentsLicense;
import com.numberbox.mathinfo.entity.MathTypeInfo;
import com.numberbox.mathinfo.entity.MathUnitInfo;
import com.numberbox.mathinfo.repository.FormulKeyRepository;
import com.numberbox.mathinfo.repository.MathConLikeInfoRepository;
import com.numberbox.mathinfo.repository.MathConRepoInfoRepository;
import com.numberbox.mathinfo.repository.MathContentsCompRepository;
import com.numberbox.mathinfo.repository.MathContentsGramRepository;
import com.numberbox.mathinfo.repository.MathContentsIpsiRepository;
import com.numberbox.mathinfo.repository.MathContentsLicenseRepository;
import com.numberbox.mathinfo.repository.MathContentsRepository;
import com.numberbox.mathinfo.repository.MathTypeRepository;
import com.numberbox.mathinfo.repository.MathUnitKeywordRepository;
import com.numberbox.mathinfo.repository.MathUnitRepository;
import com.numberbox.members.dto.MembersProfileDto;
import com.numberbox.members.entity.Members;
import com.numberbox.members.entity.MembersProfile;
import com.numberbox.members.entity.MembersRole;
import com.numberbox.members.repository.MembersFollowInfoRepository;
import com.numberbox.members.repository.MembersProfileRepository;
import com.numberbox.security.util.StaticSecurityUtil;

import kr.co.shineware.nlp.komoran.constant.DEFAULT_MODEL;
import kr.co.shineware.nlp.komoran.core.Komoran;
import kr.co.shineware.nlp.komoran.model.KomoranResult;
@Service
public class MathContentsInfoService {
	
	 @Value("${numberbox.openaiForbiddenWord}")
	 private String openaiForbiddenWord;
	 
	 @Autowired
	 MathProblemAnalyzer mathProblemAnalyzer;
		
	 
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	 
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
	@Autowired
	MathContentsCompRepository mathContentsCompRepository;
	@Autowired
	MathContentsIpsiRepository mathContentsIpsiRepository;
	@Autowired
	MathContentsLicenseRepository mathContentsLicRepository;
	@Autowired
	MathContentsGramRepository mathContentsGramRepository;
	@Autowired
	MembersProfileRepository membersProfileRepository;
	@Autowired
	MathConLikeInfoRepository mathConLikeInfoRepository;
	@Autowired
	MathConRepoInfoRepository mathConRepoInfoRepository;
	@Autowired
	MembersFollowInfoRepository membersFollowInfoRepository;
	@Autowired
	MathUnitKeywordRepository mathUnitKeywordRepository;
	
	@Autowired
	ModelMapper modelMapper;
	
	public List<MathUnitInfoGroup> takeMathSubjectInfo(String isOnlyExist){
		if(isOnlyExist != null) {
			return mathUnitRepository.selectSubjectInfoOnlyExistContents();
		}else {
			return mathUnitRepository.selectSubjectInfo();
		}
	}
	
	public List<MathUnitInfoGroup> takeMathFirUnitInfo(){
		return mathUnitRepository.selectFirUnitInfo();
	}
	
	public List<MathUnitInfoGroup> takeMathSecUnitInfo(String isOnlyExist){
		if(isOnlyExist != null) {
			return mathUnitRepository.selectSecUnitInfoOnlyExistContents();
		}else {
			return mathUnitRepository.selectSecUnitInfo();
		}
	}
	
	public List<MathUnitInfoGroup> takeMathThrUnitInfo(String isOnlyExist){
		if(isOnlyExist != null) {
			return mathUnitRepository.selectThrUnitInfoOnlyExistContents();
		}else {
			return mathUnitRepository.selectThrUnitInfo();
		}
	}
	
	public List<MathUnitInfo> takeUnitInfoList(String value){
		return mathUnitRepository.findBySecUnit(value);
	}
	
	public List<MathUnitInfo> takeUnitInfoList(MathUnitInfoDto unitInfoDto){
		return mathUnitRepository.findBySubject(unitInfoDto.getSubject());
	}
	
	
	public List<MathTypeInfo> takeMathTypeInfo(String unitUniqNo){
		return mathTypeRepository.findByUnitUniqNoOrderByTypeOrderAsc(unitUniqNo);
	}
	
	public MathTypeInfo takeMathTypeInfoOnlyOne(String unitUniqNo, String typeNo){
		return mathTypeRepository.findByMathTypeDomainUnitUniqNoAndMathTypeDomainTypeNo(unitUniqNo, typeNo);
	}
	
	public List<MathTypeInfo> takeMathTypeInfoList(String unitNoList){
		String[] unitNoArr = unitNoList.split(",");
		List<String> unitUniqNoList = new ArrayList<>();
		for(String unitNo : unitNoArr) {
			unitUniqNoList.add(unitNo);
		}
		
		return mathTypeRepository.findByMathTypeDomainUnitUniqNoInOrderByMathTypeDomainUnitUniqNoAscTypeOrderAsc(unitUniqNoList);
	}
	public static final Komoran instance = new Komoran(DEFAULT_MODEL.FULL);
	
	
	public HashMap<String, Object> takemathAiCompContents(String contentsGrammer) throws IOException{
		Members members = StaticSecurityUtil.getMembers();
		UUID userUniqId = members.getUserUniqId();
		MembersProfile mp = membersProfileRepository.findByUserUniqId(userUniqId);
		if(mp.getAiContentsCnt() > 3) {
			HashMap<String, Object> map = new HashMap<>();
			map.put("saveSuccess", false);
			map.put("existMsg", true);
			map.put("serverMsg", "AI 유사 문제 제작 일일 사용 허용량 3회를 초과하였습니다.");
			return map;
		}
		
		membersProfileRepository.changeAiContentsCnt(userUniqId, mp.getAiContentsCnt()+1);
		
		HashMap<String, Object> map = new HashMap<String, Object>();

		String question = "한국의 수학 중등 3학년의 제곱근과 실수단원에서 \"sqrt{x}이하의 자연수의 개수 또는 자연수 구하기\"와 관련된 난이도 상 문제를 5개 만들어줘";
		String answerStr = mathProblemAnalyzer.questionToChatGtp(question, 0.9, 1000, 0.8, 0.3, 0.0);
        
	    map.put("answerStr", answerStr);
		return map;
	}
	
	public HashMap<String, Object> takeMathUnitListByKeyword(String contentsGrammer) throws IOException{
		Members members = StaticSecurityUtil.getMembers();
		UUID userUniqId = members.getUserUniqId();
		MembersProfile mp = membersProfileRepository.findByUserUniqId(userUniqId);
		if(mp.getUnitMappingCnt() > 100) {
			HashMap<String, Object> map = new HashMap<>();
			map.put("saveSuccess", false);
			map.put("existMsg", true);
			map.put("serverMsg", "AI 단원 매핑 일일 사용 허용량은 100회를 모두 사용하셨습니다.");
			return map;
		}
		
		membersProfileRepository.changeUnitMappingCnt(userUniqId, mp.getUnitMappingCnt()+1);
		
		
		HashMap<String, Object> map = new HashMap<String, Object>();

		String question = "한국 수학 교육과정에서 {" + contentsGrammer + "}와 가장 관련된 단원명은 무엇인가요? 단원명만 간결하게 말해주세요.";
		String answerStr = mathProblemAnalyzer.questionToChatGtp(question, 0.8, 60, 1, 0.0, 0.0);
		
        KomoranResult analyzeResultList = instance.analyze(answerStr);
        
        String[] openaiForbiddenWordArr =openaiForbiddenWord.split(",");
        
	    String keywordList = "";
	    for(String str : analyzeResultList.getNouns()) {
	    	boolean isKeywordEqual = false;
	    	for(String forbiddenWord : openaiForbiddenWordArr) {
	    		if(str.equals(forbiddenWord)) {
	    			isKeywordEqual = true;
	    		}
	    	}
	    	if(!isKeywordEqual && str.length() != 1) {
	    		keywordList += str+"|";
	    	}
	    }
	    
	    
	    List<MathUnitInfoDto> unitInfoDtoList = new ArrayList<>();
	    if(keywordList.length()>1) {
	    	keywordList=keywordList.substring(0, keywordList.length() - 1);
	    	List<MathUnitInfo> unitList = mathUnitRepository.findByFirUnitRegexpOrSecUnitRegexpOrThrUnitRegexp(keywordList);
	 	    List<Integer> unitUniqNoList = mathUnitKeywordRepository.findByKeywordRegexp(keywordList);
	 	    List<MathUnitInfo> unitList2 =  mathUnitRepository.findByUnitUniqNoIn(unitUniqNoList);
	 	  
	 	    for(MathUnitInfo unitInfo : unitList) {
	 	    	MathUnitInfoDto unitInfoDto = modelMapper.map(unitInfo, MathUnitInfoDto.class);
	 	    	unitInfoDtoList.add(unitInfoDto);
	 		}
	 	    for(MathUnitInfo unitInfo : unitList2) {
	 	    	MathUnitInfoDto unitInfoDto = modelMapper.map(unitInfo, MathUnitInfoDto.class);
	 	    	unitInfoDtoList.add(unitInfoDto);
	 		}
	 	    Collections.sort(unitInfoDtoList);
	 	   
	    }
	    List<MathUnitInfoDto> deduplicationList = DeduplicationUtils.deduplication(unitInfoDtoList, MathUnitInfoDto::getUnitUniqNo);
	    map.put("unitList", deduplicationList);
		return map;
	}
	
	public HashMap<String, Object> takeShortCutKey(){
		HashMap<String, Object> map = new HashMap<String, Object>();
		List<FormulKey> formulKeyList = formulKeyRepository.findAllByOrderByFormulOrderAscIdAsc();
		List<FormulKeyDto> mainList = new ArrayList<>();
		List<FormulKeyDto> highList = new ArrayList<>();
		List<FormulKeyDto> etcList = new ArrayList<>();
		List<FormulKeyDto> etcList2 = new ArrayList<>();
		for(FormulKey formulKey : formulKeyList) {
			FormulKeyDto formulKeyDto = modelMapper.map(formulKey, FormulKeyDto.class);
			String classification = formulKeyDto.getClassification();
			if(classification.equals("main")) {
				mainList.add(formulKeyDto);
			}else if(classification.equals("high1")) {
				highList.add(formulKeyDto);
				
			}else if(classification.equals("etc")) {
				etcList.add(formulKeyDto);
				
			}else if(classification.equals("etc2")) {
				etcList2.add(formulKeyDto);
				
			}
		}
		map.put("shortCutKey", mainList);
		map.put("shortCutKeyHigh1", highList);
		map.put("shortCutKeyEtc", etcList);
		map.put("shortCutKeyEtc2", etcList2);
		return map;
	}
	
	@Transactional
	public MathContentsModel takeMathContents(int contentsNo){
		//수정모드인 경우, MathContents 화면단 전달, 모달창 초기화 위해
		MathContents mathContents = mathContentsRepository.findByContentsNo(contentsNo);
		MathContentsModel mathContentsModel = modelMapper.map(mathContents, MathContentsModel.class);
		return mathContentsModel;
	}
	
	
	@Transactional
	public HashMap<String, Object> registerContents(MathContentsDto mathContentsDto, String path, String accessToken, boolean isManager) throws IllegalStateException, IOException {
		HashMap<String, Object> map = new HashMap<String, Object>();
		Members members = StaticSecurityUtil.getMembers();
		UUID userUniqId = members.getUserUniqId();
		List<MembersRole> roleList =  members.getRole();
		//수정 모드인 경우 자기 자신 문제만 수정 가능
		if(mathContentsDto.getContentsNo()!=0) {
			UUID contentsUuid = mathContentsRepository.findOnlyUuidByContentsNo(mathContentsDto.getContentsNo());
			boolean isAdmin = false;
			for(MembersRole role : roleList) {
				if(role.getRoleName().equals("ADMIN")) isAdmin=true;		//관리자는 넘버링크 문제 모두 수정가능
			}
			if(!isAdmin) {
				if(!contentsUuid.equals(userUniqId)) {		//컨텐츠에 등록되어있는 uuid와 사용자 uuid 같은 경우에만 수정가능
					map.put("saveSuccess", false);
					map.put("existMsg", true);
					map.put("serverMsg", "본인이 만든 문제 외의 문제는 수정할 수 없습니다.");
					return map;
				}
			}
			mathContentsDto.setUserUniqId(userUniqId);	//uuid updatabl false 이지만 값은 셋팅 되야함
			
		//수정모드 아닌 경우에만 자신의 userUniqId 를 제작자로 셋팅
		}else {
			mathContentsDto.setUserUniqId(userUniqId);
		}
		
		//넘버링크 문제는 svcPosbStts=0
		//사용자 제작 및 변형 문제는 svcPosbStts=1
		if(isManager) {
			mathContentsDto.setSvcPosbStts(0);
		}
		else {
			mathContentsDto.setSvcPosbStts(1);
		} 
		
		//삭제한 유형에 등록하면 에러 날 수 있음
		//관리자가 유형 삭제 하더라도 사용자가 홈페이지 새로고침 안하면 삭제한 유형 그대로 보일 수 있음
		MathTypeInfo mathType = mathTypeRepository.findByMathTypeDomainUnitUniqNoAndMathTypeDomainTypeNo(Integer.toString(mathContentsDto.getUnitUniqNo()), Integer.toString(mathContentsDto.getTypeNo()));
		if(mathType == null) {
			map.put("saveSuccess", false);
			map.put("existMsg", true);
			map.put("serverMsg", "해당 유형은 삭제 되었습니다. 새로운 유형에 등록 후 새로고침하여 주시기 바랍니다.");
			return map;
		}
		
		//변형문제 갯수 세팅 
		if(mathContentsDto.getContentsClassify()==2) {
			mathContentsDto.setUserUniqId(userUniqId);
			int transConCnt = mathContentsRepository.countByOrgContentsNo(mathContentsDto.getOrgContentsNo());
			mathContentsRepository.updateTransConCnt(mathContentsDto.getOrgContentsNo(), transConCnt+1);
		}
		
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
			if(mathContentsDto.getContentsImgFile()!=null && !mathContentsDto.getContentsImgFile().isEmpty()) {
				long currentTime1 = System.currentTimeMillis();
				int randomValue1 = random1.nextInt(100);

				String fileName = Long.toString(currentTime1) + "_"+randomValue1+"_"+mathContentsDto.getContentsImgFile().getOriginalFilename();
				
				File file = new File(path+"/contentsImg" , fileName);
				mathContentsDto.getContentsImgFile().transferTo(file);
				mathContentsDto.setImgPath("/webapp/static/contentsImg/");
				mathContentsDto.setContentsImg(fileName);
			}else {
				mathContentsDto.setContentsImg(null);
			}
			
			if(mathContentsDto.getSolutionImgFile()!=null && !mathContentsDto.getSolutionImgFile().isEmpty()) {
				long currentTime1 = System.currentTimeMillis();
				int randomValue1 = random1.nextInt(100);

				String fileName = Long.toString(currentTime1) + "_"+randomValue1+"_"+mathContentsDto.getSolutionImgFile().getOriginalFilename();
				
				File file = new File(path+"/solutionImg" , fileName);
				mathContentsDto.getSolutionImgFile().transferTo(file);
				mathContentsDto.setSolutionImgPath("/webapp/static/solutionImg/");
				mathContentsDto.setSolutionImg(fileName);
			}else {
				mathContentsDto.setSolutionImg(null);
			}
		}
		
		//판별 필요 multiChoiceType, ansExistStts
		MathContents contents = mathContentsRepository.save(mathContentsDto.toEntity());
		map.put("contentsNo", contents.getContentsNo());
		
		boolean isSuccess = entityManager.contains(contents);
		if(isSuccess) {
			//사용자 제작 문제는 라이선스 등록, 변형문제는 저작권 등록 X
			if(!isManager) {
				if(mathContentsDto.getContentsClassify()==1) {
					mathContentsDto.setContentsNo(contents.getContentsNo());
					mathContentsLicRepository.save(mathContentsDto.toLicenseEntity());
				}
				
			//넘버링크 문제는 유사 문제 등록
			}else{
				if(mathContentsDto.getContentsClassify()==0) {
					mathContentsDto.setContentsNo(contents.getContentsNo());
					if(mathContentsDto.getMathContentsCompSeqNo() != 0) {
						mathContentsDto.setMathContentsCompSeqNo(mathContentsDto.getMathContentsCompSeqNo());
					}
					mathContentsCompRepository.save(mathContentsDto.toCompEntity());
				}else if(mathContentsDto.getContentsClassify()==4) {
					mathContentsDto.setContentsNo(contents.getContentsNo());
					if(mathContentsDto.getMathContentsIpsiSeqNo() != 0) {
						mathContentsDto.setMathContentsIpsiSeqNo(mathContentsDto.getMathContentsIpsiSeqNo());
						//입시 정보 시행연월은 모두 같게 등록
						mathContentsIpsiRepository.updateImpYearAndImpMonthByContentsNo(mathContentsDto.getImpYear(), mathContentsDto.getImpMonth(), mathContentsDto.getContentsNo());
					}
					mathContentsIpsiRepository.save(mathContentsDto.toIpsiEntity());
				}
			}
		}
		
		map.put("saveSuccess", true);
		return map;
	}
	
	
	@Transactional
	public HashMap<String, Object> registerContentsMulti(MathContentsListDto mathContentsDtoList, String path, String accessToken, boolean isManager) throws IllegalStateException, IOException {
		HashMap<String, Object> map = new HashMap<String, Object>();
		Members members = StaticSecurityUtil.getMembers();
		UUID userUniqId = members.getUserUniqId();
		List<MembersRole> roleList = members.getRole();
		boolean isTopTester = false;
		for(MembersRole role : roleList) {
			if(role.getRoleName().equals("TOP_TESTER")) {
				isTopTester = true;
			}
		}
		
		//삭제한 유형에 등록하면 에러 날 수 있음
		//관리자가 유형 삭제 하더라도 사용자가 홈페이지 새로고침 안하면 삭제한 유형 그대로 보일 수 있음
		List<String> unitAndTypeNoList = new ArrayList<>();;
		for(MathContentsDto mathContentsDto : mathContentsDtoList.getMathContents()) {
			unitAndTypeNoList.add(mathContentsDto.getUnitUniqNo()+","+mathContentsDto.getTypeNo());
		}
		List<MathTypeInfo> mathTypelist = mathTypeRepository.findByMathTypeDomainUnitUniqNoAndMathTypeDomainTypeNoIn(unitAndTypeNoList);
		if(mathContentsDtoList.getMathContents().size() != mathTypelist.size()) {
			int i=1;
			for(MathContentsDto mathContentsDto : mathContentsDtoList.getMathContents()) {
				MathTypeInfo mathTypeChk = mathTypeRepository.findByMathTypeDomainUnitUniqNoAndMathTypeDomainTypeNo(Integer.toString(mathContentsDto.getUnitUniqNo()), Integer.toString(mathContentsDto.getTypeNo()));
				if(mathTypeChk == null) {
					String ordinalNum = "";
					if(i==1) {
						ordinalNum = i+"st";
					}else if(i==2) {
						ordinalNum = i+"nd";
					}else if(i==3) {
						ordinalNum = i+"rd";
					}else {
						ordinalNum = i+"th";
					}
					map.put("saveSuccess", false);
					map.put("existMsg", true);
					map.put("serverMsg", ordinalNum+"에 등록하신 문제의 유형은 삭제 되었습니다. 새로운 유형에 등록 후 새로고침하여 주시기 바랍니다.");
					return map;
				}
				i++;
			}
			
		}
		
		
		List<MathContentsDto> successDtoList = new ArrayList<>();
		for(MathContentsDto mathContentsDto : mathContentsDtoList.getMathContents()) {
			//수정 모드인 경우 자기 자신 문제만 수정 가능
			mathContentsDto.setUserUniqId(userUniqId);
			//넘버링크 문제는 svcPosbStts=0
			//수능 및 일반 사용자 제작 문제는 svcPosbStts=1
			if(mathContentsDto.getContentsClassify() == 0) {
				mathContentsDto.setSvcPosbStts(0);
			}else{
				mathContentsDto.setSvcPosbStts(1);
			}
			//객관식 정답 없는 경우 주관식문제로 설정(정답 입력 이후 주관식 및 객관식 분류, 문제만 입력했을땐 주관식으로 우선 등록, 객관식 주관식 둘다 있을시 객관식으로 적용)
			if(mathContentsDto.getChoiceAnswer()==null) {
				mathContentsDto.setMultiChoiceType("E");
			}else{
				mathContentsDto.setMultiChoiceType("M");
			}

			//정답 존재유무 상태코드 설정, 0은 미존재, 1은 존재
			if((mathContentsDto.getAnswer()!=null && !mathContentsDto.getAnswer().isEmpty()) || mathContentsDto.getChoiceAnswer()!=null) {
				mathContentsDto.setAnsExistStts(1);
			}else {
				mathContentsDto.setAnsExistStts(0);
			}
			MathContents contents = mathContentsRepository.save(mathContentsDto.toEntity());
			mathContentsDto.setContentsNo(contents.getContentsNo());
			if(contents.getContentsClassify() == 1) {
				mathContentsLicRepository.save(mathContentsDto.toLicenseEntity());
			}else if(contents.getContentsClassify() == 4) {
				if(isTopTester) {
					map.put("existMsg", true);
					map.put("serverMsg", "해당 요청에 접근 권한이 없습니다.");
					map.put("accessDeny", true);
					return map;
				}
				mathContentsIpsiRepository.save(mathContentsDto.toIpsiEntity());
			}
			successDtoList.add(mathContentsDto);
		}
		map.put("successDtoList", successDtoList);
		map.put("saveSuccess", true);
		return map;
	}
	
	
	
	@Transactional
	public void registerContentsGram(MathContentsGrammerDto mathContentsGrammerDto){
		mathContentsGramRepository.save(mathContentsGrammerDto.toEntity());
	}
	
	@Transactional
	public void registerContentsGramMulti(List<MathContentsDto> contentsDtoList){
		List<MathContentsGrammer> grammerList = new  ArrayList<>();
		for(MathContentsDto contentsDto : contentsDtoList) {
			MathContentsGrammerDto grammerDto = new MathContentsGrammerDto();
			grammerDto.setContentsNo(contentsDto.getContentsNo());
			grammerDto.setContentsGram(contentsDto.getContentsGram());
			grammerList.add(grammerDto.toEntity());
		}
		
		mathContentsGramRepository.saveAll(grammerList);
		
	}
	
	@Transactional
	public HashMap<String, Object> takeContentsList(MathContentsDto mathContentsDto, String contentsNo) {
		HashMap<String, Object> map = new HashMap<>();
		Members members = StaticSecurityUtil.getMembers();
		UUID userUniqId = members.getUserUniqId();
		
		//List<MathContents> list = mathContentsRepository.findByUnitUniqNoAndSvcPosbSttsAndContentsClassifyOrUnitUniqNoAndSvcPosbSttsAndContentsClassifyAndMathContentsLicenseShareSttsOrderBySysCreateDateDesc
		//	(mathContentsDto.getUnitUniqNo(), 1, 0, mathContentsDto.getUnitUniqNo(), 1, 1, 1);
		List<ContentsListModel> list;
		if(contentsNo != null) {	// 문제 번호 검색하는 경우
			//문제 번호로 검색
			list = mathContentsRepository.findByContentsNoCustom(Integer.parseInt(contentsNo));
		}else {						// 단원으로 검색하는 경우
			List<Integer> uniqNoList = new ArrayList<>();
			if(mathContentsDto.getUnitUniqNoStr() != null) {
				String [] unitUniqNoArr = mathContentsDto.getUnitUniqNoStr().split("-");
				int strtUnitUniqNo = Integer.parseInt(unitUniqNoArr[0]);
				int endUnitUniqNo = Integer.parseInt(unitUniqNoArr[1]);
				List<MathUnitInfo> unitInfoList = mathUnitRepository.findByUnitUniqNoBetween(strtUnitUniqNo, endUnitUniqNo);
				for(MathUnitInfo mathUnitInfo : unitInfoList) {
					uniqNoList.add(mathUnitInfo.getUnitUniqNo());
				}
			}else {
				uniqNoList.add(mathContentsDto.getUnitUniqNo());
			}
			Page<ContentsListModel> contentsPage = mathContentsRepository.findByUnitUniqNoIn(uniqNoList, PageRequest.of(mathContentsDto.getCurPageNum(), mathContentsDto.getPageVolume()));
			list = new ArrayList<>();
			for(ContentsListModel model : contentsPage) {
				list.add(model);
			}
			map.put("totalPageCnt", contentsPage.getTotalPages());
		}
		
		
		List<MathContentsModel> dtoList= new ArrayList<>();
		List<Integer> contentsNoList = new ArrayList<>();
		for(ContentsListModel content : list) {
			MathContentsDto mathContentsDtoInner = modelMapper.map(content, MathContentsDto.class);
			MembersProfileDto membersProfileDto = modelMapper.map(content, MembersProfileDto.class);
			List<MathContentsLicenseDto> licenseList = new ArrayList<>();
			MathContentsLicenseDto mathContentsLic = modelMapper.map(content, MathContentsLicenseDto.class);
			licenseList.add(mathContentsLic);
			
			MathContentsModel mathContentsModel = modelMapper.map(mathContentsDtoInner, MathContentsModel.class);
			mathContentsModel.setMathContentsLicense(licenseList);
			mathContentsModel.setMembersProfile(membersProfileDto);
			dtoList.add(mathContentsModel);
			
			contentsNoList.add(content.getContentsNo());
		}
		map.put("mathContents", dtoList);
		
		List<MathConLikeInfo> likeInfoList = mathConLikeInfoRepository.findByMathConLikeDomainContentsNoInAndMathConLikeDomainUserUniqId(contentsNoList, userUniqId);
		List<MathConLikeInfoDto> likeInfoDtoList = new ArrayList<>();
		for(MathConLikeInfo likeInfo : likeInfoList) {
			MathConLikeInfoDto mathConLikeInfoDto = modelMapper.map(likeInfo, MathConLikeInfoDto.class);
			MathConLikeDomain mathConLikeDomain = new MathConLikeDomain();
			mathConLikeDomain.setContentsNo(mathConLikeInfoDto.getMathConLikeDomain().getContentsNo());
			mathConLikeInfoDto.setMathConLikeDomain(mathConLikeDomain);
			likeInfoDtoList.add(mathConLikeInfoDto);
		}
		
		map.put("mathConLikeInfo", likeInfoDtoList);
		
		List<MathConRepoInfo> repoInfoList = mathConRepoInfoRepository.findByMathConRepoDomainContentsNoInAndMathConRepoDomainUserUniqId(contentsNoList, userUniqId);
		List<MathConRepoInfoDto> repoInfoDtoList = new ArrayList<>();
		for(MathConRepoInfo repoInfo : repoInfoList) {
			MathConRepoInfoDto mathConRepoInfoDto = modelMapper.map(repoInfo, MathConRepoInfoDto.class);
			MathConRepoDomain mathConRepoDomain = new MathConRepoDomain();
			mathConRepoDomain.setContentsNo(mathConRepoInfoDto.getMathConRepoDomain().getContentsNo());
			mathConRepoInfoDto.setMathConRepoDomain(mathConRepoDomain);
			repoInfoDtoList.add(mathConRepoInfoDto);
		}
		
		map.put("mathconRepoInfo", repoInfoDtoList);
		
		return map;
	}
	
	
	
	@Transactional
	public HashMap<String, Object> takeWorkContentsList(MathContentsDto mathContentsDto, String contentsNo) {
		HashMap<String, Object> map = new HashMap<>();
		Members members = StaticSecurityUtil.getMembers();
		List<MembersRole> roleList = members.getRole();
		boolean isAdmin = false;
		boolean isTopTester = false;
		for(MembersRole role : roleList) {
			if(role.getRoleName().equals("ADMIN")) {
				isAdmin = true;
			}else if(role.getRoleName().equals("TOP_TESTER")) {
				isTopTester = true;
			}
		}
		
		List<MathContents> list = new ArrayList<>();
		if(contentsNo != null) {
			MathContents mathContents =  mathContentsRepository.findByContentsNo(Integer.parseInt(contentsNo));
			list.add(mathContents);
		}else {
			Page<MathContents> pageList;
			if(isAdmin || isTopTester) {
				pageList =  mathContentsRepository.findByUnitUniqNoAndContentsClassifyOrderBySysCreateDateDesc(mathContentsDto.getUnitUniqNo(), 0, PageRequest.of(mathContentsDto.getCurPageNum(), mathContentsDto.getPageVolume()));
			}else {
				pageList =  mathContentsRepository.findByUnitUniqNoAndUserUniqIdAndContentsClassifyOrderBySysCreateDateDesc(mathContentsDto.getUnitUniqNo(), members.getUserUniqId(), 0, PageRequest.of(mathContentsDto.getCurPageNum(), mathContentsDto.getPageVolume()));
			}
			for(MathContents mathContents : pageList) {
				list.add(mathContents);
			}
			map.put("totalContentsCnt", pageList.getTotalElements());
			map.put("totalPageCnt", pageList.getTotalPages());
		}
		
		
		List<MathContentsModel> dtoList= new ArrayList<>();
		for(MathContents mathContents : list) {
			MathContentsDto mathContentsDtoInner = modelMapper.map(mathContents, MathContentsDto.class);
			MathTypeInfoDto mathTypeInfoDto = modelMapper.map(mathContents.getMathTypeInfo(), MathTypeInfoDto.class);
			List<MathContentsCompDto> mathContentsCompDtoList = new ArrayList<>();
			for(MathContentsComp mathContentsComp : mathContents.getMathContentsComp()) {
				mathContentsCompDtoList.add(modelMapper.map(mathContentsComp, MathContentsCompDto.class));
			}
			
			MathContentsModel mathContentsModel = modelMapper.map(mathContentsDtoInner, MathContentsModel.class);
			mathContentsModel.setMathContentsComp(mathContentsCompDtoList);
			mathContentsModel.setMathTypeInfo(mathTypeInfoDto);
			dtoList.add(mathContentsModel);
		}
		
		map.put("mathContents", dtoList);
		return map;
	}
	
	
	@Transactional
	public HashMap<String, Object> takeContentsByContentsNo(int contentsNo){	//문제검색에서 변형문제 만들기 클릭시, 나의 제작문제에서 원본 문제 보기 클릭시
		//문제검색 페이지에서는 누구나 문제 볼 수 있음, 수정 아닌 새로운 변형문제 만들기 때문, 권한 체크 안함, 수정은 권한체크 해야함
		HashMap<String, Object> map = new HashMap<String, Object>();
		MathContentsModel mathContentsModel = new MathContentsModel();
		MathContents mathContents = mathContentsRepository.findByContentsNo(contentsNo);
		MathContentsDto mathContentsDto = modelMapper.map(mathContents, MathContentsDto.class);
		mathContentsModel =  modelMapper.map(mathContentsDto, MathContentsModel.class);
		
		if(mathContentsDto.getContentsClassify() == 1) {
			List<MathContentsLicenseDto> licenseList = new ArrayList<>();
			for(MathContentsLicense license : mathContents.getMathContentsLicense()) {
				MathContentsLicenseDto mathContentsLic = modelMapper.map(license, MathContentsLicenseDto.class);
				licenseList.add(mathContentsLic);
			}
			mathContentsModel.setMathContentsLicense(licenseList);
			
			//원본문제 제작자 프로필
			MembersProfile membersProfile = membersProfileRepository.findByUserUniqId(mathContents.getUserUniqId());
			MembersProfileDto membersProfileDto = modelMapper.map(membersProfile, MembersProfileDto.class);
			mathContentsModel.setMembersProfile(membersProfileDto);
		}
		
		MathUnitInfo mathUnitInfo= mathUnitRepository.findByUnitUniqNo(mathContents.getUnitUniqNo());
		MathUnitInfoDto mathUnitInfoDto = modelMapper.map(mathUnitInfo, MathUnitInfoDto.class);
		
		List<MathTypeInfo> mathTypeInfoList = mathTypeRepository.findByUnitUniqNoOrderByTypeOrderAsc(Integer.toString(mathContents.getUnitUniqNo()));
		List<MathTypeInfoDto> mathTypeInfoDtoList = new ArrayList<>();
		for(MathTypeInfo mathTypeInfo : mathTypeInfoList) {
			MathTypeInfoDto mathTypeInfoDto = modelMapper.map(mathTypeInfo, MathTypeInfoDto.class);
			mathTypeInfoDtoList.add(mathTypeInfoDto);
		}
		
		Members members = StaticSecurityUtil.getMembers();
		UUID userUniqId = members.getUserUniqId();
		List<Integer> contentsNoList = new ArrayList<>();
		contentsNoList.add(contentsNo);
		List<MathConLikeInfo> likeInfoList = mathConLikeInfoRepository.findByMathConLikeDomainContentsNoInAndMathConLikeDomainUserUniqId(contentsNoList, userUniqId);
		List<MathConLikeInfoDto> likeInfoDtoList = new ArrayList<>();
		for(MathConLikeInfo likeInfo : likeInfoList) {
			MathConLikeInfoDto mathConLikeInfoDto = modelMapper.map(likeInfo, MathConLikeInfoDto.class);
			MathConLikeDomain mathConLikeDomain = new MathConLikeDomain();
			mathConLikeDomain.setContentsNo(mathConLikeInfoDto.getMathConLikeDomain().getContentsNo());
			mathConLikeInfoDto.setMathConLikeDomain(mathConLikeDomain);
			likeInfoDtoList.add(mathConLikeInfoDto);
		}
		
		map.put("mathConLikeInfo", likeInfoDtoList);
		
		List<MathConRepoInfo> repoInfoList = mathConRepoInfoRepository.findByMathConRepoDomainContentsNoInAndMathConRepoDomainUserUniqId(contentsNoList, userUniqId);
		List<MathConRepoInfoDto> repoInfoDtoList = new ArrayList<>();
		for(MathConRepoInfo repoInfo : repoInfoList) {
			MathConRepoInfoDto mathConRepoInfoDto = modelMapper.map(repoInfo, MathConRepoInfoDto.class);
			MathConRepoDomain mathConRepoDomain = new MathConRepoDomain();
			mathConRepoDomain.setContentsNo(mathConRepoInfoDto.getMathConRepoDomain().getContentsNo());
			mathConRepoInfoDto.setMathConRepoDomain(mathConRepoDomain);
			repoInfoDtoList.add(mathConRepoInfoDto);
		}
		
		//나의 제작문제가 아닌 경우 복사 금지
		UUID contentsUuid = mathContentsRepository.findOnlyUuidByContentsNo(contentsNo);
		if(!contentsUuid.equals(members.getUserUniqId())) {		//컨텐츠에 등록되어있는 uuid와 사용자 uuid 같은 경우에만 수정가능
			map.put("isMyContents", false);
		}else {
			map.put("isMyContents", true);
		}
		
		map.put("mathconRepoInfo", repoInfoDtoList);
		
		map.put("myUnitInfo", mathUnitInfoDto);
		map.put("myTypeInfo", mathTypeInfoDtoList);
		map.put("myContents", mathContentsModel);
		
		return map;
	}
	
	@Transactional
	public HashMap<String, Object> takeMyWorkContents(int contentsNo){
		//권한 체크
		HashMap<String, Object> map = new HashMap<String, Object>();
		MathContents mathContents = mathContentsRepository.findByContentsNo(contentsNo);
		UUID contentsUserUniqId = mathContents.getUserUniqId();
		Members members = StaticSecurityUtil.getMembers();
		UUID userUniqId = members.getUserUniqId();
		//관리자 아닌 경우 자신이 만든 문제 외의 문제 수정 금지
		List<MembersRole> roleList =  StaticSecurityUtil.getMembers().getRole();
		boolean isAdmin = false;
		for(MembersRole role : roleList) {
			if(role.getRoleName().equals("ADMIN")) isAdmin=true;
		}
		if(isAdmin) {
			map.put("existMsg", false);
		}else {
			if(!contentsUserUniqId.equals(userUniqId)) {
				map.put("existMsg", true);
				map.put("serverMsg", "본인이 만든 문제 외의 문제는 수정할 수 없습니다.");
				map.put("myContents", null);
				return map;
			}else {
				map.put("existMsg", false);
			}
		}
		
		//사용자, 매니저 모두 사용(유사문제 있으면 유사문제 보여주고 라이선스 있으면 라이선스 보여주면 됨)
		//나의 문제 가져올땐, 이미 이전에 자기 문제만 가져오니 상관 없음
		MathContentsModel mathContentsModel = new MathContentsModel();
		MathContentsDto mathContentsDto = modelMapper.map(mathContents, MathContentsDto.class);
		mathContentsModel =  modelMapper.map(mathContentsDto, MathContentsModel.class);
		if(mathContents.getContentsClassify() == 0) {
			List<MathContentsCompDto> compList = new ArrayList<>();
			for(MathContentsComp comp : mathContents.getMathContentsComp()) {
				MathContentsCompDto mathContentsComp = modelMapper.map(comp, MathContentsCompDto.class);
				compList.add(mathContentsComp);
			}
			mathContentsModel =  modelMapper.map(mathContentsDto, MathContentsModel.class);
			mathContentsModel.setMathContentsComp(compList);
		}else if(mathContents.getContentsClassify() == 1){
			List<MathContentsLicenseDto> licenseList = new ArrayList<>();
			for(MathContentsLicense license : mathContents.getMathContentsLicense()) {
				MathContentsLicenseDto mathContentsLic = modelMapper.map(license, MathContentsLicenseDto.class);
				licenseList.add(mathContentsLic);
			}
			mathContentsModel.setMathContentsLicense(licenseList);
		}else if(mathContents.getContentsClassify() == 2){	//변형문제의 경우 원본 문제 라이선스 정보 보야줘야함
			List<MathContentsLicense> orglicenseList = mathContentsLicRepository.findByContentsNo(mathContents.getOrgContentsNo());
			List<MathContentsLicenseDto> licenseList = new ArrayList<>();
			for(MathContentsLicense license : orglicenseList) {
				MathContentsLicenseDto mathContentsLic = modelMapper.map(license, MathContentsLicenseDto.class);
				licenseList.add(mathContentsLic);
			}
			mathContentsModel.setMathContentsLicense(licenseList);
		}else if(mathContents.getContentsClassify() == 4){	//변형문제의 경우 원본 문제 라이선스 정보 보야줘야함
			List<MathContentsIpsiDto> ipsiDtoList = new ArrayList<>();
			for(MathContentsIpsi ipsi : mathContents.getMathContentsIpsi()) {
				MathContentsIpsiDto ipsiDto = modelMapper.map(ipsi, MathContentsIpsiDto.class);
				ipsiDtoList.add(ipsiDto);
			}
			mathContentsModel =  modelMapper.map(mathContentsDto, MathContentsModel.class);
			mathContentsModel.setMathContentsIpsi(ipsiDtoList);
		}
		
		//변형문제 복사 금지
		if(mathContentsModel.getContentsClassify() == 2){
			map.put("isMyContents", false);
		}else {
			map.put("isMyContents", true);
		}
				
		map.put("myContents", mathContentsModel);
		
		return map;
	}
	
	@Transactional
	public HashMap<String, Object> takeMyContentsList(int userNo, int curPageNum, int pageVolume){	//나의 제작 문제 또는 다른 사용자의 제작문제 보기(프로필)
		HashMap<String, Object> map = new HashMap<String, Object>();
		Members members = StaticSecurityUtil.getMembers();
		UUID userUniqId = members.getUserUniqId();
		Page<MathContents> list;
		if(userNo==0) {		// userNo가 0인 경우 자기 자신 문제 조회
			List<Integer> classifyList = new ArrayList<>();
			classifyList.add(0);
			classifyList.add(3);
			classifyList.add(4);
			list = mathContentsRepository.findByUserUniqIdAndContentsClassifyNotInOrderBySysCreateDateDesc(userUniqId, classifyList, PageRequest.of(curPageNum, pageVolume) );
		}else {					// userNo가 있으면 userNo로 상대방 프로필 조회
			MembersProfile profile= membersProfileRepository.findByUserNo(userNo);
			list = mathContentsRepository.findByUserUniqIdAndContentsClassifyOrUserUniqIdAndContentsClassifyAndMathContentsLicenseShareSttsOrderBySysCreateDateDesc
					(profile.getUserUniqId(), 2, profile.getUserUniqId(), 1, 1,  PageRequest.of(curPageNum, pageVolume));
		}
		
		map.put("totalPageCnt", list.getTotalPages());
		
		List<Integer> contentsNoList = new ArrayList<>();
		List<MathContentsModel> dtoList= new ArrayList<>();
		for(MathContents mathContents : list) {
			contentsNoList.add(mathContents.getContentsNo());
			MathContentsDto mathContentsDtoInner = modelMapper.map(mathContents, MathContentsDto.class);
			MathUnitInfoDto mathUnitInfoDto = modelMapper.map(mathContents.getMathUnitInfo(), MathUnitInfoDto.class);
			MathTypeInfoDto mathTypeInfoDto = modelMapper.map(mathContents.getMathTypeInfo(), MathTypeInfoDto.class);
			MathContentsModel mathContentsModel = modelMapper.map(mathContentsDtoInner, MathContentsModel.class);
			if(mathContents.getMathContentsLicense() != null) {
				List<MathContentsLicenseDto> licenseList = new ArrayList<>();
				for(MathContentsLicense license : mathContents.getMathContentsLicense()) {
					MathContentsLicenseDto mathContentsLic = modelMapper.map(license, MathContentsLicenseDto.class);
					licenseList.add(mathContentsLic);
				}
				mathContentsModel.setMathContentsLicense(licenseList);
			}
		
			mathContentsModel.setMathTypeInfo(mathTypeInfoDto);
			mathContentsModel.setMathUnitInfo(mathUnitInfoDto);
			dtoList.add(mathContentsModel);
		}
		
		map.put("myContentsList", dtoList);
		
		if(userNo!=0) {	//다른 사용자 프로필 볼때에는 좋아요 및 저장 내역까지 함께 보기
			List<MathConLikeInfo> likeInfoList = mathConLikeInfoRepository.findByMathConLikeDomainContentsNoInAndMathConLikeDomainUserUniqId(contentsNoList, userUniqId);
			List<MathConLikeInfoDto> likeInfoDtoList = new ArrayList<>();
			for(MathConLikeInfo likeInfo : likeInfoList) {
				MathConLikeInfoDto mathConLikeInfoDto = modelMapper.map(likeInfo, MathConLikeInfoDto.class);
				MathConLikeDomain mathConLikeDomain = new MathConLikeDomain();
				mathConLikeDomain.setContentsNo(mathConLikeInfoDto.getMathConLikeDomain().getContentsNo());
				mathConLikeInfoDto.setMathConLikeDomain(mathConLikeDomain);
				likeInfoDtoList.add(mathConLikeInfoDto);
			}
			
			map.put("mathConLikeInfo", likeInfoDtoList);
			
			List<MathConRepoInfo> repoInfoList = mathConRepoInfoRepository.findByMathConRepoDomainContentsNoInAndMathConRepoDomainUserUniqId(contentsNoList, userUniqId);
			List<MathConRepoInfoDto> repoInfoDtoList = new ArrayList<>();
			for(MathConRepoInfo repoInfo : repoInfoList) {
				MathConRepoInfoDto mathConRepoInfoDto = modelMapper.map(repoInfo, MathConRepoInfoDto.class);
				MathConRepoDomain mathConRepoDomain = new MathConRepoDomain();
				mathConRepoDomain.setContentsNo(mathConRepoInfoDto.getMathConRepoDomain().getContentsNo());
				mathConRepoInfoDto.setMathConRepoDomain(mathConRepoDomain);
				repoInfoDtoList.add(mathConRepoInfoDto);
			}
			map.put("mathconRepoInfo", repoInfoDtoList);
		}
		
		return map;
	}
	
	
	@Transactional
	public HashMap<String, Object> takeMyRepo(int curPageNum, int pageVolume){
		HashMap<String, Object> map = new HashMap<>();
		Members members = StaticSecurityUtil.getMembers();
		UUID userUniqId = members.getUserUniqId();
		Page<MathConRepoInfo> repoList = mathConRepoInfoRepository.findByMathConRepoDomainUserUniqId(userUniqId, PageRequest.of(curPageNum, pageVolume));
		
		map.put("totalPageCnt", repoList.getTotalPages());
		
		List<Integer> contentsNoList = new ArrayList<>();
		for(MathConRepoInfo mathConRepo : repoList) {
			contentsNoList.add(mathConRepo.getMathConRepoDomain().getContentsNo());
		}
		List<ContentsListModel> mathContetsList = mathContentsRepository.findByContentsNoInCustom(contentsNoList);
		List<MathContentsModel> dtoList= new ArrayList<>();
		for(ContentsListModel content : mathContetsList) {
			//sysCreateDate를 저장소에 저장한 시간으로 셋팅
			MathContentsDto mathContentsDtoInner = modelMapper.map(content, MathContentsDto.class);
			InnerLoop : for(MathConRepoInfo mathConRepo : repoList) {
				if(mathContentsDtoInner.getContentsNo() == mathConRepo.getMathConRepoDomain().getContentsNo()) {
					mathContentsDtoInner.setSysCreateDate(mathConRepo.getSysCreateDate());
					continue InnerLoop;
				}
			}
			MembersProfileDto membersProfileDto = modelMapper.map(content, MembersProfileDto.class);
			List<MathContentsLicenseDto> licenseList = new ArrayList<>();
			MathContentsLicenseDto mathContentsLic = modelMapper.map(content, MathContentsLicenseDto.class);
			licenseList.add(mathContentsLic);
			
			MathContentsModel mathContentsModel = modelMapper.map(mathContentsDtoInner, MathContentsModel.class);
			
			MathUnitInfoDto mathUnitInfoDto = modelMapper.map(content, MathUnitInfoDto.class);
			mathContentsModel.setMathContentsLicense(licenseList);
			mathContentsModel.setMembersProfile(membersProfileDto);
			mathContentsModel.setMathUnitInfo(mathUnitInfoDto);
			dtoList.add(mathContentsModel);
			
			contentsNoList.add(content.getContentsNo());
		}
		map.put("mathContents", dtoList);
		return map;
	}
	
	
	public MathUnitInfo takeUnitInfoByUnitUniqNo(int unitUniqNo){
		return mathUnitRepository.findByUnitUniqNo(unitUniqNo);
	}	
	
	public int changeConOrSolImg(MathContentsDto mathContentsDto, String path) throws IllegalStateException, IOException{
		UUID conUuid = mathContentsRepository.findOnlyUuidByContentsNo(mathContentsDto.getContentsNo());
		Members members = StaticSecurityUtil.getMembers();
		List<MembersRole> roleList = members.getRole();
		boolean isAdmin = false;
		for(MembersRole role : roleList) {
			if(role.getRoleName().equals("ADMIN")) {
				isAdmin = true;
			}
		}
		if(!isAdmin) {
			if(!conUuid.equals(members.getUserUniqId())) {
				return -1;
			}
		}
		
		Random random1 = new Random();
		if(mathContentsDto.getContentsImgFile()!=null && !mathContentsDto.getContentsImgFile().isEmpty()) {
			long currentTime1 = System.currentTimeMillis();
			int randomValue1 = random1.nextInt(100);

			String fileName = Long.toString(currentTime1) + "_"+randomValue1+"_"+mathContentsDto.getContentsImgFile().getOriginalFilename();
			MathContents mathContents = mathContentsRepository.findByContentsNo(mathContentsDto.getContentsNo());
			String contentsImgName = mathContents.getContentsImg();
			// 새이미지 추가
			File file = new File(path+"/contentsImg" , fileName);
			mathContentsDto.getContentsImgFile().transferTo(file);
			mathContentsDto.setImgPath("/webapp/static/contentsImg/");
			mathContentsDto.setContentsImg(fileName);
			int isSuccess = mathContentsRepository.changeConImg(mathContentsDto.getContentsNo(), "/webapp/static/contentsImg/", mathContentsDto.getContentsImg());
			
			//이전 이미지 삭제
			File delFile = new File(path+"/contentsImg/"+contentsImgName);
			delFile.delete();
			return isSuccess;
		}		
		if(mathContentsDto.getSolutionImgFile()!=null && !mathContentsDto.getSolutionImgFile().isEmpty()) {
			long currentTime1 = System.currentTimeMillis();
			int randomValue1 = random1.nextInt(100);

			String fileName = Long.toString(currentTime1) + "_"+randomValue1+"_"+mathContentsDto.getSolutionImgFile().getOriginalFilename();
			MathContents mathContents = mathContentsRepository.findByContentsNo(mathContentsDto.getContentsNo());
			String solutionImgName = mathContents.getSolutionImg();
			//새 이미지 추가
			File file = new File(path+"/solutionImg" , fileName);
			mathContentsDto.getSolutionImgFile().transferTo(file);
			mathContentsDto.setSolutionImgPath("/webapp/static/solutionImg/");
			mathContentsDto.setSolutionImg(fileName);
			int isSuccess = mathContentsRepository.changeSolImg(mathContentsDto.getContentsNo(), "/webapp/static/solutionImg/", mathContentsDto.getSolutionImg());
			
			//이전 이미지 삭제
			File delFile = new File(path+"/solutionImg/"+solutionImgName);
			delFile.delete();
			return isSuccess;
		}
		return 0;
		
	}	
	
	
	public int delConOrSolImg(int contentsNo, String conOrSol, String path ){
		UUID conUuid = mathContentsRepository.findOnlyUuidByContentsNo(contentsNo);
		Members members = StaticSecurityUtil.getMembers();
		List<MembersRole> roleList = members.getRole();
		boolean isAdmin = false;
		for(MembersRole role : roleList) {
			if(role.getRoleName().equals("ADMIN")) {
				isAdmin = true;
			}
		}
		if(!isAdmin) {
			if(!conUuid.equals(members.getUserUniqId())) {
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
	
	
	public int changeSvcStts(int contentsNo, int svcStts) {
		return mathContentsRepository.changeSvcStts(contentsNo, svcStts);
	}
	
	public HashMap<String, Object> registerCompContents(MathContentsCompListDto compContentsList) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		Members members = StaticSecurityUtil.getMembers();
		List<MathContentsComp> list = new ArrayList<>();
		for(MathContentsCompDto compDto :compContentsList.getMathContentsComp()) {
			if(compDto.getContentsNo() != 0) {
				compDto.setUserUniqId(members.getUserUniqId());
				list.add(compDto.toEntity());
			}
		}
		List<MathContentsComp> compList = mathContentsCompRepository.saveAll(list);
		List<MathContentsCompDto> compDtoList = new ArrayList<>();
		for(MathContentsComp comp : compList) {
			MathContentsCompDto compModel = modelMapper.map(comp, MathContentsCompDto.class);
			compDtoList.add(compModel);
		}
		map.put("isSuccess", true);
		map.put("successObj", compDtoList);
		return map;
	}
	
	public HashMap<String, Object> registerIpsiContents(MathContentsIpsiListDto ipsiContentsList) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		List<MathContentsIpsi> list = new ArrayList<>();
		for(MathContentsIpsiDto ipsiContentsDto :ipsiContentsList.getMathContentsIpsi()) {
			list.add(ipsiContentsDto.toEntity());
		}
		List<MathContentsIpsi> ipsiList = mathContentsIpsiRepository.saveAll(list);
		List<MathContentsIpsiDto> ipsiDtoList = new ArrayList<>();
		for(MathContentsIpsi ipsiCon : ipsiList) {
			MathContentsIpsiDto ipsiDto = modelMapper.map(ipsiCon, MathContentsIpsiDto.class);
			ipsiDtoList.add(ipsiDto);
		}
		map.put("isSuccess", true);
		map.put("successObj", ipsiDtoList);
		return map;
	}
	
	@Transactional
	public HashMap<String, Object> delIpsiContents(int seqNo, int contentsNo) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		if(mathContentsIpsiRepository.exsistOverOneByContentsNo(contentsNo)) {
			mathContentsIpsiRepository.deleteById(seqNo);
			List<MathContentsIpsi> ipsiList = mathContentsIpsiRepository.findByContentsNo(contentsNo);
			List<MathContentsIpsiDto> ipsiDtoList = new ArrayList<>();
			for(MathContentsIpsi comp : ipsiList) {
				MathContentsIpsiDto CompModel = modelMapper.map(comp, MathContentsIpsiDto.class);
				ipsiDtoList.add(CompModel);
			}
			map.put("successObj", ipsiDtoList);
			map.put("isSuccess", true);
		}else {
			map.put("isSuccess", false);
		}
		return map;
	}
	
	@Transactional
	public HashMap<String, Object> delCompContents(int seqNo, int contentsNo) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		if(mathContentsCompRepository.exsistOverOneByContentsNo(contentsNo)) {
			mathContentsCompRepository.deleteById(seqNo);
			List<MathContentsComp> compList = mathContentsCompRepository.findByContentsNo(contentsNo);
			List<MathContentsCompDto> compDtoList = new ArrayList<>();
			for(MathContentsComp comp : compList) {
				MathContentsCompDto compModel = modelMapper.map(comp, MathContentsCompDto.class);
				compDtoList.add(compModel);
			}
			map.put("successObj", compDtoList);
			map.put("isSuccess", true);
		}else {
			map.put("isSuccess", false);
		}
		return map;
	}
	
	@Transactional
	public HashMap<String, Object> myContentsDel(int contentsNo, String path){
		Members members = StaticSecurityUtil.getMembers();
		UUID userUniqId = members.getUserUniqId();
		HashMap<String, Object> map = new HashMap<String, Object>();
		MathContents mathContents = mathContentsRepository.findByContentsNo(contentsNo);
		UUID contentsUuid = mathContents.getUserUniqId();
		
		map.put("isDeleted", false);		//이미지 파일 정보 db에서 삭제 가능 구분 여부
		if(!contentsUuid.equals(userUniqId)) {		// 컨텐츠에 등록되어있는 uuid와 사용자 uuid 같은 경우에만 삭제가능
			map.put("existMsg", true);
			map.put("serverMsg", "자신의 문제가 아닌 경우 삭제 할 수 없습니다.");
			map.put("myContents", null);
			return map;
		}
		
		if(mathContents.getContentsClassify() == 0) {
			map.put("existMsg", true);
			map.put("serverMsg", "넘버링크 제작 문제는 삭제할 수 없습니다.");
			map.put("myContents", null);
		}else if(mathContents.getContentsClassify() == 1) {
			MathContents mathContentsForDel = mathContentsRepository.findByContentsNo(contentsNo);
			if(mathContentsForDel.getContentsImg() != null && !mathContentsForDel.getContentsImg().isEmpty()) {
				File conFile = new File(path+"/contentsImg/"+mathContentsForDel.getContentsImg());
				conFile.delete();
			}
			if(mathContentsForDel.getSolutionImg() != null && !mathContentsForDel.getSolutionImg().isEmpty()) {
				File solFile = new File(path+"/solutionImg/"+mathContentsForDel.getSolutionImg());
				solFile.delete();
			}
			
			if(mathContents.getTransConCnt()==0) {	//변형문제 없는 제작문제는 바로 삭제 가능(문법테이블 행도 삭제 가능)
				map.put("isDeleted", true);
				mathConLikeInfoRepository.deleteByMathConLikeDomainContentsNo(contentsNo); //좋아요 정보 삭제
				mathConRepoInfoRepository.deleteByMathConRepoDomainContentsNo(contentsNo); //저장소 정보 삭제
				mathContentsLicRepository.deleteByContentsNo(contentsNo); //라이선스 정보 삭제
				mathContentsRepository.deleteByContentsNo(contentsNo);
				mathContentsGramRepository.deleteByContentsNo(contentsNo);
				map.put("existMsg", false);
			}else{	//변형문제가 존재하는 제작문제는 contents_classify 3으로 변경(문법테이블 행 삭제 불가)
				map.put("isDeleted", true);	//변형문제 contents_classify 3으로 변경되면 다른 사용자에게 안 보이므로  이미지 파일 목록 DB에서 삭제
				mathConLikeInfoRepository.deleteByMathConLikeDomainContentsNo(contentsNo); //좋아요 정보 삭제
				mathConRepoInfoRepository.deleteByMathConRepoDomainContentsNo(contentsNo); //저장소 정보 삭제
				mathContentsRepository.updateContentsClassify(contentsNo, 3);
				map.put("existMsg", false);
			}
		}else if(mathContents.getContentsClassify() == 2) {	//변형문제는 삭제 가능(문법테이블 행도 삭제 가능)
			map.put("isDeleted", true);
			int transConCnt = mathContentsRepository.countByOrgContentsNo(mathContents.getOrgContentsNo());
			//원본문제 TransConCnt -1 하기
			mathConLikeInfoRepository.deleteByMathConLikeDomainContentsNo(contentsNo); //좋아요 정보 삭제
			mathConRepoInfoRepository.deleteByMathConRepoDomainContentsNo(contentsNo); //저장소 정보 삭제
			mathContentsRepository.updateTransConCnt(mathContents.getOrgContentsNo(), transConCnt-1);
			mathContentsRepository.deleteByContentsNo(contentsNo);
			mathContentsGramRepository.deleteByContentsNo(contentsNo);
			map.put("existMsg", false);
		}
		
		return map;
	}
	
	
	@Transactional
	public int myRepoDel(int contentsNo){
		Members members = StaticSecurityUtil.getMembers();
		UUID userUniqId = members.getUserUniqId();
		return mathConRepoInfoRepository.deleteByMathConRepoDomainContentsNoAndMathConRepoDomainUserUniqId(contentsNo, userUniqId);
	}
	
	
	@Transactional
	public int likeContents(int contentsno) {
		Members members = StaticSecurityUtil.getMembers();
		UUID userUniqId = members.getUserUniqId();
		
		MathConLikeInfo mathConLikeInfo = mathConLikeInfoRepository.findByMathConLikeDomainContentsNoAndMathConLikeDomainUserUniqId(contentsno, userUniqId);
		if(mathConLikeInfo == null) {
			MathConLikeInfoDto mathConLikeInfoDto = new MathConLikeInfoDto();
			MathConLikeDomain mathConLikeDomain = new MathConLikeDomain();
			mathConLikeDomain.setContentsNo(contentsno);
			mathConLikeDomain.setUserUniqId(userUniqId);
			mathConLikeInfoDto.setMathConLikeDomain(mathConLikeDomain);
			mathConLikeInfoRepository.save(mathConLikeInfoDto.toEntity());
		}else {
			mathConLikeInfoRepository.deleteByMathConLikeDomainContentsNoAndMathConLikeDomainUserUniqId(contentsno, userUniqId);
		}
		
		return 0;
	}
	
	@Transactional
	public int putInMyRepo(int contentsno) {
		Members members = StaticSecurityUtil.getMembers();
		UUID userUniqId = members.getUserUniqId();
		
		MathConRepoInfo mathConRepoInfo = mathConRepoInfoRepository.findByMathConRepoDomainContentsNoAndMathConRepoDomainUserUniqId(contentsno, userUniqId);
		if(mathConRepoInfo == null) {
			MathConRepoInfoDto mathConRepoInfoDto = new MathConRepoInfoDto();
			MathConRepoDomain mathConRepoDomain = new MathConRepoDomain();
			mathConRepoDomain.setContentsNo(contentsno);
			mathConRepoDomain.setUserUniqId(userUniqId);
			mathConRepoInfoDto.setMathConRepoDomain(mathConRepoDomain);
			mathConRepoInfoRepository.save(mathConRepoInfoDto.toEntity());
		}else {
			mathConRepoInfoRepository.deleteByMathConRepoDomainContentsNoAndMathConRepoDomainUserUniqId(contentsno, userUniqId);
		}
		
		return 0;
	}
	
	@Transactional
	public HashMap<String, Object> chngQuesType(MathTypeInfoModel mathTypeInfoModel) {
		MathTypeInfo orgMathTypeInfo = mathTypeRepository.findByMathTypeDomainUnitUniqNoAndMathTypeDomainTypeNo(mathTypeInfoModel.getUnitUniqNo(), mathTypeInfoModel.getTypeNo());
		MathTypeDomain mathTypeDomain = new MathTypeDomain();
		mathTypeDomain.setUnitUniqNo(mathTypeInfoModel.getUnitUniqNo());
		mathTypeDomain.setTypeNo(mathTypeInfoModel.getTypeNo());
		MathTypeInfoDto mathTypeInfoDto = new MathTypeInfoDto();
		mathTypeInfoDto.setMathTypeDomain(mathTypeDomain);
		mathTypeInfoDto.setQuesType(mathTypeInfoModel.getQuesType());
		mathTypeInfoDto.setTypeOrder(orgMathTypeInfo.getTypeOrder());
		MathTypeInfo mathTypeInfo = mathTypeRepository.save(mathTypeInfoDto.toEntity());
		boolean isSuccess = entityManager.contains(mathTypeInfo);
		HashMap<String, Object> map = new HashMap<>();
		if(isSuccess) {
			map.put("isSuccess", true);
			MathTypeInfoDto newMathTypeInfoDto = modelMapper.map(mathTypeInfo, MathTypeInfoDto.class);
			map.put("mathTypeInfo", newMathTypeInfoDto);
		}else {
			map.put("isSuccess", false);
		}
		
		return map;
	}
	
	public HashMap<String, Object> takeConCntByUnitUniqNo(String unitUnqiNo){
		List<ContentsCnt> list = mathContentsRepository.contentsCntByUnitUniqNo(Integer.parseInt(unitUnqiNo));
		HashMap<String, Object> map = new HashMap<>();
		map.put("cntList", list);
		return map;
	}
	
	@Transactional
	public HashMap<String, Object> typeDel(String unitUnqiNo, String typeNo){
		 long contentsCnt = mathContentsRepository.countByUnitUniqNoAndTypeNo(Integer.parseInt(unitUnqiNo), Integer.parseInt(typeNo));
		 HashMap<String, Object> map = new HashMap<>();
		 if(contentsCnt>0) {
			 map.put("isSuccess", false);
			 return map;
		 }
		 mathTypeRepository.deleteByMathTypeDomainUnitUniqNoAndMathTypeDomainTypeNo(unitUnqiNo, typeNo);
		 map.put("isSuccess", true);
		 return map;
	}
	
	@Transactional
	public HashMap<String, Object> mathTypeAdd(MathTypeInfoModel mathTypeInfoModel){
		 List<MathTypeInfo> mathTypeInfoList = mathTypeRepository.findByMathTypeDomainUnitUniqNo(mathTypeInfoModel.getUnitUniqNo());
		 int lastTypeNo = 1;
		 int lastTypeOrder = 1;
		 for(MathTypeInfo mathTypeInfo : mathTypeInfoList) {
			 if(lastTypeNo<Integer.parseInt(mathTypeInfo.getMathTypeDomain().getTypeNo())) {
				 lastTypeNo=Integer.parseInt(mathTypeInfo.getMathTypeDomain().getTypeNo());
			 }
			 if(lastTypeOrder<mathTypeInfo.getTypeOrder()) {
				 lastTypeOrder=mathTypeInfo.getTypeOrder();
			 }
		 }
		 MathTypeDomain mathTypeDomain= new MathTypeDomain();
		 mathTypeDomain.setUnitUniqNo(mathTypeInfoModel.getUnitUniqNo());
		 mathTypeDomain.setTypeNo(Integer.toString(lastTypeNo+1));
		 
		 MathTypeInfoDto mathTypeInfoDto = new MathTypeInfoDto();
		 mathTypeInfoDto.setMathTypeDomain(mathTypeDomain);
		 mathTypeInfoDto.setQuesType(mathTypeInfoModel.getQuesType());
		 mathTypeInfoDto.setTypeOrder(lastTypeOrder+1);
		 MathTypeInfo mathTypeInfo = mathTypeRepository.save(mathTypeInfoDto.toEntity());
		 
		 boolean isSuccess = entityManager.contains(mathTypeInfo);
		 HashMap<String, Object> map = new HashMap<>();
		 if(isSuccess) {
			map.put("isSuccess", true);
			map.put("mathTypeInfo", mathTypeInfo);
		 }else {
			map.put("isSuccess", false);
		 }
		 return map;
	}
	
	@Transactional
	public HashMap<String, Object> contentsMoveFromTo(String fromUnitUniqNo, String fromTypeNo, String toUnitUniqNo, String toTypeNo){
		mathContentsRepository.contentsMoveFromTo(Integer.parseInt(fromUnitUniqNo), Integer.parseInt(fromTypeNo), Integer.parseInt(toUnitUniqNo), Integer.parseInt(toTypeNo));
		HashMap<String, Object> map = new HashMap<>();
		map.put("isSuccess", true);
		return map;
	}
	
	@Transactional
	public HashMap<String, Object> mathTypeOrderChng(List<MathTypeInfoModel> mathTypeInfoModelList){
		List<MathTypeInfo> mathTypeInfoList = mathTypeRepository.findByMathTypeDomainUnitUniqNo(mathTypeInfoModelList.get(0).getUnitUniqNo());
		
		List<MathTypeInfo> mathTypeList = new ArrayList<>();
		for(MathTypeInfoModel mathTypeInfoModel : mathTypeInfoModelList) {
			 MathTypeDomain mathTypeDomain= new MathTypeDomain();
			 mathTypeDomain.setUnitUniqNo(mathTypeInfoModel.getUnitUniqNo());
			 mathTypeDomain.setTypeNo(mathTypeInfoModel.getTypeNo());
			 MathTypeInfoDto mathTypeInfoDto = new MathTypeInfoDto();
			 mathTypeInfoDto.setMathTypeDomain(mathTypeDomain);
			 for(MathTypeInfo mathTypeInfo : mathTypeInfoList) {
				 if(mathTypeInfo.getMathTypeDomain().getTypeNo().equals(mathTypeInfoModel.getTypeNo())) {
					 mathTypeInfoDto.setQuesType(mathTypeInfo.getQuesType());
				 }
			 }
			 mathTypeInfoDto.setTypeOrder(mathTypeInfoModel.getTypeOrder());
			 mathTypeList.add(mathTypeInfoDto.toEntity());
		}
		mathTypeRepository.saveAll(mathTypeList);
		HashMap<String, Object> map = new HashMap<>();
		map.put("isSuccess", true);
		return map;
	}
	
	public HashMap<String, Object> mathContentsStatistic(){
		
		HashMap<String, Object> map = new HashMap<>();
		List<CustomTenFieldDto> list = mathContentsRepository.mathContentsStatistic();
		CustomTenFieldDto customHeaderDto = new CustomTenFieldDto("프로필", "나이", "문제 수", null, null, null, null, null, null, null);
		
		LocalDate now = LocalDate.now();
		String fullYearStr = Integer.toString(now.getYear());
		int year = Integer.parseInt(fullYearStr.substring(2));
		List<CustomTenFieldDto> newList = new ArrayList<>();
		for(CustomTenFieldDto dto: list){
			if(dto.getNbCol2() == null) {
				dto.setNbCol2("미인증");
			}else {
				int memberBirthYear = Integer.parseInt(dto.getNbCol2().toString());
				
				int memberAge = 0;
				if(memberBirthYear>year) {
					memberBirthYear=memberBirthYear+1900;
				}else {
					memberBirthYear=memberBirthYear+2000;
				}
				memberAge = Integer.parseInt(fullYearStr)-memberBirthYear+1;
				dto.setNbCol2(memberAge);
			}
			
			
			newList.add(dto);
		}
		newList.add(0, customHeaderDto);
		
		List<CustomTenFieldDto> list2 = mathContentsRepository.statisticMathContentsUsageGroupBySysCreateDateMonth();
		CustomTenFieldDto customHeaderDto2 = new CustomTenFieldDto(list2.get(0).getNbCol1(), list2.get(1).getNbCol1(), 
				list2.get(2).getNbCol1(), list2.get(3).getNbCol1(), list2.get(4).getNbCol1(), list2.get(5).getNbCol1(), 
				list2.size()>6 ? list2.get(6).getNbCol1() : null, list2.size()>7 ? list2.get(7).getNbCol1() : null,
				list2.size()>8 ? list2.get(8).getNbCol1() : null, list2.size()>9 ? list2.get(9).getNbCol1() : null);
		CustomTenFieldDto customBodyDto2 = new CustomTenFieldDto(list2.get(0).getNbCol2(), list2.get(1).getNbCol2(), 
				list2.get(2).getNbCol2(), list2.get(3).getNbCol2(), list2.get(4).getNbCol2(), list2.get(5).getNbCol2(), 
				list2.size()>6 ? list2.get(6).getNbCol2() : null, list2.size()>7 ? list2.get(7).getNbCol2() : null,
				list2.size()>8 ? list2.get(8).getNbCol2() : null, list2.size()>9 ? list2.get(9).getNbCol2() : null);
		List<CustomTenFieldDto> newList2 = new ArrayList<>();
		newList2.add(0, customBodyDto2);
		newList2.add(0, customHeaderDto2);
		
		List<CustomTenFieldDto> list3 = mathContentsRepository.statisticContentsUsageByProfile();
		CustomTenFieldDto customHeaderDto3 = new CustomTenFieldDto("미등록", "원장", "강사", "교사","학부모","학생", "기타", null, null,null);
		list3.add(0, customHeaderDto3);
		
		List<CustomTenFieldDto> list4 = mathContentsRepository.statisticMathContentsUsageByDayOfWeek();
		CustomTenFieldDto customHeaderDto4 = new CustomTenFieldDto("월요일", "화요일", "수요일", "목요일","금요일","토요일", "일요일", null, null,null);
		list4.add(0, customHeaderDto4);
		
		List<CustomTenFieldDto> list5 = mathContentsRepository.statisticMathContentsUsageByClassifySvcPosbSttsShareStts();
		CustomTenFieldDto customHeaderDto5 = new CustomTenFieldDto("문제구분", "문제수", null, null,null,null, null, null, null,null);
		list5.add(0, customHeaderDto5);
		
		map.put("memberMathContentsCnt", newList);
		map.put("mathContentsCntByMonthly", newList2);
		map.put("mathContentsCntByProfile", list3);
		map.put("mathContentsCntByWeekday", list4);
		map.put("mathContentsCntByShareStts", list5);
		return map;
	}
	
	@Transactional
	public HashMap<String, Object> takeIpsiYear(){
		List<Integer> list = mathContentsIpsiRepository.takeImpYearDistinct();
		HashMap<String, Object> map = new HashMap<>();
		map.put("isSuccess", true);
		map.put("impYearList", list);
		return map;
	}

	@Transactional
	public HashMap<String, Object> takeIpsiMonth(int impYear){
		List<Integer> list = mathContentsIpsiRepository.takeImpYearDistinctByImpYear(impYear);
		HashMap<String, Object> map = new HashMap<>();
		map.put("isSuccess", true);
		map.put("impMonthList", list);
		return map;
	}
	
	@Transactional
	public HashMap<String, Object> findByMathContentsIpsiImpYear(int impYear, int impMonth, int curPageNum, int pageVolume){
		Page<MathContents> list;
		if(impMonth == 0) {
			list = mathContentsRepository.findByMathContentsIpsiImpYear(impYear, PageRequest.of(curPageNum, pageVolume));
		}else {
			list = mathContentsRepository.findByMathContentsIpsiImpYearAndMathContentsIpsiImpMonth(impYear, impMonth, PageRequest.of(curPageNum, pageVolume));
		}
		
		List<MathContentsModel> dtoList= new ArrayList<>();
		for(MathContents mathContents : list) {
			MathContentsDto mathContentsDtoInner = modelMapper.map(mathContents, MathContentsDto.class);
			MathUnitInfoDto mathUnitInfoDto = modelMapper.map(mathContents.getMathUnitInfo(), MathUnitInfoDto.class);
			MathTypeInfoDto mathTypeInfoDto = modelMapper.map(mathContents.getMathTypeInfo(), MathTypeInfoDto.class);
			List<MathContentsIpsiDto> mathContentsIpsiDtoList = new ArrayList<>();
			for(MathContentsIpsi mathContentsIpsi : mathContents.getMathContentsIpsi()) {
				mathContentsIpsiDtoList.add(modelMapper.map(mathContentsIpsi, MathContentsIpsiDto.class));
			}
			
			MathContentsModel mathContentsModel = modelMapper.map(mathContentsDtoInner, MathContentsModel.class);
			mathContentsModel.setMathUnitInfo(mathUnitInfoDto);
			mathContentsModel.setMathContentsIpsi(mathContentsIpsiDtoList);
			mathContentsModel.setMathTypeInfo(mathTypeInfoDto);
			dtoList.add(mathContentsModel);
		}
		HashMap<String, Object> map = new HashMap<>();
		map.put("totalContentsCnt", list.getTotalElements());
		map.put("totalPageCnt", list.getTotalPages());
		map.put("isSuccess", true);
		map.put("mathContentsList", dtoList);
		return map;
	}
	
	@Transactional
	public HashMap<String, Object> takeMathContentsIpsiByContentsNo(int contentsNo){
		List<Integer> contentsNoList = new ArrayList<>();
		contentsNoList.add(contentsNo);
		List<MathContents> list = mathContentsRepository.findByContentsNoInAndSvcPosbSttsAndContentsClassifyNot(contentsNoList, 1, 0);
		
		List<MathContentsModel> dtoList= new ArrayList<>();
		for(MathContents mathContents : list) {
			MathContentsDto mathContentsDtoInner = modelMapper.map(mathContents, MathContentsDto.class);
			MathUnitInfoDto mathUnitInfoDto = modelMapper.map(mathContents.getMathUnitInfo(), MathUnitInfoDto.class);
			MathTypeInfoDto mathTypeInfoDto = modelMapper.map(mathContents.getMathTypeInfo(), MathTypeInfoDto.class);
			List<MathContentsIpsiDto> mathContentsIpsiDtoList = new ArrayList<>();
			for(MathContentsIpsi mathContentsIpsi : mathContents.getMathContentsIpsi()) {
				mathContentsIpsiDtoList.add(modelMapper.map(mathContentsIpsi, MathContentsIpsiDto.class));
			}
			
			MathContentsModel mathContentsModel = modelMapper.map(mathContentsDtoInner, MathContentsModel.class);
			mathContentsModel.setMathUnitInfo(mathUnitInfoDto);
			mathContentsModel.setMathContentsIpsi(mathContentsIpsiDtoList);
			mathContentsModel.setMathTypeInfo(mathTypeInfoDto);
			dtoList.add(mathContentsModel);
		}
		HashMap<String, Object> map = new HashMap<>();
		map.put("isSuccess", true);
		map.put("mathContentsList", dtoList);
		return map;
	}
	
	
}
