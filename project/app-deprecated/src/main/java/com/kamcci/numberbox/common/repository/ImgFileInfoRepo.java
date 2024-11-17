package com.kamcci.numberbox.common.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kamcci.numberbox.common.entity.ImgFileInfo;

public interface ImgFileInfoRepo extends JpaRepository<ImgFileInfo, UUID> {

	public List<ImgFileInfo> findByActionIdAndContentsNo(int actionId, int contentsNo);

	public int deleteByActionIdAndContentsNo(int actionId, int contentsNo);

}
