package com.numberbox.mathinfo.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.numberbox.mathinfo.dto.MathContentsDto;
import com.numberbox.mathinfo.entity.MathContents;
import com.numberbox.mathinfo.repository.MathContentsRepository;

@Service
public class MathDocsSevice {

	@PersistenceContext
    EntityManager entityManager;

	@Autowired
	MathContentsRepository mathContentsRepository;
	
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
			mathContentsDto.setUserUniqId(null);
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
			mathContentsDto.setUserUniqId(null);
			mathContentsDto.setOrgContentsNo(0);
			mathContentsDto.setOrgSrcPage(0);
			mathContentsDto.setOrgSrcRef(null);
			mathContentsDtoList.add(mathContentsDto);
		}
		return mathContentsDtoList;
	}
	
}
