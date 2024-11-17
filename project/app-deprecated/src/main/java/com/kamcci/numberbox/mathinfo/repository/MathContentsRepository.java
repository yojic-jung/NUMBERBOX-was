package com.kamcci.numberbox.mathinfo.repository;

import com.kamcci.numberbox.mathinfo.domain.MathTypeDomain;
import com.kamcci.numberbox.mathinfo.dto.ContentsCnt;
import com.kamcci.numberbox.mathinfo.dto.ContentsListModel;
import com.kamcci.numberbox.mathinfo.entity.MathContents;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public interface MathContentsRepository extends JpaRepository<MathContents, Integer> {

    public List<MathContents> findByUserUniqId(UUID userUniqId);

    @Query(value = "select userUniqId from MathContents where contentsNo=:contentsNo", nativeQuery = false)
    public UUID findOnlyUuidByContentsNo(@Param("contentsNo") int contentsNo);

    @Transactional
    @Modifying // select 문이 아님을 나타낸다
    @Query(value = "UPDATE MathContents m set m.unitUniqNo =:toUnitUniqNo, m.typeNo=:toTypeNo where m.unitUniqNo =:fromUnitUniqNo and m.typeNo =:fromTypeNo", nativeQuery = false)
    public int contentsMoveFromTo(@Param("fromUnitUniqNo") int fromUnitUniqNo, @Param("fromTypeNo") int fromTypeNo,
                                  @Param("toUnitUniqNo") int toUnitUniqNo, @Param("toTypeNo") int toTypeNo);

    @EntityGraph(attributePaths = {"mathContentsComp", "mathTypeInfo"}) // n+1 문제 해결, 작업내역(라이선스 조회 안함)
    public Page<MathContents> findByUnitUniqNoAndContentsClassifyOrderBySysCreateDateDesc(int unitUniqNo,
                                                                                          int contentsClassify, Pageable page);

    @EntityGraph(attributePaths = {"mathContentsComp", "mathTypeInfo", "mathUnitInfo"}) // n+1 문제 해결, 작업내역(라이선스 조회 안함)
    public List<MathContents> findByUnitUniqNoAndAndTypeNoAndContentsClassifyAndSvcPosbSttsOrderBySysCreateDateDesc(
            int unitUniqNo, int typeNo, int contentsClassify, int svcPosbStts);

    @EntityGraph(attributePaths = {"mathContentsComp", "mathTypeInfo"}) // n+1 문제 해결, 작업내역(라이선스 조회 안함)
    public Page<MathContents> findByUnitUniqNoAndUserUniqIdAndContentsClassifyOrderBySysCreateDateDesc(int unitUniqNo,
                                                                                                       UUID userUniqId, int contentsClassify, Pageable page);

    @EntityGraph(attributePaths = {"mathContentsLicense", "mathTypeInfo", "mathUnitInfo"}) // n+1 문제 해결, 사용자 나의
    // 제작문제(유사문제 조회 안함)
    public Page<MathContents> findByUserUniqIdAndContentsClassifyNotInOrderBySysCreateDateDesc(UUID userUniqId,
                                                                                               List<Integer> contentsClassify, Pageable page);

    @EntityGraph(attributePaths = {"mathContentsLicense", "mathTypeInfo", "mathUnitInfo"}) // n+1 문제 해결, 사용자 나의
    // 제작문제(유사문제 조회 안함)
    public Page<MathContents> findByUserUniqIdAndContentsClassifyOrUserUniqIdAndContentsClassifyAndMathContentsLicenseShareSttsOrderBySysCreateDateDesc(
            UUID userUniqId, int contentsClassify, UUID userUniqId2, int contentsClassify2, int shareStts,
            Pageable page);

    @Query(value = "select DISTINCT new com.kamcci.mathinfo.dto.ContentsCnt"
            + "(a.unitUniqNo, a.typeNo, count(*) as cnt)" + " from MathContents a " + " where"
            + " a.unitUniqNo=:unitUniqNo " + "group by a.unitUniqNo, a.typeNo")
    public List<ContentsCnt> contentsCntByUnitUniqNo(@Param("unitUniqNo") int unitUniqNo);

    public long countByUnitUniqNoAndTypeNo(int unitUniqNo, int typeNo);

    @Query(value = "select DISTINCT new com.kamcci.mathinfo.dto.ContentsListModel"
            + "(a.contentsNo, a.unitUniqNo, a.typeNo, a.contents, a.contentsImg, a.solution, a.solutionImg, a.imgPath, a.solutionImgPath"
            + ", a.firNo, a.secNo, a.thrNo, a.fourNo, a.fifNo, a.multiChoiceType, a.answer"
            + ", a.choiceAnswer, a.quesLevel, a.ansExistStts, a.svcPosbStts, a.contentsClassify, a.orgContentsNo"
            + ", a.transConCnt, a.sysCreateDate, a.sysUpdateDate"
            + ", b.onlineLicStts, b.perLicStts, b.perLicPrice, b.entLicStts, b.entLicPrice, b.shareStts"
            + ", c.userNo, c.nickname, c.profileImgName, c.profileImgPath"
            + ", d.subject, d.firUnit, d.secUnit, d.thrUnit)"
            + " from MathContents a INNER JOIN MembersProfile c on a.userUniqId = c.userUniqId"
            + " INNER JOIN MathUnitInfo d on a.unitUniqNo = d.unitUniqNo"
            + " LEFT JOIN MathContentsLicense b on a.contentsNo = b.contentsNo" + " where" + " a.svcPosbStts=1 "
            + " and ( (a.contentsClassify=0) or (a.contentsClassify =1  and b.shareStts=1) ) and a.unitUniqNo in (:unitUniqNo)"
            + " order by a.quesLevel desc", nativeQuery = false)
    public Page<ContentsListModel> findByUnitUniqNoIn(@Param("unitUniqNo") List<Integer> unitUniqNo, Pageable pageable);

    @Query(value = "select DISTINCT new com.kamcci.mathinfo.dto.ContentsListModel"
            + "(a.contentsNo, a.unitUniqNo, a.typeNo, a.contents, a.contentsImg, a.solution, a.solutionImg, a.imgPath, a.solutionImgPath"
            + ", a.firNo, a.secNo, a.thrNo, a.fourNo, a.fifNo, a.multiChoiceType, a.answer"
            + ", a.choiceAnswer, a.quesLevel, a.ansExistStts, a.svcPosbStts, a.contentsClassify, a.orgContentsNo"
            + ", a.transConCnt, a.sysCreateDate, a.sysUpdateDate"
            + ", b.onlineLicStts, b.perLicStts, b.perLicPrice, b.entLicStts, b.entLicPrice, b.shareStts"
            + ", c.userNo, c.nickname, c.profileImgName, c.profileImgPath"
            + ", d.subject, d.firUnit, d.secUnit, d.thrUnit)"
            + " from MathContents a INNER JOIN MembersProfile c on a.userUniqId = c.userUniqId"
            + " INNER JOIN MathUnitInfo d on a.unitUniqNo = d.unitUniqNo"
            + " LEFT JOIN MathContentsLicense b on a.contentsNo = b.contentsNo" + " where" + " a.svcPosbStts=1 "
            + " and ( (a.contentsClassify=0) or (a.contentsClassify =1  and b.shareStts=1) ) and a.contentsNo=:contentsNo"
            + " order by a.sysCreateDate desc", nativeQuery = false)
    public List<ContentsListModel> findByContentsNoCustom(@Param("contentsNo") int contentsNo);

    @Query(value = "select DISTINCT new com.kamcci.mathinfo.dto.ContentsListModel"
            + "(a.contentsNo, a.unitUniqNo, a.typeNo, a.contents, a.contentsImg, a.solution, a.solutionImg, a.imgPath, a.solutionImgPath"
            + ", a.firNo, a.secNo, a.thrNo, a.fourNo, a.fifNo, a.multiChoiceType, a.answer"
            + ", a.choiceAnswer, a.quesLevel, a.ansExistStts, a.svcPosbStts, a.contentsClassify, a.orgContentsNo"
            + ", a.transConCnt, a.sysCreateDate, a.sysUpdateDate"
            + ", b.onlineLicStts, b.perLicStts, b.perLicPrice, b.entLicStts, b.entLicPrice, b.shareStts"
            + ", c.userNo, c.nickname, c.profileImgName, c.profileImgPath"
            + ", d.subject, d.firUnit, d.secUnit, d.thrUnit)"
            + " from MathContents a INNER JOIN MembersProfile c on a.userUniqId = c.userUniqId"
            + " INNER JOIN MathUnitInfo d on a.unitUniqNo = d.unitUniqNo"
            + " LEFT JOIN MathContentsLicense b on a.contentsNo = b.contentsNo" + " where" + " a.svcPosbStts=1 "
            + " and a.contentsClassify =1"
            + " and a.userUniqId not in (SELECT mr.userUniqId FROM MembersRole mr where mr.roleName='ADMIN' or mr.roleName='MANAGER')"
            + " order by a.sysCreateDate desc", nativeQuery = false)
    public List<ContentsListModel> findAllUserContentsCustom();

    @Query(value = "select DISTINCT new com.kamcci.mathinfo.dto.ContentsListModel"
            + "(a.contentsNo, a.unitUniqNo, a.typeNo, a.contents, a.contentsImg, a.solution, a.solutionImg, a.imgPath, a.solutionImgPath"
            + ", a.firNo, a.secNo, a.thrNo, a.fourNo, a.fifNo, a.multiChoiceType, a.answer"
            + ", a.choiceAnswer, a.quesLevel, a.ansExistStts, a.svcPosbStts, a.contentsClassify, a.orgContentsNo"
            + ", a.transConCnt, a.sysCreateDate, a.sysUpdateDate"
            + ", b.onlineLicStts, b.perLicStts, b.perLicPrice, b.entLicStts, b.entLicPrice, b.shareStts"
            + ", c.userNo, c.nickname, c.profileImgName, c.profileImgPath"
            + ", d.subject, d.firUnit, d.secUnit, d.thrUnit)"
            + " from MathContents a INNER JOIN MembersProfile c on a.userUniqId = c.userUniqId"
            + " INNER JOIN MathUnitInfo d on a.unitUniqNo = d.unitUniqNo"
            + " LEFT JOIN MathContentsLicense b on a.contentsNo = b.contentsNo" + " where" + " a.svcPosbStts=1 "
            + " and ( (a.contentsClassify=0) or (a.contentsClassify=2) or (a.contentsClassify =1  and b.shareStts=1) ) and a.contentsNo in(:contentsNoList)"
            + " order by a.sysCreateDate desc", nativeQuery = false)
    public List<ContentsListModel> findByContentsNoInCustom(@Param("contentsNoList") List<Integer> contentsNoList);

    @EntityGraph(attributePaths = {"mathUnitInfo", "mathTypeInfo", "mathContentsIpsi"}) // n+1 문제 해결
    public List<MathContents> findByContentsNoInAndSvcPosbSttsAndContentsClassifyNot(List<Integer> contentsNoList,
                                                                                     int svcPosbStts, int contentsClassify);

    @EntityGraph(attributePaths = {"mathContentsLicense", "mathTypeInfo", "membersProfile"}) // n+1 문제 해결, 사용자
    // 문제검색(유사문제 조회 안함)
    public List<MathContents> findByUnitUniqNoAndSvcPosbSttsAndContentsClassifyOrUnitUniqNoAndSvcPosbSttsAndContentsClassifyAndMathContentsLicenseShareSttsOrderBySysCreateDateDesc(
            int unitUniqNo, int svcPosbStts, int contentsClassify, int unitUniqNo2, int svcPosbStts2,
            int contentsClassify2, int shareStts);

    @EntityGraph(attributePaths = {"mathTypeInfo"})
    public MathContents findByContentsNo(int contentsNo);

    @Query(value = "select count(1) from MathContents where orgContentsNo=:orgContentsNo", nativeQuery = false)
    public int countByOrgContentsNo(@Param("orgContentsNo") int orgContentsNo);

    @Transactional
    @Modifying // select 문이 아님을 나타낸다
    @Query(value = "UPDATE MathContents m set m.transConCnt =:transConCnt where m.contentsNo =:contentsNo", nativeQuery = false)
    public int updateTransConCnt(@Param("contentsNo") int contentsNo, @Param("transConCnt") int transConCnt);

    @Transactional
    @Modifying // select 문이 아님을 나타낸다
    @Query(value = "UPDATE MathContents m set m.contentsClassify =:contentsClassify where m.contentsNo =:contentsNo", nativeQuery = false)
    public int updateContentsClassify(@Param("contentsNo") int contentsNo,
                                      @Param("contentsClassify") int contentsClassify);

    @Transactional
    @Modifying // select 문이 아님을 나타낸다
    @Query(value = "UPDATE MathContents m set m.imgPath =:imgpath, m.contentsImg =:contentsImg where m.contentsNo =:contentsNo", nativeQuery = false)
    public int changeConImg(@Param("contentsNo") int contentsNo, @Param("imgpath") String imgpath,
                            @Param("contentsImg") String contentsImg);

    @Transactional
    @Modifying // select 문이 아님을 나타낸다
    @Query(value = "UPDATE MathContents m set m.solutionImgPath =:solutionImgpath, m.solutionImg =:solutionImg where m.contentsNo =:contentsNo", nativeQuery = false)
    public int changeSolImg(@Param("contentsNo") int contentsNo, @Param("solutionImgpath") String solutionImgpath,
                            @Param("solutionImg") String solutionImg);

    @Transactional
    @Modifying // select 문이 아님을 나타낸다
    @Query(value = "UPDATE MathContents m set m.svcPosbStts =:svcPosbStts where m.contentsNo =:contentsNo", nativeQuery = false)
    public int changeSvcStts(@Param("contentsNo") int contentsNo, @Param("svcPosbStts") int svcPosbStts);

    public int deleteByContentsNo(int contentsNo);

    @Query(value = "select count(1) from MathContents where contentsClassify=0 and svcPosbStts=1 and (quesLevel BETWEEN :startLv and :endLv) and CONCAT(unitUniqNo, ',', typeNo) in (:unitUniqNoAndTypeNoList)", nativeQuery = false)
    public int countByQuesLevelAndUnitUniqNoTypeNoIn(@Param("startLv") int startLv, @Param("endLv") int endLv,
                                                     @Param("unitUniqNoAndTypeNoList") List<String> unitUniqNoAndTypeNoList);

    @Query(value = "select count(1) from MathContents where contentsClassify=0 and svcPosbStts=1 and not (quesLevel BETWEEN :startLv and :endLv) and CONCAT(unitUniqNo, ',', typeNo) in (:unitUniqNoAndTypeNoList)", nativeQuery = false)
    public int countByNotQuesLevelAndUnitUniqNoTypeNoIn(@Param("startLv") int startLv, @Param("endLv") int endLv,
                                                        @Param("unitUniqNoAndTypeNoList") List<String> unitUniqNoAndTypeNoList);

    @Query(value = "select count(contentsNo) from MathContents where contentsClassify=0 and svcPosbStts=1 and (quesLevel BETWEEN :startLv and :endLv) and CONCAT(unitUniqNo, ',', typeNo) in (:unitUniqNoAndTypeNoList) group by unitUniqNo, typeNo", nativeQuery = false)
    public List<String> countQuesLevelAndQuesLevelAndUnitUniqNoTypeNoIn(@Param("startLv") int startLv,
                                                                        @Param("endLv") int endLv, @Param("unitUniqNoAndTypeNoList") List<String> unitUniqNoAndTypeNoList);

    @Query(value = "select count(contentsNo) from MathContents where contentsClassify=0 and svcPosbStts=1 and not (quesLevel BETWEEN :startLv and :endLv) and CONCAT(unitUniqNo, ',', typeNo) in (:unitUniqNoAndTypeNoList) group by unitUniqNo, typeNo", nativeQuery = false)
    public List<String> countNotQuesLevelAndQuesLevelAndUnitUniqNoTypeNoIn(@Param("startLv") int startLv,
                                                                           @Param("endLv") int endLv, @Param("unitUniqNoAndTypeNoList") List<String> unitUniqNoAndTypeNoList);
// todo jpql 정상적이지 않음 수정 필요(java 17 migration 과정 중)
    // 사용자 문제 제작 수 통계
//    @Query(value = "select " + "new com.numberbox.common.util.CustomTenFieldDto( "
//            + "(CASE WHEN C.profileType=0 THEN '미등록'" + "WHEN C.profileType=1 THEN '원장'"
//            + "WHEN C.profileType=2 THEN '강사'" + "WHEN C.profileType=3 THEN '교사'" + "WHEN C.profileType=4 THEN '학무보'"
//            + "WHEN C.profileType=5 THEN '학생'" + "WHEN C.profileType=6 THEN '기타' END) as nbCol1, "
//            + "SUBSTRING(D.birth, 1, 2) as nbCol2, count(A.contentsNo) as nbCol3,"
//            + "0 as nbCol4, 0 as nbCol5, 0 as nbCol6, 0 as nbCol7, 0 as nbCol8, 0 as nbCol9, 0 as nbCol10)"
//            + " from MathContents as A, Members as B , MembersProfile as C, MembersPrivate as D"
//            + " where A.userUniqId=B.userUniqId and A.userUniqId=B.userUniqId  and A.userUniqId=C.userUniqId  and A.userUniqId=D.userUniqId "
//            + " and A.userUniqId not in (SELECT mr.userUniqId FROM MembersRole mr where mr.roleName='ADMIN' or mr.roleName='MANAGER')"
//            + " group by B.email", nativeQuery = false)
//    public List<CustomTenFieldDto> mathContentsStatistic();

    @EntityGraph(attributePaths = {"mathContentsIpsi", "mathUnitInfo", "mathTypeInfo"}) // n+1 문제 해결
    public Page<MathContents> findByMathContentsIpsiImpYear(int impYear, Pageable page);

    @EntityGraph(attributePaths = {"mathContentsIpsi", "mathUnitInfo", "mathTypeInfo"}) // n+1 문제 해결
    public Page<MathContents> findByMathContentsIpsiImpYearAndMathContentsIpsiImpMonth(int impYear, int impMonth,
                                                                                       Pageable page);

    @EntityGraph(attributePaths = {"mathContentsIpsi", "mathUnitInfo", "mathTypeInfo"}) // n+1 문제 해결
    public List<MathContents> findByMathTypeInfoMathTypeDomainInAndQuesLevelInAndMathContentsIpsiWrongRatioBetweenAndMathContentsIpsiImpYearBetweenAndMathContentsIpsiImpMonthIn(
            List<MathTypeDomain> mathTypeDomain, List<Integer> quesLevel, int startWrongRatio, int endWrongRatio,
            int startImpYear, int endImpYear, List<Integer> impMonth, Pageable pageable);
// todo jpql 정상적이지 않음 수정 필요(java 17 migration 과정 중)
//    @Query(value = "SELECT new com.numberbox.common.util.CustomTenFieldDto("
//            + " CONCAT(YEAR(mc.sysCreateDate), '년 ', MONTH(mc.sysCreateDate), '월') as nbCol1, count(*) as nbCol2,"
//            + " 0 as nbCol3, 0 as nbCol4, 0 as nbCol5, 0 as nbCol6, 0 as nbCol7, 0 as nbCol8, 0 as nbCol9, 0 as nbCol10) "
//            + " FROM MathContents as mc"
//            + " where (mc.contentsClassify=1 or mc.contentsClassify=2) and mc.sysCreateDate >= '2022-11-01'"
//            + " and mc.userUniqId not in (SELECT mr.userUniqId FROM MembersRole mr where mr.roleName='ADMIN' or mr.roleName='MANAGER') "
//            + " GROUP BY YEAR(mc.sysCreateDate), MONTH(mc.sysCreateDate) ORDER BY mc.sysCreateDate ASC", nativeQuery = false)
//    public List<CustomTenFieldDto> statisticMathContentsUsageGroupBySysCreateDateMonth();
//
//    @Query(value = "SELECT new com.numberbox.common.util.CustomTenFieldDto("
//            + " COUNT(CASE WHEN B.profileType=0 THEN 1 END) as nbCol1,"
//            + " COUNT(CASE WHEN B.profileType=1 THEN 1 END) as nbCol2,"
//            + " COUNT(CASE WHEN B.profileType=2 THEN 1 END) as nbCol3,"
//            + " COUNT(CASE WHEN B.profileType=3 THEN 1 END) as nbCol4,"
//            + " COUNT(CASE WHEN B.profileType=4 THEN 1 END) as nbCol5,"
//            + " COUNT(CASE WHEN B.profileType=5 THEN 1 END) as nbCol6,"
//            + " COUNT(CASE WHEN B.profileType=6 THEN 1 END) as nbCol7, 0 as nbCol8, 0 as nbCol9, 0 as nbCol10)"
//            + " FROM" + " MathContents as A," + " MembersProfile as B   " + " WHERE A.userUniqId=B.userUniqId "
//            + " and A.userUniqId not in (SELECT mr.userUniqId FROM MembersRole mr where mr.roleName='ADMIN' or mr.roleName='MANAGER')"
//            + " and (A.contentsClassify=1 or A.contentsClassify=2)", nativeQuery = false)
//    public List<CustomTenFieldDto> statisticContentsUsageByProfile();

//	@Query(value = "SELECT " + " new com.numberbox.common.util.CustomTenFieldDto("
//			+ " COUNT(CASE WHEN WEEKDAY(sysCreateDate)=0 THEN '월요일' END) as nbCol1, "
//			+ " COUNT(CASE WHEN WEEKDAY(sysCreateDate)=1 THEN '화요일' END) as nbCol2, "
//			+ " COUNT(CASE WHEN WEEKDAY(sysCreateDate)=2 THEN '수요일' END) as nbCol3, "
//			+ " COUNT(CASE WHEN WEEKDAY(sysCreateDate)=3 THEN '목요일' END) as nbCol4, "
//			+ " COUNT(CASE WHEN WEEKDAY(sysCreateDate)=4 THEN '금요일' END) as nbCol5, "
//			+ " COUNT(CASE WHEN WEEKDAY(sysCreateDate)=5 THEN '토요일' END) as nbCol6, "
//			+ " COUNT(CASE WHEN WEEKDAY(sysCreateDate)=6 THEN '일요일' END) as nbCol7, "
//			+ " 0 as nbCol8, 0 as nbCol9, 0 as nbCol10) " + " FROM MathContents "
//			+ " where userUniqId not in (SELECT mr.userUniqId FROM MembersRole mr where mr.roleName='ADMIN' or mr.roleName='MANAGER')"
//			+ " and (contentsClassify=1 or contentsClassify=2)", nativeQuery = false)
//	public List<CustomTenFieldDto> statisticMathContentsUsageByDayOfWeek();
//
//	// 공개/비공개 문제수
//	@Query(value = " SELECT " + " new com.numberbox.common.util.CustomTenFieldDto(" + " CASE"
//			+ "	WHEN a.contentsClassify=0 and a.svcPosbStts=0 THEN '미출시(자체)'"
//			+ "	WHEN a.contentsClassify=0 and a.svcPosbStts=2 THEN '검수완료(자체)'"
//			+ "	WHEN a.contentsClassify=0 and a.svcPosbStts=3 THEN '오류(자체)'"
//			+ "	WHEN a.contentsClassify=0 and a.svcPosbStts=1 THEN '출시(자체)'"
//			+ "   WHEN a.contentsClassify=1 and b.shareStts=1 THEN '사용자 공개'"
//			+ "   WHEN a.contentsClassify=1 and b.shareStts=0 THEN '사용자 비공개'" + "   WHEN a.contentsClassify=2 THEN '변형'"
//			+ "   WHEN a.contentsClassify=4 THEN '수능 및 모의고사'" + "   ELSE '기타'" + " END as nbCol1"
//			+ ", COUNT(*) as nbCo2, 0 as nbCol3, 0 as nbCol4, 0 as nbCol5, 0 as nbCol6, 0 as nbCol7, 0 as nbCol8, 0 as nbCol9, 0 as nbCol10)"
//			+ " FROM MathContents a LEFT OUTER JOIN MathContentsLicense b on a.contentsNo=b.contentsNo"
//			+ " where (a.userUniqId, a.contentsClassify) not in (SELECT mr.userUniqId, 1 FROM MembersRole mr where mr.roleName='ADMIN' or mr.roleName='MANAGER')"
//			+ " group by a.contentsClassify, b.shareStts, a.svcPosbStts"
//			+ " order by a.contentsClassify", nativeQuery = false)
//	public List<CustomTenFieldDto> statisticMathContentsUsageByClassifySvcPosbSttsShareStts();

}
