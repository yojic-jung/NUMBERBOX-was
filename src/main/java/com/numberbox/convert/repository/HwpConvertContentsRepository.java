package com.numberbox.convert.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.numberbox.convert.entity.HwpConvertContents;

public interface HwpConvertContentsRepository extends JpaRepository<HwpConvertContents, Long> {  
	
	public int deleteByConvertNoAndUserUniqId(Long convertNo, UUID uuid);
	
	public int countByUserUniqIdAndErrStts(UUID uuid, boolean errStts);
	
	public HwpConvertContents findByConvertNo(Long convertNo);
	
	public List<HwpConvertContents> findByUserUniqIdAndErrSttsOrderBySysCreateDateDesc(UUID uuid, boolean errStts);
	
	public HwpConvertContents findByConvertNo(long convertNo);
	
	@Transactional
	@Modifying // select 문이 아님을 나타낸다
	@Query(value = "UPDATE HwpConvertContents hwpCon set hwpCon.errStts =:errStts where hwpCon.userUniqId =:uuid and hwpCon.convertNo=:convertNo", nativeQuery = false)
	public int changeErrStts(@Param("uuid") UUID uuid, @Param("convertNo") long convertNo, @Param("errStts") boolean errStts);

	@Transactional
	@Modifying // select 문이 아님을 나타낸다
	@Query(value = "UPDATE HwpConvertContents hwpCon set hwpCon.converted =:converted where hwpCon.userUniqId =:uuid and hwpCon.convertNo=:convertNo", nativeQuery = false)
	public int changeConverted(@Param("uuid") UUID uuid, @Param("convertNo") long convertNo, @Param("converted") boolean converted);

}
