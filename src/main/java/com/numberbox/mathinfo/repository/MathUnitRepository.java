package com.numberbox.mathinfo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.numberbox.mathinfo.dto.MathUnitInfoGroup;
import com.numberbox.mathinfo.entity.MathUnitInfo;

public interface MathUnitRepository extends JpaRepository <MathUnitInfo, Integer> {
	
	@Query(value = "SELECT new com.numberbox.mathinfo.dto.MathUnitInfoGroup(min(rp.unitUniqNo), rp.subject as mainVal) FROM MathUnitInfo rp GROUP BY rp.subject order by rp.unitUniqNo asc",nativeQuery = false)
	public List<MathUnitInfoGroup> selectSubjectInfo();

	@Query(value = "SELECT new com.numberbox.mathinfo.dto.MathUnitInfoGroup(min(rp.unitUniqNo), rp.subject as parentVal, rp.firUnit as mainVal) FROM MathUnitInfo rp GROUP BY rp.subject, rp.firUnit order by rp.unitUniqNo asc",nativeQuery = false)
	public List<MathUnitInfoGroup> selectFirUnitInfo();
	
	@Query(value = "SELECT new com.numberbox.mathinfo.dto.MathUnitInfoGroup(min(rp.unitUniqNo), rp.subject as parentVal, rp.secUnit as mainVal) FROM MathUnitInfo rp GROUP BY rp.firUnit, rp.secUnit order by rp.unitUniqNo asc",nativeQuery = false)
	public List<MathUnitInfoGroup> selectSecUnitInfo();
	
	@Query(value = "SELECT new com.numberbox.mathinfo.dto.MathUnitInfoGroup(rp.unitUniqNo, rp.secUnit as parentVal, rp.thrUnit as mainVal) FROM MathUnitInfo rp order by rp.unitUniqNo asc",nativeQuery = false)
	public List<MathUnitInfoGroup> selectThrUnitInfo();
	
	public MathUnitInfo findByUnitUniqNo(int unitUniqNo);
	
	public List<MathUnitInfo> findBySubject(String subject);
	
}
