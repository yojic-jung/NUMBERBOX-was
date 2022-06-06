package com.numberbox.members.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.numberbox.jwt.util.JwtUtil;
import com.numberbox.members.dto.MebersPrivateDto;
import com.numberbox.members.dto.MembersDto;
import com.numberbox.members.dto.MembersRoleDto;
import com.numberbox.members.entity.Members;
import com.numberbox.members.entity.MembersRole;
import com.numberbox.members.repository.MembersPrivateRepository;
import com.numberbox.members.repository.MembersRepository;
import com.numberbox.members.repository.MembersRoleRepository;

@Service
public class MembersService {
	@Autowired 
	private JwtUtil jwtUtil;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	private MembersRepository membersRepository;
	@Autowired
	private MembersRoleRepository membersRoleRepository;
	@Autowired
	private MembersPrivateRepository membersPrivateRepository;
	
	@Transactional
	public HashMap<String, String> signUp(MembersDto membersDto) {
		HashMap<String, String> map = new HashMap<>();
		boolean existsEmail = membersRepository.existsByEmail(membersDto.getEmail());
		if(existsEmail) {
			map.put("isSuccess", "existsEmail");
			return map;
		}
		boolean existsPhone = membersPrivateRepository.existsByPhoneNumber(membersDto.getPhoneNumber());
		if(existsPhone) {
			map.put("isSuccess", "existsPhone");
			return map;
		}
		membersDto.setPassword(bCryptPasswordEncoder.encode(membersDto.getPassword()) );
		membersDto.setHumanStatus(false);
		membersDto.setFailCount(0);
		Members members = membersRepository.save(membersDto.toEntity());
		MembersRoleDto membersRoleDto = new MembersRoleDto();
		UUID userUniqId = members.getUserUniqId();
		membersRoleDto.setUserUniqId(userUniqId);
		membersRoleDto.setEnabled(true);
		membersRoleDto.setRoleName("USER");
		MembersRole membersRole = membersRoleRepository.save(membersRoleDto.toEntity());
		
		if(membersDto.getUserName() != null) {
			MebersPrivateDto mebersPrivateDto = new MebersPrivateDto();
			mebersPrivateDto.setUserUniqId(userUniqId);
			mebersPrivateDto.setUserName(membersDto.getUserName());
			mebersPrivateDto.setPhoneNumber(membersDto.getPhoneNumber());
			mebersPrivateDto.setBirth(membersDto.getBirth());
			membersPrivateRepository.save(mebersPrivateDto.toEntity());
		}
		List<MembersRole> list = new ArrayList<>();
		list.add(membersRole);
        String accessToken = jwtUtil.createAccessToken(members.getEmail(), members.getUserUniqId(), list);
        String refreshToken = jwtUtil.createRefreshToken(members.getEmail(), members.getUserUniqId());
        
        map.put("isSuccess", "success");
        map.put("accessToken", accessToken);
        map.put("refreshToken", refreshToken);
		return map;
	}
}
