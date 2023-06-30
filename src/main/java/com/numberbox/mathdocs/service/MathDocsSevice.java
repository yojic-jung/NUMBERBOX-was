package com.numberbox.mathdocs.service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.modelmapper.ModelMapper;
import org.qlrm.mapper.JpaResultMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.numberbox.common.util.CustomTenFieldDto;
import com.numberbox.mathdocs.dto.MathDocsPaperDto;
import com.numberbox.mathdocs.dto.MathDocsUsageDto;
import com.numberbox.mathdocs.entity.MathDocsPaper;
import com.numberbox.mathdocs.entity.MathDocsUsage;
import com.numberbox.mathdocs.repository.MathDocsPaperRepository;
import com.numberbox.mathdocs.repository.MathDocsUsageRepository;
import com.numberbox.mathinfo.domain.MathTypeDomain;
import com.numberbox.mathinfo.dto.MathContentsDto;
import com.numberbox.mathinfo.dto.MathContentsDtoForDocs;
import com.numberbox.mathinfo.dto.MathTypeInfoDto;
import com.numberbox.mathinfo.entity.MathContents;
import com.numberbox.mathinfo.repository.MathContentsRepository;
import com.numberbox.members.entity.Members;
import com.numberbox.members.entity.MembersRole;
import com.numberbox.members.repository.MembersRoleRepository;
import com.numberbox.security.util.StaticSecurityUtil;

@Service
public class MathDocsSevice {

	@PersistenceContext
    EntityManager entityManager;

	@Autowired
	MathContentsRepository mathContentsRepository;
	
	@Autowired
	MathDocsPaperRepository mathDocsPaperRepository;
	@Autowired
	MathDocsUsageRepository mathDocsUsageRepository;
	@Autowired
	private MembersRoleRepository membersRoleRepository;
	
	@Autowired
	ModelMapper modelMapper;
	
	public List<MathContentsDto> takeMathSubjectInfo(String unitUniqNoAndTypeNo, int quesLevel, int contentCnt){
		int startLv;
		int endLv;
		
		int subStartLv=0;
		int subEndtLv=0;
		int finalLv=0;		//메인과 서브 난이도의 문제 부족시 finalLv 문제로 추가
		boolean isLvOneOrLvFive = false;
		//난이도 하 선택한 경우, 메인(하, 중으로 80%), 서브(중하, 중상 20%)
		if(quesLevel == 1) {
			startLv = 1;
			endLv = 2;
			isLvOneOrLvFive = true;
			subStartLv=3;
			subEndtLv=4;
			finalLv=5;
		}
		//난이도 중 선택한 경우, 메인(중하, 중 , 중상으로 80%), 서브(하, 상 20%)
		else if(quesLevel == 3) {
			startLv = 2;
			endLv = 4;
		}
		//난이도 상 선택한 경우, 메인(중상, 상으로 80%), 서브(중하, 중 20%)
		else {
			startLv = 4;
			endLv = 5;
			isLvOneOrLvFive = true;
			subStartLv=2;
			subEndtLv=3;
			finalLv=1;
		}
		
		int subLvConCnt = contentCnt*20/100;
		int mainLvConCnt = contentCnt-subLvConCnt;
		
		List<String> unitUniqNoAndTypeNoList = new ArrayList<>();
		String[] unitUniqNoAndTypeNoArr = unitUniqNoAndTypeNo.split("-");
		for(String unitUniqNoAndTypeNoVal : unitUniqNoAndTypeNoArr) {
			unitUniqNoAndTypeNoList.add(unitUniqNoAndTypeNoVal);
		}
		Collections.shuffle(unitUniqNoAndTypeNoList);
		
		//실제 DB에 존재하는 서브 문제 개수
		int subLvConRealCnt;
		if(isLvOneOrLvFive) {
			subLvConRealCnt = mathContentsRepository.countByQuesLevelAndUnitUniqNoTypeNoIn(subStartLv, subEndtLv, unitUniqNoAndTypeNoList);
		}else {
			subLvConRealCnt = mathContentsRepository.countByNotQuesLevelAndUnitUniqNoTypeNoIn(startLv, endLv, unitUniqNoAndTypeNoList);
		}
		
		//실제 DB에 존재하는 서브 문제 개수가 기대값 보다 낮은 경우 메인에서 추가
		if(subLvConRealCnt < subLvConCnt) {
			mainLvConCnt= contentCnt-subLvConRealCnt;
		}
		
		
		
		//유형별 존재하는 메인 문제 수
		List<String> mainContCntList = mathContentsRepository.countQuesLevelAndQuesLevelAndUnitUniqNoTypeNoIn(startLv, endLv, unitUniqNoAndTypeNoList);
		
		//각 유형별로 몇 문제씩 뽑아와야할지 기준 설정
		int[] typeGropSelCntStandardList = {1, 2, 3, 4, 5, 10, 15, 20, 30, 50, 100};
		int perN=1;	//각 유형별 n개씩 뽑아오는 기준
		Loop1 : 
		for(int n : typeGropSelCntStandardList) {
			int mainLvConRealCnt = 0;
			for(String contetnsCount : mainContCntList) {
				if(n<=Integer.parseInt(contetnsCount)) {
					mainLvConRealCnt +=n;
					if(mainLvConRealCnt>mainLvConCnt) {
						perN = n;
						break Loop1;
					}
				}else {
					mainLvConRealCnt += Integer.parseInt(contetnsCount);
				}
			}
			perN = n;
		}
		//mainLvConCnt 수 만큼 조회, 각 유형별 selectStandard개 씩
		StringBuffer queryString = new StringBuffer(); 
		queryString.append("select A.contents_no as contentsNo, A.unit_uniq_no as unitUniqNo, A.type_no as typeNo, A.contents, A.contents_img, A.img_path, A.solution, A.solution_img, A.solution_img_path,A.fir_no, A.sec_no, A.thr_no, A.four_no, A.fif_no,A.multi_choice_type, A.answer, A.choice_answer, A.ques_level,A.ans_exist_stts,A.contents_classify,B.subject, B.fir_unit, B.sec_unit, B.thr_unit, C.ques_type");
		queryString.append(" from (select *, ROW_NUMBER() OVER (PARTITION BY unit_uniq_no, type_no) as row_num from math_contents where contents_classify=0 and svc_posb_stts=1 and (ques_level between :startLv and :endLv) and");
		queryString.append(" CONCAT(unit_uniq_no, ',', type_no) in (:inList) order by Rand() ) as A,");
		queryString.append("math_unit_info as B, math_type_info as C");
		queryString.append(" where A.row_num<= :perN and A.unit_uniq_no = B.unit_uniq_no and (A.unit_uniq_no = C.unit_uniq_no and A.type_no = C.type_no) limit :contentsCnt ");
		Query query  = (Query) entityManager.createNativeQuery(queryString.toString())
						.setParameter("startLv", startLv)
						.setParameter("endLv", endLv)
						.setParameter("inList", unitUniqNoAndTypeNoList)
						.setParameter("perN", perN)
						.setParameter("contentsCnt", mainLvConCnt);
		JpaResultMapper result = new JpaResultMapper();
		List<MathContentsDtoForDocs> mainConListModel = result.list(query, MathContentsDtoForDocs.class);
		List<MathContentsDto> mainConList = new ArrayList<>();
		for(MathContentsDtoForDocs mainCon : mainConListModel) {
			MathTypeDomain typeDomain = new MathTypeDomain();
			typeDomain.setTypeNo(mainCon.getTypeNo().toString());
			typeDomain.setUnitUniqNo(mainCon.getUnitUniqNo().toString());
			MathTypeInfoDto typeInfoDto = new MathTypeInfoDto(typeDomain, mainCon.getQuesType(), 0);
			MathContentsDto mathContentsDto = modelMapper.map(mainCon, MathContentsDto.class);
			
			mathContentsDto.setMathTypeInfo(typeInfoDto.toEntity());
			mainConList.add(mathContentsDto);
		}
		mainLvConCnt = mainConList.size();
		subLvConCnt = contentCnt-mainLvConCnt;
		//유형별 존재하는 서브 문제 수
		List<String> subContCntList;
		if(isLvOneOrLvFive) {
			subContCntList = mathContentsRepository.countQuesLevelAndQuesLevelAndUnitUniqNoTypeNoIn(subStartLv, subEndtLv, unitUniqNoAndTypeNoList);
		}else {
			subContCntList = mathContentsRepository.countNotQuesLevelAndQuesLevelAndUnitUniqNoTypeNoIn(startLv, endLv, unitUniqNoAndTypeNoList);
		}
		
		//각 유형별로 몇 문제씩 뽑아와야할지 기준 설정
		Loop1 : 
		for(int n : typeGropSelCntStandardList) {
			subLvConRealCnt = 0;
			for(String contetnsCount : subContCntList) {
				if(n<=Integer.parseInt(contetnsCount)) {
					subLvConRealCnt +=n;
					if(subLvConRealCnt>subLvConCnt) {
						perN = n;
						break Loop1;
					}
				}else {
					subLvConRealCnt += Integer.parseInt(contetnsCount);
				}
			}
			perN = n;
		}
		
		//subLvConCnt 수 만큼 조회, 각 유형별 selectStandard개 씩
		
		List<MathContentsDto> subConList = new ArrayList<>();
		if(isLvOneOrLvFive) {
			query  = (Query) entityManager.createNativeQuery(queryString.toString())
					.setParameter("startLv", subStartLv)
					.setParameter("endLv", subEndtLv)
					.setParameter("inList", unitUniqNoAndTypeNoList)
					.setParameter("perN", perN)
					.setParameter("contentsCnt", subLvConCnt);
			List<MathContentsDtoForDocs> subConModelList = result.list(query, MathContentsDtoForDocs.class);
			for(MathContentsDtoForDocs subConModel : subConModelList) {
				MathTypeDomain typeDomain = new MathTypeDomain();
				typeDomain.setTypeNo(subConModel.getTypeNo().toString());
				typeDomain.setUnitUniqNo(subConModel.getUnitUniqNo().toString());
				MathTypeInfoDto typeInfoDto = new MathTypeInfoDto(typeDomain, subConModel.getQuesType(), 0);
				MathContentsDto mathContentsDto = modelMapper.map(subConModel, MathContentsDto.class);
				
				mathContentsDto.setMathTypeInfo(typeInfoDto.toEntity());
				subConList.add(mathContentsDto);
			}
		}else {
			String queryString1 = "select "+
					"A.contents_no, A.unit_uniq_no, A.type_no, A.contents, A.contents_img, A.img_path,"
					+ " A.solution, A.solution_img, A.solution_img_path,"+
					"A.fir_no, A.sec_no, A.thr_no, A.four_no, A.fif_no,"
					+ " A.multi_choice_type, A.answer, A.choice_answer, A.ques_level,"
					+ " A.ans_exist_stts,"+
					"A.contents_classify,"+
					"B.subject, B.fir_unit, B.sec_unit, B.thr_unit, C.ques_type" + 
				" from (" + 
					"select" + 
						" *, ROW_NUMBER() OVER (PARTITION BY unit_uniq_no, type_no) as row_num" + 
					" from" + 
						" math_contents" + 
					" where" + 
						" contents_classify=0 and svc_posb_stts=1 and not (ques_level between :startLv and :endLv)" + 
					" and" + 
						" CONCAT(unit_uniq_no, ',', type_no) in (:inList)" + 
					" order by Rand()) as A," + 
					"math_unit_info as B,"+
					"math_type_info as C"+
			" where A.row_num<= :perN and A.unit_uniq_no = B.unit_uniq_no and (A.unit_uniq_no = C.unit_uniq_no and A.type_no = C.type_no) limit :contentsCnt";
			query  = (Query) entityManager.createNativeQuery(queryString1)
					.setParameter("startLv", startLv)
					.setParameter("endLv", endLv)
					.setParameter("inList", unitUniqNoAndTypeNoList)
					.setParameter("perN", perN)
					.setParameter("contentsCnt", subLvConCnt);
			List<MathContentsDtoForDocs> subConModelList= result.list(query, MathContentsDtoForDocs.class);
			for(MathContentsDtoForDocs subConModel : subConModelList) {
				MathTypeDomain typeDomain = new MathTypeDomain();
				typeDomain.setTypeNo(subConModel.getTypeNo().toString());
				typeDomain.setUnitUniqNo(subConModel.getUnitUniqNo().toString());
				MathTypeInfoDto typeInfoDto = new MathTypeInfoDto(typeDomain, subConModel.getQuesType(), 0);
				MathContentsDto mathContentsDto = modelMapper.map(subConModel, MathContentsDto.class);
				
				mathContentsDto.setMathTypeInfo(typeInfoDto.toEntity());
				subConList.add(mathContentsDto);
			}
		}
		subLvConCnt = subConList.size();
		
		List<MathContentsDto> finalLvConList = new ArrayList<>();
		//레벨 하 또는 상 선택한 경우 서브레벨에서 문제 부족한 경우 finalLv에서 문제 추가
		if(isLvOneOrLvFive && contentCnt>mainLvConCnt+subLvConCnt) {
			int finalLvConCnt = contentCnt-(mainLvConCnt+subLvConCnt);
			query  = (Query) entityManager.createNativeQuery(queryString.toString())
					.setParameter("startLv", finalLv)
					.setParameter("endLv", finalLv)
					.setParameter("inList", unitUniqNoAndTypeNoList)
					.setParameter("perN", perN)
					.setParameter("contentsCnt", finalLvConCnt);
			List<MathContentsDtoForDocs> finalLvConModelList = result.list(query, MathContentsDtoForDocs.class);
			for(MathContentsDtoForDocs finalConModel : finalLvConModelList) {
				MathTypeDomain typeDomain = new MathTypeDomain();
				typeDomain.setTypeNo(finalConModel.getTypeNo().toString());
				typeDomain.setUnitUniqNo(finalConModel.getUnitUniqNo().toString());
				MathTypeInfoDto typeInfoDto = new MathTypeInfoDto(typeDomain, finalConModel.getQuesType(), 0);
				MathContentsDto mathContentsDto = modelMapper.map(finalConModel, MathContentsDto.class);
				
				mathContentsDto.setMathTypeInfo(typeInfoDto.toEntity());
				finalLvConList.add(mathContentsDto);
			}
			mainConList.addAll(finalLvConList);
		}
		//문제 모두 합치기
		mainConList.addAll(subConList);
		
		//문제 난이도에 따라 오름차순으로 정렬
		Collections.sort(mainConList);
		
		return mainConList;
	}
	
	
	public List<MathContentsDto> takeMathIpsiContents(String unitUniqNoAndTypeNo, String quesLevel, int contentCnt,
			int wrongRatioMin, int wrongRatioMax, int ipsiYearMin, int ipsiYearMax, String ipsiMonth){
		
		List<MathTypeDomain> mathTypeDomainList = new ArrayList<>();
		String[] unitUniqNoAndTypeNoArr = unitUniqNoAndTypeNo.split("-");
		for(String unitUniqNoAndTypeNoVal : unitUniqNoAndTypeNoArr) {
			String[] unitNoAndTypeNo = unitUniqNoAndTypeNoVal.split(",");
			MathTypeDomain mathTypeDomain = new MathTypeDomain();
			mathTypeDomain.setUnitUniqNo(unitNoAndTypeNo[0]);
			mathTypeDomain.setTypeNo(unitNoAndTypeNo[1]);
			mathTypeDomainList.add(mathTypeDomain);
		}
		
		
		List<Integer> quesLevelList = new ArrayList<>();
		String[] quesLevelArr = quesLevel.split(",");
		
		for(String quesLevelStr : quesLevelArr) {
			quesLevelList.add(Integer.parseInt(quesLevelStr));
		}
		
		List<Integer> ipsiMonthList = new ArrayList<>();
		String[] ipsiMonthArr = ipsiMonth.split(",");
		for(String ipsiMonthStr : ipsiMonthArr) {
			ipsiMonthList.add(Integer.parseInt(ipsiMonthStr));
		}
		
		Pageable limit = PageRequest.of(0,contentCnt);
		List<MathContents> list = mathContentsRepository.findByMathTypeInfoMathTypeDomainInAndQuesLevelInAndMathContentsIpsiWrongRatioBetweenAndMathContentsIpsiImpYearBetweenAndMathContentsIpsiImpMonthIn(
				mathTypeDomainList, quesLevelList, wrongRatioMin, wrongRatioMax, ipsiYearMin, ipsiYearMax, ipsiMonthList, limit);

		List<MathContentsDto> dtoList = new ArrayList<>();
		for(MathContents mathContents: list) {
			MathContentsDto mathContentsDto = modelMapper.map(mathContents, MathContentsDto.class);
			mathContentsDto.setImpYear(mathContents.getMathContentsIpsi().get(0).getImpYear());
			mathContentsDto.setImpMonth(mathContents.getMathContentsIpsi().get(0).getImpMonth());
			mathContentsDto.setOddQuesNum(mathContents.getMathContentsIpsi().get(0).getOddQuesNum());
			mathContentsDto.setWrongRatio(mathContents.getMathContentsIpsi().get(0).getWrongRatio());
			mathContentsDto.setPaperType(mathContents.getMathContentsIpsi().get(0).getPaperType());
			dtoList.add(mathContentsDto);
		}
		
		return dtoList;
	}
	
	
	public List<MathContentsDto> takeSimilarContents(int unitUniqNo, int typeNo, int contentsClassify){
		List<MathContents> similarConList = mathContentsRepository.findByUnitUniqNoAndAndTypeNoAndContentsClassifyAndSvcPosbSttsOrderBySysCreateDateDesc(unitUniqNo, typeNo, contentsClassify, 1);
		List<MathContentsDto> mathContentsDtoList = new ArrayList<>();
		for(MathContents mathContents: similarConList) {
			MathContentsDto mathContentsDto = modelMapper.map(mathContents, MathContentsDto.class);
			mathContentsDto.setOrgContentsNo(0);
			mathContentsDto.setOrgSrcPage(0);
			mathContentsDto.setOrgSrcRef(null);
			mathContentsDtoList.add(mathContentsDto);
		}
		return mathContentsDtoList;
	}
	
	public HashMap<String, Object> registerMathDocsPaper(MathDocsPaperDto mathDocsPaperDto) {
		Members members = StaticSecurityUtil.getMembers();
		UUID userUniqId = members.getUserUniqId();
		mathDocsPaperDto.setUserUniqId(userUniqId);
		MathDocsPaper mathDocsPaper = mathDocsPaperRepository.save(mathDocsPaperDto.toEntity());
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("isSuccess", true);
		map.put("docsNo", mathDocsPaper.getDocsNo());
		return map;
	}
	
	public HashMap<String, Object> registerMathDocsUsage(MathDocsUsageDto mathDocsUsageDto) {
		Members members = StaticSecurityUtil.getMembers();
		UUID userUniqId = members.getUserUniqId();
		mathDocsUsageDto.setUserUniqId(userUniqId);
		MathDocsUsage mathDocsPaper = mathDocsUsageRepository.save(mathDocsUsageDto.toEntity());
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("isSuccess", true);
		map.put("docsNo", mathDocsPaper.getDocsNo());
		return map;
	}
	
	public HashMap<String, Object> myMathDocs(int curPageNum, int pageVolume){
		HashMap<String, Object> map = new HashMap<String, Object>();
		Members members = StaticSecurityUtil.getMembers();
		UUID userUniqId = members.getUserUniqId();
		List<Integer> errSttsList = new ArrayList<>();
		errSttsList.add(2);
		errSttsList.add(3);
		Page<MathDocsPaper> myDocsList = mathDocsPaperRepository.findByUserUniqIdAndDocsErrSttsNotInOrderBySysCreateDateDesc(userUniqId, errSttsList,  PageRequest.of(curPageNum, pageVolume));
		List<MathDocsPaperDto> myDocsDtoList = new ArrayList<>();
		for(MathDocsPaper myDocs : myDocsList) {
			MathDocsPaperDto myDocsDto = modelMapper.map(myDocs, MathDocsPaperDto.class);
			myDocsDtoList.add(myDocsDto);
		}
		map.put("isSuccess", true);
		map.put("totalPageCnt", myDocsList.getTotalPages());
		map.put("myDocsList", myDocsDtoList);
		return map;
	}
	
	@Transactional
	public HashMap<String, Object> delMyMathDocs(int docsNo){
		HashMap<String, Object> map = new HashMap<String, Object>();
		Members members = StaticSecurityUtil.getMembers();
		MathDocsPaper mathDocsPaper = mathDocsPaperRepository.findByDocsNo(docsNo);
		if(mathDocsPaper.getUserUniqId().equals(members.getUserUniqId())) {
			int isSuccess = 0;
			if(mathDocsPaper.getDocsErrStts() == 1) {
				MathDocsPaperDto myDocsDto = modelMapper.map(mathDocsPaper, MathDocsPaperDto.class);
				myDocsDto.setDocsErrStts(2);
				MathDocsPaper newDocs = mathDocsPaperRepository.save(myDocsDto.toEntity());
				boolean isSaved = entityManager.contains(newDocs);
				if(isSaved) {
					isSuccess = 1;
				}
			}else {
				isSuccess = mathDocsPaperRepository.deleteByDocsNo(docsNo);
			}
			
			if(isSuccess == 1) {
				map.put("isSuccess", true);
			}else {
				map.put("isSuccess", false);
			}
		}else {
			map.put("existMsg", true);
			map.put("serverMsg", "본인이 만든 학습지가 아닌 경우 삭제 할 수 없습니다.");
		}
		
		
		return map;
	}
	
	
	public HashMap<String, Object> mathDocsByMyMathDocsPage(int docsNo) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		MathDocsPaper mathDocsPaper = mathDocsPaperRepository.findByDocsNo(docsNo);
		Members members = StaticSecurityUtil.getMembers();
		if(!mathDocsPaper.getUserUniqId().equals(members.getUserUniqId())) {
			map.put("existMsg", true);
			map.put("serverMsg", "본인이 만든 학습지가 아닌 경우 접근이 불가합니다.");
			return map;
		}
		MathDocsPaperDto mathDocsPaperDto = modelMapper.map(mathDocsPaper, MathDocsPaperDto.class);
		
		String contentsNoListStr = mathDocsPaper.getContentsNoList();
		String[] contentsNoArr = contentsNoListStr.split(",");
		List<Integer> contentsNoList = new ArrayList<>();
		HashMap<Integer, Integer> contentsOrderMap = new HashMap<>();		//in절 순서대로 정렬하기 위해 순서 값을 가진 맵
		int i=0;
		for(String contentsNo : contentsNoArr) {
			contentsNoList.add(Integer.parseInt(contentsNo));
			contentsOrderMap.put(Integer.parseInt(contentsNo), i);
			i++;
		}
		List<MathContents> mainConList = mathContentsRepository.findByContentsNoInAndSvcPosbSttsAndContentsClassifyNot(contentsNoList, 1, 3);
		
		List<MathContentsDto> mathContentsDtoList = new ArrayList<>();	//arrayList는 초기 크기 지정 안되 null 값으로 미리 지정
		for(int j=0; j<contentsOrderMap.size(); j++) {
			mathContentsDtoList.add(null);
		}
		
		for(MathContents mathContents: mainConList) {
			MathContentsDto mathContentsDto = modelMapper.map(mathContents, MathContentsDto.class);
			mathContentsDto.setOrgContentsNo(0);
			mathContentsDto.setOrgSrcPage(0);
			mathContentsDto.setOrgSrcRef(null);
			if(mathContents.getMathContentsIpsi().size() > 0) {
				mathContentsDto.setImpYear(mathContents.getMathContentsIpsi().get(0).getImpYear());
				mathContentsDto.setImpMonth(mathContents.getMathContentsIpsi().get(0).getImpMonth());
				mathContentsDto.setOddQuesNum(mathContents.getMathContentsIpsi().get(0).getOddQuesNum());
				mathContentsDto.setWrongRatio(mathContents.getMathContentsIpsi().get(0).getWrongRatio());
				mathContentsDto.setPaperType(mathContents.getMathContentsIpsi().get(0).getPaperType());
			}
			mathContentsDtoList.set(contentsOrderMap.get(mathContents.getContentsNo()), mathContentsDto);
		}
		
		//만약 문제가 비공개 됬거나 미출시, 삭제되어 조회가 안되는 경우, null로 리턴 될 수 있으니 null제거
		for(int k=mathContentsDtoList.size()-1; k>=0; k--) {
			if(mathContentsDtoList.get(k) == null) {
				mathContentsDtoList.remove(k);
			}
		}
		
		map.put("mathContentsList", mathContentsDtoList);
		map.put("mathDocsPaper", mathDocsPaperDto);
		return map;
	}
	
	public List<CustomTenFieldDto> mathDocsUsageStatistic(){
		List<String> roleNameList = new ArrayList<>();
		roleNameList.add("ADMIN");
		roleNameList.add("MANAGER");
		List<MembersRole> membersRoleList = membersRoleRepository.findByRoleNameIn(roleNameList);
		List<UUID> uuidList = new ArrayList<>();
		for(MembersRole membersRole : membersRoleList) {
			UUID uuid = membersRole.getUserUniqId();
			uuidList.add(uuid);
		}
		
		LocalDateTime ofDateTime = LocalDateTime.of(2022, 01, 01, 00, 00, 00);
		//전체 학습지 사용횟수
		int totalCnt = mathDocsUsageRepository.countBySysCreateDateAfterAndUserUniqIdNotIn(ofDateTime, uuidList);
		//최근 한달 사용횟수
		int monthAgoCnt = mathDocsUsageRepository.countBySysCreateDateAfterAndUserUniqIdNotIn(LocalDateTime.now().minusMonths(1).with(LocalTime.MIN), uuidList);
		//최근 일주일 사용횟수
		int weekAgoCnt = mathDocsUsageRepository.countBySysCreateDateAfterAndUserUniqIdNotIn(LocalDateTime.now().minusWeeks(1).with(LocalTime.MIN), uuidList);
		//어제 사용횟수
		int yesterDayCnt = mathDocsUsageRepository.countBySysCreateDateAfterAndUserUniqIdNotIn(LocalDateTime.now().minusDays(1).with(LocalTime.MIN), uuidList);
		//오늘 사용횟수
		int todayCnt = mathDocsUsageRepository.countBySysCreateDateAfterAndUserUniqIdNotIn(LocalDateTime.now().with(LocalTime.MIN), uuidList);
		
		yesterDayCnt= yesterDayCnt-todayCnt;
		
		CustomTenFieldDto customHeaderDto = new CustomTenFieldDto("전체", "최근 한달", "최근 일주일", "어제", "오늘", null, null, null,null, null);
		CustomTenFieldDto customBodyDto = new CustomTenFieldDto(totalCnt, monthAgoCnt, weekAgoCnt, yesterDayCnt, todayCnt, null, null, null,null, null);
		List<CustomTenFieldDto> list = new ArrayList<>();
		list.add(customHeaderDto);
		list.add(customBodyDto);
		
		return list;
	}
	
	public List<CustomTenFieldDto> mathDocsUsageStatisticByProfile(){
		List<CustomTenFieldDto> list = mathDocsUsageRepository.statisticMathDocsUsageByProfile();
		CustomTenFieldDto customHeaderDto = new CustomTenFieldDto("미등록", "원장", "강사", "교사","학부모","학생", "기타", null, null,null);
		list.add(0, customHeaderDto);
		return list;
	}
	
	public List<CustomTenFieldDto> mathDocsUsageStatisticByProfileAndDay(){
		List<CustomTenFieldDto> list = mathDocsUsageRepository.statisticMathDocsUsageByProfileDayOfWeek();
		CustomTenFieldDto customHeaderDto = new CustomTenFieldDto("프로필", "월요일","화요일", "수요일", "목요일", "금요일","토요일","일요일",null, null);
		list.add(0, customHeaderDto);
		return list;
	}
	
	public List<CustomTenFieldDto> mathDocsUsageStatisticByDayOfWeek(){
		List<CustomTenFieldDto> list = mathDocsUsageRepository.statisticMathDocsUsageByDayOfWeek();
		CustomTenFieldDto customHeaderDto = new CustomTenFieldDto("월요일","화요일", "수요일", "목요일", "금요일","토요일","일요일", null, null, null);
		list.add(0, customHeaderDto);
		return list;
	}
	
	public List<CustomTenFieldDto> countMathDocsUsageGroupBySysCreateDateMonth(){
		List<CustomTenFieldDto> list = mathDocsUsageRepository.countMathDocsUsageGroupBySysCreateDateMonth();
		CustomTenFieldDto customHeaderDto = new CustomTenFieldDto(list.get(0).getNbCol1(), list.get(1).getNbCol1(), 
				list.get(2).getNbCol1(), list.get(3).getNbCol1(), list.get(4).getNbCol1(), list.get(5).getNbCol1(), 
				list.size()>6 ? list.get(6).getNbCol1() : null, list.size()>7 ? list.get(7).getNbCol1() : null,
				list.size()>8 ? list.get(8).getNbCol1() : null, list.size()>9 ? list.get(9).getNbCol1() : null);
		CustomTenFieldDto customBodyDto = new CustomTenFieldDto(list.get(0).getNbCol2(), list.get(1).getNbCol2(), 
				list.get(2).getNbCol2(), list.get(3).getNbCol2(), list.get(4).getNbCol2(), list.get(5).getNbCol2(), 
				list.size()>6 ? list.get(6).getNbCol2() : null, list.size()>7 ? list.get(7).getNbCol2() : null,
				list.size()>8 ? list.get(8).getNbCol2() : null, list.size()>9 ? list.get(9).getNbCol2() : null);
		List<CustomTenFieldDto> list2 = new ArrayList<>();
		list2.add(0, customBodyDto);
		list2.add(0, customHeaderDto);
		return list2;
	}
	
}
