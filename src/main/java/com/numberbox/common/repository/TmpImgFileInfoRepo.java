package com.numberbox.common.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.numberbox.common.entity.TmpImgFileInfo;

public interface TmpImgFileInfoRepo extends JpaRepository<TmpImgFileInfo, Long> {  
	
	public int deleteByUserUniqIdAndImgPathAndImgFileName(UUID uuid, String imgPath, String imgFileName);
	
}
