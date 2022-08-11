package com.numberbox.scheduler.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.numberbox.common.util.CommonUtil;
import com.numberbox.members.dto.MembersDto;
import com.numberbox.members.entity.Members;
import com.numberbox.members.repository.MembersRepository;

@Service
public class SchedulerService {

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	private MembersRepository membersRepository;
	@Autowired
	ModelMapper modelMapper;
	
	@Transactional
	public void tmpPassChange() {
		//tmpPassword가 1인 값 새로운 비밀번호로 변경
		int tmpPasswordLength = membersRepository.countByTmpPassword(true);
		int loopCnt = tmpPasswordLength/10000;
		if(tmpPasswordLength%10000 > 0) {
			loopCnt = loopCnt+1;
		}
		for(int i=0; i<loopCnt; i++) {
			List<Members> membersList = membersRepository.findTop10000ByTmpPassword(true);
			List<Members> targetMembersList = new ArrayList<>();
			for(Members members : membersList) {
				MembersDto membersDto = modelMapper.map(members, MembersDto.class);
				membersDto.setTmpPassword(false);
				membersDto.setPassword(bCryptPasswordEncoder.encode(CommonUtil.makeRandomPassword()));
				targetMembersList.add(membersDto.toEntity());
			}
			membersRepository.saveAll(targetMembersList);
		}
	}
}
