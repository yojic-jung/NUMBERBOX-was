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
import com.numberbox.members.dto.MembersNoDto;
import com.numberbox.members.dto.MembersRoleDto;
import com.numberbox.members.entity.Members;
import com.numberbox.members.entity.MembersNo;
import com.numberbox.members.entity.MembersRole;
import com.numberbox.members.repository.MembersPrivateRepository;
import com.numberbox.members.repository.MembersNoRepository;
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
	private MembersNoRepository membersNoRepository;
	@Autowired
	private MembersRoleRepository membersRoleRepository;
	@Autowired
	private MembersPrivateRepository membersPrivateRepository;
	
	
	/*
	@Transactional
	public HashMap<String, Object> login(MembersDto membersDto, HttpServletRequest request) {
		HashMap<String, Object> map = new HashMap<>();
		Members members = membersRepository.findByEmail(membersDto.getEmail());
		if(members == null) {
			map.put("isLogin", false);
			return map;
		}
		boolean isMatches = bCryptPasswordEncoder.matches(membersDto.getPassword(), members.getPassword());
		if(isMatches) {
	        String expiredToken = jwtUtil.resolveRefreshToken(request);
	        if (expiredToken != null && !expiredToken.isEmpty()) {
	            expiredRefreshTokenService.addExpiredToken(expiredToken);
	        }

	        MembersNo membersNo = membersNoRepository.findByUserUniqId(members.getUserUniqId());
	        
	        String accessToken = jwtUtil.createAccessToken(members.getEmail(), membersNo.getUserNo(), members.getRole());
	        String refreshToken = jwtUtil.createRefreshToken(members.getEmail(), membersNo.getUserNo());
	        
	        map.put("accessToken", accessToken);
	        map.put("refreshToken", refreshToken);
	        map.put("isLogin", true);	
	        return map;
		}else {
			map.put("isLogin", false);
			return map;
		}
	}
	*/
	
	
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
		MembersNoDto membersNoDto = new MembersNoDto();
		membersNoDto.setUserUniqId(members.getUserUniqId());
		MembersNo membersNo = membersNoRepository.save(membersNoDto.toEntity());
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
        String accessToken = jwtUtil.createAccessToken(members.getEmail(), membersNo.getUserNo(), list);
        String refreshToken = jwtUtil.createRefreshToken(members.getEmail(), membersNo.getUserNo());
        
        map.put("isSuccess", "success");
        map.put("accessToken", accessToken);
        map.put("refreshToken", refreshToken);
		return map;
	}
}
