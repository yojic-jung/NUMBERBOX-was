package com.numberbox.mathinfo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.numberbox.mathinfo.dto.MathUnitInfoGroup;
import com.numberbox.mathinfo.entity.MathUnitInfo;

public interface MathUnitRepository extends JpaRepository <MathUnitInfo, Integer> {
	
	@Query(value = "SELECT new com.numberbox.mathinfo.dto.MathUnitInfoGroup(min(rp.unitUniqNo), rp.subject as mainVal) FROM MathUnitInfo rp GROUP BY rp.subject order by rp.unitUniqNo asc",nativeQuery = false)
	public List<MathUnitInfoGroup> selectSubjectInfo();

	@Query(value = "SELECT "
			+ "new com.numberbox.mathinfo.dto.MathUnitInfoGroup(min(rp.unitUniqNo), rp.subject as mainVal) "
			+ "FROM"
			+ " MathUnitInfo rp "
			+ "WHERE "
			+ "rp.unitUniqNo in (select distinct mc.unitUniqNo from MathContents mc "
			+ "LEFT JOIN MathContentsLicense mcLic on mc.contentsNo = mcLic.contentsNo "
			+ "where (mc.svcPosbStts=1 and mc.contentsClassify=0) or (mc.svcPosbStts=1 and mc.contentsClassify=1 and mcLic.shareStts=1)) "
			+ "GROUP BY rp.subject order by rp.unitUniqNo asc",nativeQuery = false)
	public List<MathUnitInfoGroup> selectSubjectInfoOnlyExistContents();
	
	@Query(value = "SELECT new com.numberbox.mathinfo.dto.MathUnitInfoGroup(min(rp.unitUniqNo), rp.subject as parentVal, rp.firUnit as mainVal) FROM MathUnitInfo rp GROUP BY rp.subject, rp.firUnit order by rp.unitUniqNo asc",nativeQuery = false)
	public List<MathUnitInfoGroup> selectFirUnitInfo();
	
	@Query(value = "SELECT "
			+ "new com.numberbox.mathinfo.dto.MathUnitInfoGroup(min(rp.unitUniqNo), rp.subject as parentVal, rp.firUnit as mainVal) "
			+ "FROM"
			+ " MathUnitInfo rp "
			+ "WHERE "
			+ "rp.unitUniqNo in (select distinct mc.unitUniqNo from MathContents mc where mc.svcPosbStts=1) "
			+ "GROUP BY rp.subject, rp.firUnit order by rp.unitUniqNo asc",nativeQuery = false)
	public List<MathUnitInfoGroup> selectFirUnitInfoOnlyExistContents();
	
	
	@Query(value = "SELECT new com.numberbox.mathinfo.dto.MathUnitInfoGroup(min(rp.unitUniqNo), rp.subject as parentVal, rp.secUnit as mainVal) FROM MathUnitInfo rp GROUP BY rp.firUnit, rp.secUnit order by rp.unitUniqNo asc",nativeQuery = false)
	public List<MathUnitInfoGroup> selectSecUnitInfo();
	
	@Query(value = "SELECT "
			+ "new com.numberbox.mathinfo.dto.MathUnitInfoGroup(min(rp.unitUniqNo), rp.subject as parentVal, rp.secUnit as mainVal) "
			+ "FROM MathUnitInfo rp "
			+ "WHERE "
			+ "rp.unitUniqNo in (select distinct mc.unitUniqNo from MathContents mc where mc.svcPosbStts=1) "
			+ "GROUP BY rp.firUnit, rp.secUnit order by rp.unitUniqNo asc",nativeQuery = false)
	public List<MathUnitInfoGroup> selectSecUnitInfoOnlyExistContents();
	
	@Query(value = "SELECT new com.numberbox.mathinfo.dto.MathUnitInfoGroup(rp.unitUniqNo, rp.secUnit as parentVal, rp.thrUnit as mainVal) FROM MathUnitInfo rp order by rp.unitUniqNo asc",nativeQuery = false)
	public List<MathUnitInfoGroup> selectThrUnitInfo();
	
	@Query(value = "SELECT "
			+ "new com.numberbox.mathinfo.dto.MathUnitInfoGroup(rp.unitUniqNo, rp.secUnit as parentVal, rp.thrUnit as mainVal) "
			+ "FROM MathUnitInfo rp "
			+ "WHERE "
			+ "rp.unitUniqNo in (select distinct mc.unitUniqNo from MathContents mc where mc.svcPosbStts=1) "
			+ "order by rp.unitUniqNo asc",nativeQuery = false)
	public List<MathUnitInfoGroup> selectThrUnitInfoOnlyExistContents();
	
	public MathUnitInfo findByUnitUniqNo(int unitUniqNo);
	
	public List<MathUnitInfo> findBySubject(String subject);
	
}
