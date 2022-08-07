package com.numberbox.mathdocs.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
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
import com.numberbox.mathinfo.dto.MathContentsDto;
import com.numberbox.mathinfo.entity.MathContents;
import com.numberbox.mathinfo.repository.MathContentsRepository;
import com.numberbox.members.entity.Members;
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
		int typeGropSelCntStandard=1;	//각 유형별 n개씩 뽑아오는 기준
		Loop1 : 
		for(int n : typeGropSelCntStandardList) {
			int mainLvConRealCnt = 0;
			for(String contetnsCount : mainContCntList) {
				if(n<=Integer.parseInt(contetnsCount)) {
					mainLvConRealCnt +=n;
					if(mainLvConRealCnt>mainLvConCnt) {
						typeGropSelCntStandard = n;
						break Loop1;
					}
				}else {
					mainLvConRealCnt += Integer.parseInt(contetnsCount);
				}
			}
			typeGropSelCntStandard = n;
		}
		//mainLvConCnt 수 만큼 조회, 각 유형별 selectStandard개 씩
		List<MathContents> mainConList = mathContentsRepository.findByQuesLevelAndUnitUniqNoTypeNoIn(startLv, endLv, unitUniqNoAndTypeNoList, typeGropSelCntStandard, mainLvConCnt);
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
						typeGropSelCntStandard = n;
						break Loop1;
					}
				}else {
					subLvConRealCnt += Integer.parseInt(contetnsCount);
				}
			}
			typeGropSelCntStandard = n;
		}
		
		//subLvConCnt 수 만큼 조회, 각 유형별 selectStandard개 씩
		List<MathContents> subConList;
		if(isLvOneOrLvFive) {
			subConList = mathContentsRepository.findByQuesLevelAndUnitUniqNoTypeNoIn(subStartLv, subEndtLv, unitUniqNoAndTypeNoList, typeGropSelCntStandard, subLvConCnt);
		}else {
			subConList = mathContentsRepository.findByNotQuesLevelAndUnitUniqNoTypeNoIn(startLv, endLv, unitUniqNoAndTypeNoList, typeGropSelCntStandard, subLvConCnt);
		}
		subLvConCnt = subConList.size();
		
		List<MathContents> finalLvConList;
		//레벨 하 또는 상 선택한 경우 서브레벨에서 문제 부족한 경우 finalLv에서 문제 추가
		if(isLvOneOrLvFive && contentCnt>mainLvConCnt+subLvConCnt) {
			int finalLvConCnt = contentCnt-(mainLvConCnt+subLvConCnt);
			finalLvConList = mathContentsRepository.findByQuesLevelAndUnitUniqNoTypeNoIn(finalLv, finalLv, unitUniqNoAndTypeNoList, typeGropSelCntStandard, finalLvConCnt);
			mainConList.addAll(finalLvConList);
		}
		//문제 모두 합치기
		mainConList.addAll(subConList);
		
		List<MathContentsDto> mathContentsDtoList = new ArrayList<>();
		for(MathContents mathContents: mainConList) {
			MathContentsDto mathContentsDto = modelMapper.map(mathContents, MathContentsDto.class);
			mathContentsDto.setOrgContentsNo(0);
			mathContentsDto.setOrgSrcPage(0);
			mathContentsDto.setOrgSrcRef(null);
			mathContentsDtoList.add(mathContentsDto);
		}
		
		//문제 난이도에 따라 오름차순으로 정렬
		Collections.sort(mathContentsDtoList);
		
		return mathContentsDtoList;
	}
	
	
	public List<MathContentsDto> takeSimilarContents(int unitUniqNo, int typeNo){
		List<MathContents> similarConList = mathContentsRepository.findByUnitUniqNoAndAndTypeNoAndContentsClassifyAndSvcPosbSttsOrderBySysCreateDateDesc(unitUniqNo, typeNo, 0, 1);
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
	
	public HashMap<String, Object> myMathDocs(){
		HashMap<String, Object> map = new HashMap<String, Object>();
		Members members = StaticSecurityUtil.getMembers();
		UUID userUniqId = members.getUserUniqId();
		List<MathDocsPaper> myDocsList = mathDocsPaperRepository.findByUserUniqIdAndDocsErrSttsNotOrderBySysCreateDateDesc(userUniqId, 2);
		List<MathDocsPaperDto> myDocsDtoList = new ArrayList<>();
		for(MathDocsPaper myDocs : myDocsList) {
			MathDocsPaperDto myDocsDto = modelMapper.map(myDocs, MathDocsPaperDto.class);
			myDocsDtoList.add(myDocsDto);
		}
		map.put("isSuccess", true);
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
		MathDocsPaperDto mathDocsPaperDto = modelMapper.map(mathDocsPaper, MathDocsPaperDto.class);
		
		String contentsNoListStr = mathDocsPaper.getContentsNoList();
		String[] contentsNoArr = contentsNoListStr.split(",");
		List<Integer> contentsNoList = new ArrayList<>();
		for(String contentsNo : contentsNoArr) {
			contentsNoList.add(Integer.parseInt(contentsNo));
		}
		List<MathContents> mainConList = mathContentsRepository.findByContentsNoIn(contentsNoList);
		
		List<MathContentsDto> mathContentsDtoList = new ArrayList<>();
		for(MathContents mathContents: mainConList) {
			MathContentsDto mathContentsDto = modelMapper.map(mathContents, MathContentsDto.class);
			mathContentsDto.setOrgContentsNo(0);
			mathContentsDto.setOrgSrcPage(0);
			mathContentsDto.setOrgSrcRef(null);
			mathContentsDtoList.add(mathContentsDto);
		}
		
		//문제 난이도에 따라 오름차순으로 정렬
		Collections.sort(mathContentsDtoList);
		map.put("mathContentsList", mathContentsDtoList);
		map.put("mathDocsPaper", mathDocsPaperDto);
		return map;
	}
}
