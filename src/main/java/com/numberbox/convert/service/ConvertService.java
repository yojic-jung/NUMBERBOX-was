package com.numberbox.convert.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.numberbox.convert.dto.HwpConvertContentsDto;
import com.numberbox.convert.entity.HwpConvertContents;
import com.numberbox.convert.repository.HwpConvertContentsRepository;
import com.numberbox.members.entity.Members;
import com.numberbox.members.entity.MembersProfile;
import com.numberbox.members.repository.MembersProfileRepository;
import com.numberbox.security.util.StaticSecurityUtil;

@Service
public class ConvertService {

	@Autowired
	HwpConvertContentsRepository hwpConvertContentsRepository;
	@Autowired
	private MembersProfileRepository membersProfileRepository;
	
	@Autowired
	ModelMapper modelMapper;
	
	public HashMap<String, Object> checkHwpConvertCnt() {
		Members members = StaticSecurityUtil.getMembers();
		MembersProfile memProfile = membersProfileRepository.findByUserUniqId(members.getUserUniqId());
		
		HashMap<String, Object> map = new HashMap<>();
		//이미 3회 이상 다운 받은 경우 다운 불가
		if(memProfile.getHwpDownCnt() >= 3) {
			map.put("existMsg", true);
			map.put("serverMsg", "일일 다운로드 및 업로드 허용 횟수 3회를 모두 사용하셨습니다.");
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
		hwpConvertContentsRepository.save(hwpConvertContentsDto.toEntity());
		
		if(isFirst) {
			MembersProfile membersProfile = membersProfileRepository.findByUserUniqId(members.getUserUniqId());
			membersProfileRepository.changeHwpDownCnt(members.getUserUniqId(), membersProfile.getHwpDownCnt()+1);
		}
		
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
}
