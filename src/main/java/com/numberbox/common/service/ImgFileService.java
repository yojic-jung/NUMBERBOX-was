package com.numberbox.common.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.numberbox.common.dto.ImgFileInfoDto;
import com.numberbox.common.dto.ImgFileModel;
import com.numberbox.common.dto.TmpImgFileInfoDto;
import com.numberbox.common.entity.ImgFileInfo;
import com.numberbox.common.repository.ImgFileInfoRepo;
import com.numberbox.common.repository.TmpImgFileInfoRepo;
import com.numberbox.mathinfo.dto.MathContentsDto;
import com.numberbox.members.entity.Members;
import com.numberbox.security.util.StaticSecurityUtil;

@Service
public class ImgFileService {
	
	@Autowired
	ImgFileInfoRepo imgFileInfoRepo;
	@Autowired
	TmpImgFileInfoRepo tmpImgFileInfoRepo;
	
	
	 @Value("${numberbox.s3BucketUrl}")
    private String bucketUrl;
	
	@Transactional
	public void registerImgFileInfo(int actionId, int contentsNo, List<String> imgTagList) {
		if(imgTagList != null && imgTagList.size()!=0) {
			List<ImgFileInfo> imgPathList = new ArrayList<>();
			for(String imgTag : imgTagList) {
				if(imgTag.indexOf(bucketUrl)<0) {
					continue;
				}
				
				ImgFileInfoDto imgDto = new ImgFileInfoDto();
				imgDto.setActionId(actionId);
				imgDto.setContentsNo(contentsNo);
				
				String imgPathStr = imgTag.replace(bucketUrl, "");
				int firstSlashIdx = imgPathStr.indexOf("/");
				int lastSlashIdx = imgPathStr.lastIndexOf("/");
				
				String imgFileName = imgPathStr.substring(lastSlashIdx+1);
				
				try {
					//imgPathCode, imgPath, imgFileName 모두 기존 url에서 뽑아오기(url에서 뽑지 않고 연월 구해서 재셋팅하면 실제 저장된 s3 연월 폴더와 db에 저장된 폴더 경로 및 imgPathCode가 불일치 될 수 있음)
					imgDto.setImgPathCode(Integer.parseInt(imgPathStr.substring(firstSlashIdx+1, lastSlashIdx)));
					imgDto.setImgPath(imgPathStr.substring(0, lastSlashIdx));
					imgDto.setImgFileName(imgFileName);
					imgPathList.add(imgDto.toEntity());
				}catch(Exception e) {
					continue;
				}
			}
			
			//actionId가 10(문제 만들기), 11(hwpToHtml 파일변환)의 경우 contentsNo에 해당하는 이미지 파일 기존 것 삭제 후 재 추가
			if(actionId == 10 || actionId == 11) {
				imgFileInfoRepo.deleteByActionIdAndContentsNo(actionId, contentsNo);
			}
			imgFileInfoRepo.saveAll(imgPathList);
		}else {
			imgFileInfoRepo.deleteByActionIdAndContentsNo(actionId, contentsNo);
		}
		
		
	}
	
	@Transactional
	public void registerImgFileInfoMulti(int actionId, List<MathContentsDto> contentsDtoList) {
		for(MathContentsDto contentsDto : contentsDtoList) {
			if(contentsDto.getImgTagSrc() != null && contentsDto.getImgTagSrc().size()!=0) {
				List<ImgFileInfo> imgPathList = new ArrayList<>();
				for(String imgTag : contentsDto.getImgTagSrc()) {
					if(imgTag.indexOf(bucketUrl)<0) {
						continue;
					}
					
					ImgFileInfoDto imgDto = new ImgFileInfoDto();
					imgDto.setActionId(actionId);
					imgDto.setContentsNo(contentsDto.getContentsNo());
					
					String imgPathStr = imgTag.replace(bucketUrl, "");
					int firstSlashIdx = imgPathStr.indexOf("/");
					int lastSlashIdx = imgPathStr.lastIndexOf("/");
					
					String imgFileName = imgPathStr.substring(lastSlashIdx+1);
					
					try {
						//imgPathCode, imgPath, imgFileName 모두 기존 url에서 뽑아오기(url에서 뽑지 않고 연월 구해서 재셋팅하면 실제 저장된 s3 연월 폴더와 db에 저장된 폴더 경로 및 imgPathCode가 불일치 될 수 있음)
						imgDto.setImgPathCode(Integer.parseInt(imgPathStr.substring(firstSlashIdx+1, lastSlashIdx)));
						imgDto.setImgPath(imgPathStr.substring(0, lastSlashIdx));
						imgDto.setImgFileName(imgFileName);
						imgPathList.add(imgDto.toEntity());
					}catch(Exception e) {
						continue;
					}
				}
				
				//actionId가 10(문제 만들기), 11(hwpToHtml 파일변환)의 경우 contentsNo에 해당하는 이미지 파일 기존 것 삭제 후 재 추가
				if(actionId == 10 || actionId == 11) {
					imgFileInfoRepo.deleteByActionIdAndContentsNo(actionId, contentsDto.getContentsNo());
				}
				imgFileInfoRepo.saveAll(imgPathList);
			}else {
				imgFileInfoRepo.deleteByActionIdAndContentsNo(actionId, contentsDto.getContentsNo());
			}
		}
	}
	

	@Transactional
	public void registerTmpImgFileInfo(ImgFileModel imgFileModel, String s3Url) {
		Members members = StaticSecurityUtil.getMembers();
		UUID userUniqId = members.getUserUniqId();
		
		TmpImgFileInfoDto imgDto = new TmpImgFileInfoDto();
		imgDto.setUserUniqId(userUniqId);
		imgDto.setActionId(imgFileModel.getActionId());
		
		String imgPathStr = s3Url.replace(bucketUrl, "");
		int firstSlashIdx = imgPathStr.indexOf("/");
		int lastSlashIdx = imgPathStr.lastIndexOf("/");
		try {
			//imgPathCode, imgPath, imgFileName 모두 기존 url에서 뽑아오기(url에서 뽑지 않고 연월 구해서 재셋팅하면 실제 저장된 s3 연월 폴더와 db에 저장된 폴더 경로 및 imgPathCode가 불일치 될 수 있음)
			imgDto.setImgPathCode(Integer.parseInt(imgPathStr.substring(firstSlashIdx+1, lastSlashIdx)));
			imgDto.setImgPath(imgPathStr.substring(0, lastSlashIdx));
			imgDto.setImgFileName(imgPathStr.substring(lastSlashIdx+1));
		}catch(Exception e) {
			return;
		}
		
		tmpImgFileInfoRepo.save(imgDto.toEntity());
	}
	
	@Transactional
	public void removeImgFileInfo(int actionId, int contentsNo) {
		imgFileInfoRepo.deleteByActionIdAndContentsNo(actionId, contentsNo);
	}
	
	
	@Transactional
	public void removeTmpImgFileInfo(List<String> imgTagList) {
		Members members = StaticSecurityUtil.getMembers();
		UUID userUniqId = members.getUserUniqId();
		
		if(imgTagList != null) {
			String imgPathStr = "";
			for(String imgTag : imgTagList) {
				imgPathStr = imgTag.replace(bucketUrl, "");
				int lastSlashIdx = imgPathStr.lastIndexOf("/");
				if(lastSlashIdx <0) {
					continue;
				}
				String imgPath = imgPathStr.substring(0, lastSlashIdx);
				String imgFileName = imgPathStr.substring(lastSlashIdx+1);
				tmpImgFileInfoRepo.deleteByUserUniqIdAndImgPathAndImgFileName(userUniqId, imgPath, imgFileName);
			}
		}
	}
}
