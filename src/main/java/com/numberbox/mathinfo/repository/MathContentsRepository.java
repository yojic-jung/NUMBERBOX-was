package com.numberbox.mathinfo.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.numberbox.mathinfo.dto.ContentsListModel;
import com.numberbox.mathinfo.entity.MathContents;

public interface MathContentsRepository extends JpaRepository <MathContents, Integer> {
	
	@Query(value = "select userUniqId from MathContents where contentsNo=:contentsNo", nativeQuery = false)
	public UUID findOnlyUuidByContentsNo(@Param("contentsNo")int contentsNo);
	
	@EntityGraph(attributePaths = {"mathContentsComp", "mathTypeInfo"})		//n+1 문제 해결, 작업내역(라이선스 조회 안함)
	public List<MathContents> findByUnitUniqNoAndContentsClassifyOrderBySysCreateDateDesc(int unitUniqNo, int contentsClassify);
	
	@EntityGraph(attributePaths = {"mathContentsComp", "mathTypeInfo"})		//n+1 문제 해결, 작업내역(라이선스 조회 안함)
	public List<MathContents> findByUnitUniqNoAndAndTypeNoAndContentsClassifyAndSvcPosbSttsOrderBySysCreateDateDesc(int unitUniqNo, int typeNo, int contentsClassify, int svcPosbStts);
	
	@EntityGraph(attributePaths = {"mathContentsComp", "mathTypeInfo"})		//n+1 문제 해결, 작업내역(라이선스 조회 안함)
	public List<MathContents> findByUnitUniqNoAndUserUniqIdAndContentsClassifyOrderBySysCreateDateDesc(int unitUniqNo, UUID userUniqId, int contentsClassify);
	
	@EntityGraph(attributePaths = {"mathContentsLicense", "mathTypeInfo"})		//n+1 문제 해결, 사용자 나의 제작문제(유사문제 조회 안함)
	public List<MathContents> findByUserUniqIdAndContentsClassifyNotOrderBySysCreateDateDesc(UUID userUniqId, int contentsClassify);
	
	@EntityGraph(attributePaths = {"mathContentsLicense", "mathTypeInfo"})		//n+1 문제 해결, 사용자 나의 제작문제(유사문제 조회 안함)
	public List<MathContents> findByUserUniqIdAndContentsClassifyOrUserUniqIdAndContentsClassifyAndMathContentsLicenseShareSttsOrderBySysCreateDateDesc(
			UUID userUniqId, int contentsClassify, UUID userUniqId2, int contentsClassify2, int shareStts);
	
	
    @Query(value = "select DISTINCT new com.numberbox.mathinfo.dto.ContentsListModel"
    		+ "(a.contentsNo, a.unitUniqNo, a.typeNo, a.contents, a.contentsImg, a.solution, a.solutionImg, a.imgPath, a.solutionImgPath"+
    		", a.firNo, a.secNo, a.thrNo, a.fourNo, a.fifNo, a.multiChoiceType, a.answer"+
    		", a.choiceAnswer, a.quesLevel, a.ansExistStts, a.svcPosbStts, a.contentsClassify, a.orgContentsNo"+
    		", a.transConCnt, a.sysCreateDate, a.sysUpdateDate"+
    		", b.onlineLicStts, b.perLicStts, b.perLicPrice, b.entLicStts, b.entLicPrice, b.shareStts"+
    		", c.userNo, c.nickname, c.profileImgName, c.profileImgPath"+
    		", d.subject, d.firUnit, d.secUnit, d.thrUnit)"+
    		" from MathContents a INNER JOIN MembersProfile c on a.userUniqId = c.userUniqId" +
    		" INNER JOIN MathUnitInfo d on a.unitUniqNo = d.unitUniqNo" + 
    		" LEFT JOIN MathContentsLicense b on a.contentsNo = b.contentsNo" + 
    		" where" + 
    		" a.svcPosbStts=1 " + 
    		" and ( (a.contentsClassify=0) or (a.contentsClassify =1  and b.shareStts=1) ) and a.unitUniqNo=:unitUniqNo" + 
    		" order by a.sysCreateDate desc", nativeQuery = false)
	public List<ContentsListModel> findByUnitUniqNo(@Param("unitUniqNo")int unitUniqNo);
    
    @Query(value = "select DISTINCT new com.numberbox.mathinfo.dto.ContentsListModel"+
    		"(a.contentsNo, a.unitUniqNo, a.typeNo, a.contents, a.contentsImg, a.solution, a.solutionImg, a.imgPath, a.solutionImgPath"+
    		", a.firNo, a.secNo, a.thrNo, a.fourNo, a.fifNo, a.multiChoiceType, a.answer"+
    		", a.choiceAnswer, a.quesLevel, a.ansExistStts, a.svcPosbStts, a.contentsClassify, a.orgContentsNo"+
    		", a.transConCnt, a.sysCreateDate, a.sysUpdateDate"+
    		", b.onlineLicStts, b.perLicStts, b.perLicPrice, b.entLicStts, b.entLicPrice, b.shareStts"+
    		", c.userNo, c.nickname, c.profileImgName, c.profileImgPath"+
    		", d.subject, d.firUnit, d.secUnit, d.thrUnit)"+
    		" from MathContents a INNER JOIN MembersProfile c on a.userUniqId = c.userUniqId" +
    		" INNER JOIN MathUnitInfo d on a.unitUniqNo = d.unitUniqNo" + 
    		" LEFT JOIN MathContentsLicense b on a.contentsNo = b.contentsNo" + 
    		" where" + 
    		" a.svcPosbStts=1 " + 
    		" and ( (a.contentsClassify=0) or (a.contentsClassify=2) or (a.contentsClassify =1  and b.shareStts=1) ) and a.contentsNo in(:contentsNoList)" + 
    		" order by a.sysCreateDate desc", nativeQuery = false)
	public List<ContentsListModel> findByContentsNoIn(@Param("contentsNoList")List<Integer> contentsNoList);
    
	@EntityGraph(attributePaths = {"mathContentsLicense", "mathTypeInfo", "membersProfile"})		//n+1 문제 해결, 사용자 문제검색(유사문제 조회 안함)
    public List<MathContents> findByUnitUniqNoAndSvcPosbSttsAndContentsClassifyOrUnitUniqNoAndSvcPosbSttsAndContentsClassifyAndMathContentsLicenseShareSttsOrderBySysCreateDateDesc(
    		int unitUniqNo, int svcPosbStts, int contentsClassify, int unitUniqNo2, int svcPosbStts2, int contentsClassify2, int shareStts);
    
	@EntityGraph(attributePaths = {"mathTypeInfo"})
	public MathContents findByContentsNo(int contentsNo);
	
	
	@Query(value = "select count(1) from MathContents where orgContentsNo=:orgContentsNo", nativeQuery = false)
	public int countByOrgContentsNo(@Param("orgContentsNo")int orgContentsNo);
	
	@Transactional
	@Modifying // select 문이 아님을 나타낸다
	@Query(value = "UPDATE MathContents m set m.transConCnt =:transConCnt where m.contentsNo =:contentsNo", nativeQuery = false)
	public int updateTransConCnt(@Param("contentsNo")int contentsNo, @Param("transConCnt")int transConCnt);
	
	
	@Transactional
	@Modifying // select 문이 아님을 나타낸다
	@Query(value = "UPDATE MathContents m set m.imgPath =:imgpath, m.contentsImg =:contentsImg where m.contentsNo =:contentsNo", nativeQuery = false)
	public int changeConImg(@Param("contentsNo")int contentsNo, @Param("imgpath")String imgpath, @Param("contentsImg")String contentsImg);
	
	@Transactional
	@Modifying // select 문이 아님을 나타낸다
	@Query(value = "UPDATE MathContents m set m.solutionImgPath =:solutionImgpath, m.solutionImg =:solutionImg where m.contentsNo =:contentsNo", nativeQuery = false)
	public int changeSolImg(@Param("contentsNo")int contentsNo, @Param("solutionImgpath")String solutionImgpath, @Param("solutionImg")String solutionImg);

	@Transactional
	@Modifying // select 문이 아님을 나타낸다
	@Query(value = "UPDATE MathContents m set m.svcPosbStts =:svcPosbStts where m.contentsNo =:contentsNo", nativeQuery = false)
	public int changeSvcStts(@Param("contentsNo")int contentsNo, @Param("svcPosbStts")int svcPosbStts);

	public int deleteByContentsNo(int contentsNo);
	
	@Query(value = "select count(1) from MathContents where contentsClassify=0 and svcPosbStts=1 and (quesLevel BETWEEN :startLv and :endLv) and CONCAT(unitUniqNo, ',', typeNo) in (:unitUniqNoAndTypeNoList)", nativeQuery = false)
	public int countByQuesLevelAndUnitUniqNoTypeNoIn(@Param("startLv")int startLv, @Param("endLv")int endLv, @Param("unitUniqNoAndTypeNoList") List<String> unitUniqNoAndTypeNoList);

	@Query(value = "select count(1) from MathContents where contentsClassify=0 and svcPosbStts=1 and not (quesLevel BETWEEN :startLv and :endLv) and CONCAT(unitUniqNo, ',', typeNo) in (:unitUniqNoAndTypeNoList)", nativeQuery = false)
	public int countByNotQuesLevelAndUnitUniqNoTypeNoIn(@Param("startLv")int startLv, @Param("endLv")int endLv, @Param("unitUniqNoAndTypeNoList") List<String> unitUniqNoAndTypeNoList);

	
	@Query(value = "select count(contentsNo) from MathContents where contentsClassify=0 and svcPosbStts=1 and (quesLevel BETWEEN :startLv and :endLv) and CONCAT(unitUniqNo, ',', typeNo) in (:unitUniqNoAndTypeNoList) group by unitUniqNo, typeNo", nativeQuery = false)
	public List<String> countQuesLevelAndQuesLevelAndUnitUniqNoTypeNoIn(@Param("startLv")int startLv, @Param("endLv")int endLv, @Param("unitUniqNoAndTypeNoList") List<String> unitUniqNoAndTypeNoList);
	
	@Query(value = "select count(contentsNo) from MathContents where contentsClassify=0 and svcPosbStts=1 and not (quesLevel BETWEEN :startLv and :endLv) and CONCAT(unitUniqNo, ',', typeNo) in (:unitUniqNoAndTypeNoList) group by unitUniqNo, typeNo", nativeQuery = false)
	public List<String> countNotQuesLevelAndQuesLevelAndUnitUniqNoTypeNoIn(@Param("startLv")int startLv, @Param("endLv")int endLv, @Param("unitUniqNoAndTypeNoList") List<String> unitUniqNoAndTypeNoList);
	
	@Query(value = 
			"select *" + 
			" from (" + 
				"select" + 
					" *, ROW_NUMBER() OVER (PARTITION BY unit_uniq_no, type_no) as row_num" + 
				" from" + 
					" math_contents" + 
				" where" + 
					" contents_classify=0 and svc_posb_stts=1 and (ques_level between :startLv and :endLv)" + 
				" and" + 
					" CONCAT(unit_uniq_no, ',', type_no) in (:unitUniqNoAndTypeNoList) order by Rand()" + 
				") as A" + 
			" where A.row_num<=:n limit :conCnt", nativeQuery = true)
	public List<MathContents> findByQuesLevelAndUnitUniqNoTypeNoIn(@Param("startLv")int startLv, @Param("endLv")int endLv, @Param("unitUniqNoAndTypeNoList") List<String> unitUniqNoAndTypeNoList, @Param("n")int n, @Param("conCnt")int conCnt);

	@Query(value = 
			"select *" + 
			" from (" + 
				"select" + 
					" *, ROW_NUMBER() OVER (PARTITION BY unit_uniq_no, type_no) as row_num" + 
				" from" + 
					" math_contents" + 
				" where" + 
					" contents_classify=0 and svc_posb_stts=1 and not (ques_level between :startLv and :endLv)" + 
				" and" + 
					" CONCAT(unit_uniq_no, ',', type_no) in (:unitUniqNoAndTypeNoList)" + 
				" order by Rand()) as A" + 
			" where A.row_num<=:n limit :conCnt", nativeQuery = true)
	public List<MathContents> findByNotQuesLevelAndUnitUniqNoTypeNoIn(@Param("startLv")int startLv, @Param("endLv")int endLv, @Param("unitUniqNoAndTypeNoList") List<String> unitUniqNoAndTypeNoList, @Param("n")int n, @Param("conCnt")int conCnt);


}
