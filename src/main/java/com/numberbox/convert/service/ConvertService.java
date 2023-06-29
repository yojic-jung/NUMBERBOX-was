package com.numberbox.convert.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.numberbox.aws.s3.service.AwsS3Service;
import com.numberbox.common.util.CustomTenFieldDto;
import com.numberbox.convert.dto.HwpConvertContentsDto;
import com.numberbox.convert.dto.HwpConvertContentsStatisticDto;
import com.numberbox.convert.entity.HwpConvertContents;
import com.numberbox.convert.repository.HwpConvertContentsRepository;
import com.numberbox.convert.repository.HwpConvertContentsStatisticRepository;
import com.numberbox.members.entity.Members;
import com.numberbox.members.entity.MembersProfile;
import com.numberbox.members.entity.MembersRole;
import com.numberbox.members.repository.MembersProfileRepository;
import com.numberbox.security.util.StaticSecurityUtil;

@Service
public class ConvertService {

	@Autowired
    private AwsS3Service awsS3Service;
	@Autowired
	HwpConvertContentsRepository hwpConvertContentsRepository;
	@Autowired
	HwpConvertContentsStatisticRepository hwpConvertContentsStatisticRepository;
	@Autowired
	private MembersProfileRepository membersProfileRepository;
	
	@Autowired
	ModelMapper modelMapper;
	
	public HashMap<String, Object> checkHwpConvertCnt() {
		Members members = StaticSecurityUtil.getMembers();
		List<MembersRole> roleList =  members.getRole();
		boolean isTopTester = false;
		for(MembersRole role : roleList) {
			if(role.getRoleName().equals("TOP_TESTER")) isTopTester=true;		//관리자는 넘버링크 문제 모두 수정가능
		}
		MembersProfile memProfile = membersProfileRepository.findByUserUniqId(members.getUserUniqId());
		
		HashMap<String, Object> map = new HashMap<>();
		//이미 3회 이상 다운 받은 경우 다운 불가
		if(!isTopTester && memProfile.getHwpDownCnt() >= 3) {
			map.put("existMsg", true);
			map.put("serverMsg", "일일 한글 파일 다운로드 및 업로드 허용 횟수 3회를 모두 사용하셨습니다.");
			return map;
		}else {
			map.put("existMsg", false);
		}
		
		int upldFileCnt = hwpConvertContentsRepository.countByUserUniqIdAndErrStts(members.getUserUniqId(), false);
		if(upldFileCnt >= 10) {
			map.put("existMsg", true);
			map.put("serverMsg", "[나의 업로드 내역]에 존재하는 파일은 최대 10개입니다.\n[나의 업로드 내역]에 존재하는 파일을 삭제 후 재시도 해주시기 바랍니다.");
			return map;
		}
		
		return map;
	}
	
	public List<HwpConvertContentsDto> takeConvertContents() {
		Members members = StaticSecurityUtil.getMembers();
		List<HwpConvertContents> list = hwpConvertContentsRepository.findByUserUniqIdAndErrSttsOrderBySysCreateDateDesc(members.getUserUniqId(), false);
		List<HwpConvertContentsDto> contentsList = new ArrayList<>();
		for(HwpConvertContents convertContents : list) {
			HwpConvertContentsDto hwpConvertContentsDto = modelMapper.map(convertContents, HwpConvertContentsDto.class);
			contentsList.add(hwpConvertContentsDto);
		}
		return contentsList;
	}
	
	public List<CustomTenFieldDto> takeConvertContentsStatistic() {
		List<CustomTenFieldDto> list = hwpConvertContentsStatisticRepository.statisticConvertContentsByProfile();
		CustomTenFieldDto customHeaderDto = new CustomTenFieldDto("미등록", "원장", "강사", "교사","학부모","학생", "기타", null, null,null);
		list.add(0, customHeaderDto);
		return list;
	}
	
	public HwpConvertContentsDto takeErrConvertContents(String convertNo) {
		HwpConvertContents convertContents = hwpConvertContentsRepository.findByConvertNo(Long.parseLong(convertNo));
		HwpConvertContentsDto hwpConvertContentsDto = modelMapper.map(convertContents, HwpConvertContentsDto.class);
		return hwpConvertContentsDto;
	}
	
	@Transactional
	public HashMap<String, Object> removeConvertContents(Long convertNo) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		Members members = StaticSecurityUtil.getMembers();
		int isSuccess = hwpConvertContentsRepository.deleteByConvertNoAndUserUniqId(convertNo, members.getUserUniqId());
		if(isSuccess != 0) {
			map.put("isSuccess", true);
		}else {
			map.put("isSuccess", false);
		}
		return map;
	}
	
	@Transactional
	public HashMap<String, Object> registerConvertContents(HwpConvertContentsDto hwpConvertContentsDto, boolean isFirst) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		Members members = StaticSecurityUtil.getMembers();
		if(hwpConvertContentsDto.getConvertNo() != null) {
			HwpConvertContents hwpConvertContents = hwpConvertContentsRepository.findByConvertNo(hwpConvertContentsDto.getConvertNo());
			if(!hwpConvertContents.getUserUniqId().equals(members.getUserUniqId())) {
				map.put("existMsg", "자신이 업로드한 파일인 아닌 경우 수정할 수 없습니다.");
				map.put("isSuccess", false);
				return map;
			}
			hwpConvertContentsDto.setConvertFileName(hwpConvertContents.getConvertFileName());
		}
		
		hwpConvertContentsDto.setUserUniqId(members.getUserUniqId());
		HwpConvertContents hwpConvert = hwpConvertContentsRepository.save(hwpConvertContentsDto.toEntity());
		HwpConvertContentsStatisticDto statisticDto = new HwpConvertContentsStatisticDto();
		statisticDto.setConvertFileName(hwpConvertContentsDto.getConvertFileName());
		statisticDto.setConvertNo(hwpConvert.getConvertNo());
		statisticDto.setUserUniqId(hwpConvertContentsDto.getUserUniqId());
		hwpConvertContentsStatisticRepository.deleteByConvertNo(hwpConvertContentsDto.getConvertNo());
		hwpConvertContentsStatisticRepository.save(statisticDto.toEntity());
		
		if(isFirst) {
			MembersProfile membersProfile = membersProfileRepository.findByUserUniqId(members.getUserUniqId());
			membersProfileRepository.changeHwpDownCnt(members.getUserUniqId(), membersProfile.getHwpDownCnt()+1);
		}
		map.put("convertNo", hwpConvert.getConvertNo());
		map.put("isSuccess", true);
		return map;
	}
	
	
	@Transactional
	public HashMap<String, Object> changeErrStts(String convertNo) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		Members members = StaticSecurityUtil.getMembers();
		int isSuccess = hwpConvertContentsRepository.changeErrStts(members.getUserUniqId(), Long.parseLong(convertNo), true);
		if(isSuccess != 0) {
			map.put("isSuccess", true);
		}else {
			map.put("isSuccess", false);
		}
		return map;
	}
	
	@Transactional
	public HashMap<String, Object> changeConverted(Long convertNo, boolean converted) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		Members members = StaticSecurityUtil.getMembers();
		int isSuccess = hwpConvertContentsRepository.changeConverted(members.getUserUniqId(), convertNo, converted);
		if(isSuccess != 0) {
			map.put("isSuccess", true);
		}else {
			map.put("isSuccess", false);
		}
		return map;
	}
	
	//파일 S3 서버로 전달
	public String moveToS3Server(String orgFilePath) throws IOException {
		Random random1 = new Random();
    	long currentTime1 = System.currentTimeMillis();
		int randomValue1 = random1.nextInt(100);
        
		//hwpToHtml 이미지 파일  S3서버 imgFileDir로 이동
		File hwpToHtmlDir = new File(orgFilePath);
		File hwpToHtmlDirImgList[] = hwpToHtmlDir.listFiles();
		String s3FileUrl = "";
		if(hwpToHtmlDirImgList != null) {
			for(int i = 0; i < hwpToHtmlDirImgList.length; i++) {
				s3FileUrl = awsS3Service.uploadToS3SeverSingleFile(11, hwpToHtmlDirImgList[i], currentTime1+"_"+randomValue1+"_"+hwpToHtmlDirImgList[i].getName());
				s3FileUrl = s3FileUrl.replace(hwpToHtmlDirImgList[i].getName(), "");
			}
		}
		return s3FileUrl;
	}
	
	
}
