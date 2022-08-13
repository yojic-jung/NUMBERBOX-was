package com.numberbox.members.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.numberbox.common.util.CommonUtil;
import com.numberbox.jwt.service.ExpiredRefreshTokenService;
import com.numberbox.jwt.util.JwtUtil;
import com.numberbox.members.dto.FollowUsersDto;
import com.numberbox.members.dto.MembersPrivateDto;
import com.numberbox.members.dto.MembersDto;
import com.numberbox.members.dto.MembersFollowInfoDto;
import com.numberbox.members.dto.MembersProfileDto;
import com.numberbox.members.dto.MembersRoleDto;
import com.numberbox.members.dto.PasswordModel;
import com.numberbox.members.entity.Members;
import com.numberbox.members.entity.MembersFollowInfo;
import com.numberbox.members.entity.MembersPrivate;
import com.numberbox.members.entity.MembersProfile;
import com.numberbox.members.entity.MembersRole;
import com.numberbox.members.repository.MembersFollowInfoRepository;
import com.numberbox.members.repository.MembersPrivateRepository;
import com.numberbox.members.repository.MembersProfileRepository;
import com.numberbox.members.repository.MembersRepository;
import com.numberbox.members.repository.MembersRoleRepository;
import com.numberbox.security.util.StaticSecurityUtil;

@Service
public class MembersService {
	
	@PersistenceContext
    EntityManager entityManager;
	@Autowired 
	private JwtUtil jwtUtil;
	@Autowired 
	private ExpiredRefreshTokenService expiredRefreshTokenService;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	private MembersRepository membersRepository;
	@Autowired
	private MembersProfileRepository membersProfileRepository;
	@Autowired
	private MembersRoleRepository membersRoleRepository;
	@Autowired
	private MembersPrivateRepository membersPrivateRepository;
	@Autowired
	private MembersFollowInfoRepository membersFollowInfoRepository;
	@Autowired
	ModelMapper modelMapper;
	
	
	
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
		MembersProfileDto membersProfileDto = new MembersProfileDto();
		membersProfileDto.setUserUniqId(members.getUserUniqId());
		//닉네임 10글자 임의 소문자 알파벳으로 설정
		int leftLimit = 97; // letter 'a'
	    int rightLimit = 122; // letter 'z'
	    int targetStringLength = 10;
	    Random random = new Random();
	    String generatedString = random.ints(leftLimit, rightLimit + 1)
	                                   .limit(targetStringLength)
	                                   .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
	                                   .toString();
	    membersProfileDto.setNickname(generatedString);
		membersProfileRepository.save(membersProfileDto.toEntity());
		MembersRoleDto membersRoleDto = new MembersRoleDto();
		UUID userUniqId = members.getUserUniqId();
		membersRoleDto.setUserUniqId(userUniqId);
		membersRoleDto.setEnabled(true);
		membersRoleDto.setRoleName("USER");
		MembersRole membersRole = membersRoleRepository.save(membersRoleDto.toEntity());
		
		if(membersDto.getUserName() != null) {
			MembersPrivateDto mebersPrivateDto = new MembersPrivateDto();
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
	
	
	@Transactional
	public HashMap<String, String> naverLogin(MembersDto membersDto, HttpServletRequest request) {
		String expiredToken = jwtUtil.resolveRefreshToken(request);
        //로그인시 클라이언트단에 refresh토큰이 남아있는 경우 해당 refresh토큰을 만료시킴(클라이언트단에 로그아웃시 refresh토큰 삭제하여 정상적인 로직시 해당 로직 타는 경우 없지만 refresh토큰 탈취하여 사용하는 경우 만료시킴 )
        if (expiredToken != null && !expiredToken.isEmpty()) {
            expiredRefreshTokenService.addExpiredToken(expiredToken);
        }
	        
		HashMap<String, String> map = new HashMap<>();
		Members members = membersRepository.findByEmail(membersDto.getEmail());
		List<MembersRole> roleList = new ArrayList<>();
		if(members != null) {
			List<MembersRole> membersRoleList = membersRoleRepository.findByUserUniqId(members.getUserUniqId());
			roleList = membersRoleList;
			map.put("isSuccess", "loginSuccess");
		}else {
			//로그인 API로  회원가입하는 경우
			//휴대폰 인증 체크
			boolean existsPhone = membersPrivateRepository.existsByPhoneNumber(membersDto.getPhoneNumber());
			if(existsPhone) {
				map.put("isSuccess", "existsPhone");
				return map;
			}
			
			membersDto.setPassword(bCryptPasswordEncoder.encode(CommonUtil.makeRandomPassword()) );
			membersDto.setHumanStatus(false);
			membersDto.setFailCount(0);
			members = membersRepository.save(membersDto.toEntity());
			MembersProfileDto membersProfileDto = new MembersProfileDto();
			membersProfileDto.setUserUniqId(members.getUserUniqId());
			//닉네임 10글자 임의 소문자 알파벳으로 설정
			int leftLimit = 97; // letter 'a'
		    int rightLimit = 122; // letter 'z'
		    int targetStringLength = 10;
		    Random random = new Random();
		    String generatedString = random.ints(leftLimit, rightLimit + 1)
		                                   .limit(targetStringLength)
		                                   .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
		                                   .toString();
		    membersProfileDto.setNickname(generatedString);
			membersProfileRepository.save(membersProfileDto.toEntity());
			MembersRoleDto membersRoleDto = new MembersRoleDto();
			UUID userUniqId = members.getUserUniqId();
			membersRoleDto.setUserUniqId(userUniqId);
			membersRoleDto.setEnabled(true);
			membersRoleDto.setRoleName("USER");
			MembersRole membersRole = membersRoleRepository.save(membersRoleDto.toEntity());
			roleList.add(membersRole);
			if(membersDto.getUserName() != null) {
				MembersPrivateDto mebersPrivateDto = new MembersPrivateDto();
				mebersPrivateDto.setUserUniqId(userUniqId);
				mebersPrivateDto.setUserName(membersDto.getUserName());
				mebersPrivateDto.setPhoneNumber(membersDto.getPhoneNumber());
				mebersPrivateDto.setBirth(membersDto.getBirth());
				membersPrivateRepository.save(mebersPrivateDto.toEntity());
			}
			
			 map.put("isSuccess", "signUpSuccess");
		}
		
		
        String accessToken = jwtUtil.createAccessToken(members.getEmail(), members.getUserUniqId(), roleList);
        String refreshToken = jwtUtil.createRefreshToken(members.getEmail(), members.getUserUniqId());
        
        //매니저 권한 임시 구현
        boolean isManager = false;
        boolean isAdmin = false;
        for(MembersRole role : roleList) {
        	if(role.getRoleName().equals("MANAGER")) {
        		isManager=true;
        	}
        	else if(role.getRoleName().equals("ADMIN")) {
        		isAdmin=true;
        	}
        }
        
        if(isAdmin) {
        	map.put("role", "ADMIN");
        }else if(!isAdmin && isManager) {
        	map.put("role", "MANAGER");
        }else {
        	map.put("role", "USER");
        }
        
        map.put("accessToken", accessToken);
        map.put("refreshToken", refreshToken);
		return map;
	}
	
	@Transactional
	public HashMap<String, Object> takeProfile(){
		HashMap<String, Object> map = new HashMap<String, Object>();
		Members members = StaticSecurityUtil.getMembers();
		UUID userUniqId = members.getUserUniqId();
		MembersProfile profile = membersProfileRepository.findByUserUniqId(userUniqId);
		MembersProfileDto profileDto = modelMapper.map(profile, MembersProfileDto.class);
		map.put("isSuccess", true);
		map.put("profile", profileDto);
		
		//내가 팔로잉한 팔로잉 정보 가져오기
		List<MembersFollowInfo> userFollowingInfo = membersFollowInfoRepository.findByFollowUsersFollowerUserNo(profile.getUserNo());
		map.put("followingCnt", userFollowingInfo.size());
		
		List<Long> userNoList = new ArrayList<>();
		for(MembersFollowInfo followInfo : userFollowingInfo) {
			userNoList.add(followInfo.getFollowUsers().getFollowingUserNo());
		}
		List<MembersProfile> followingProfile = membersProfileRepository.findByUserNoIn(userNoList);
		List<MembersProfileDto> followingDtoList = new ArrayList<>();
		for(MembersProfile following : followingProfile) {
			MembersProfileDto followingDto = modelMapper.map(following, MembersProfileDto.class);
			followingDtoList.add(followingDto);
		}
		map.put("myFollowing", followingDtoList);
		
		//나를 팔로잉 한 팔로워 정보 가져오기
		List<MembersFollowInfo> userFollowInfo = membersFollowInfoRepository.findByFollowUsersFollowingUserNo(profile.getUserNo());
		map.put("followerCnt", userFollowInfo.size());
		
		List<Long> userNoList2 = new ArrayList<>();
		for(MembersFollowInfo followInfo : userFollowInfo) {
			userNoList2.add(followInfo.getFollowUsers().getFollowerUserNo());
		}
		List<MembersProfile> followerProfile = membersProfileRepository.findByUserNoIn(userNoList2);
		List<MembersProfileDto> followerDtoList = new ArrayList<>();
		for(MembersProfile follower : followerProfile) {
			MembersProfileDto followerDto = modelMapper.map(follower, MembersProfileDto.class);
			followerDtoList.add(followerDto);
		}
		map.put("myFollower", followerDtoList);
		
		return map;
	}
	
	@Transactional
	public HashMap<String, Object> takeUserProfile(long userNo){
		HashMap<String, Object> map = new HashMap<String, Object>();
		Members members = StaticSecurityUtil.getMembers();
		UUID userUniqId = members.getUserUniqId();
		MembersProfile userProfile = membersProfileRepository.findByUserNo(userNo);
		map.put("isSuccess", true);
		map.put("profile", userProfile);
		
		//팔로우 여부 정보 가져오기
		MembersProfile myProfile= membersProfileRepository.findByUserUniqId(userUniqId);
		MembersFollowInfo membersFollowInfo =membersFollowInfoRepository.findByFollowUsersFollowingUserNoAndFollowUsersFollowerUserNo(userNo, myProfile.getUserNo());
		if(membersFollowInfo != null) {
			map.put("isFollowed", true);
		}else {
			map.put("isFollowed", false);
		}
		
		//팔로워 수 가져오기
		List<MembersFollowInfo> userFollowInfo = membersFollowInfoRepository.findByFollowUsersFollowingUserNo(userNo);
		map.put("followerCnt", userFollowInfo.size());
		
		
		return map;
	}
	
	@Transactional
	public HashMap<String, Object> registerProfileImg(MembersProfileDto membersProfileDto, String path) throws IllegalStateException, IOException {
		HashMap<String, Object> map = new HashMap<>();
		Members members = StaticSecurityUtil.getMembers();
		UUID userUniqId = members.getUserUniqId();
		MembersProfile profile = membersProfileRepository.findByUserUniqId(userUniqId);
		
		String alreadyExistedImgName = profile.getProfileImgName();
		Random random1 = new Random();
		if(membersProfileDto.getProfileImgFile()!=null && !membersProfileDto.getProfileImgFile().isEmpty()) {
			long currentTime1 = System.currentTimeMillis();
			int randomValue1 = random1.nextInt(100);

			String fileName = Long.toString(currentTime1) + "_"+randomValue1+"_"+membersProfileDto.getProfileImgFile().getOriginalFilename();
			File file = new File(path+"/profileImg" , fileName);
			membersProfileDto.getProfileImgFile().transferTo(file);
			membersProfileDto.setProfileImgPath("/webapp/static/profileImg/");
			membersProfileDto.setProfileImgName(fileName);
			membersProfileRepository.changeProfileImg(userUniqId, "/webapp/static/profileImg/", membersProfileDto.getProfileImgName());
			
			if(alreadyExistedImgName != null) {
				//이미지삭제
				File file2 = new File(path+"/profileImg/"+alreadyExistedImgName);
				file2.delete();
			}
			map.put("isSuccess", true);
			map.put("profileImgPath", "/profileImg");
			map.put("profileImgName", membersProfileDto.getProfileImgName());
		}else {
			//실패 메시지
			map.put("isSucess", false);
		}
		
		return map;
	}
	
	@Transactional
	public HashMap<String, Object> changeNickname(String nickname){
		HashMap<String, Object> map = new HashMap<>();
		Members members = StaticSecurityUtil.getMembers();
		UUID userUniqId = members.getUserUniqId();
		int isSuccess = membersProfileRepository.changeNickname(userUniqId, nickname);
		
		if(isSuccess==1) map.put("isSuccess", true);
		else map.put("isSuccess", false);
		
		return map;
	}
	
	@Transactional
	public HashMap<String, Object> followingUser(int userNo){
		HashMap<String, Object> map = new HashMap<>();
		Members members = StaticSecurityUtil.getMembers();
		UUID userUniqId = members.getUserUniqId();
		MembersProfile membersProfile= membersProfileRepository.findByUserUniqId(userUniqId);
		MembersFollowInfoDto membersFollowInfoDto = new MembersFollowInfoDto();
		FollowUsersDto followUsersDto = new FollowUsersDto();
		followUsersDto.setFollowingUserNo(userNo);
		followUsersDto.setFollowerUserNo(membersProfile.getUserNo());
		membersFollowInfoDto.setFollowUsersDto(followUsersDto);
		MembersFollowInfo membersFollowInfo = membersFollowInfoRepository.save(membersFollowInfoDto.toEntity());
		boolean isSuccess = entityManager.contains(membersFollowInfo);
		if(isSuccess) {
			//팔로워 수 가져오기
			List<MembersFollowInfo> userFollowInfo = membersFollowInfoRepository.findByFollowUsersFollowingUserNo(userNo);
			map.put("followerCnt", userFollowInfo.size());
			map.put("isSuccess", true);
		} 
		else {
			map.put("isSuccess", false);
		} 
		
		return map;
	}
	
	
	@Transactional
	public HashMap<String, Object> followingCancel(int userNo){
		HashMap<String, Object> map = new HashMap<>();
		Members members = StaticSecurityUtil.getMembers();
		UUID userUniqId = members.getUserUniqId();
		MembersProfile membersProfile= membersProfileRepository.findByUserUniqId(userUniqId);
		int isSuccess = membersFollowInfoRepository.deleteByFollowUsersFollowingUserNoAndFollowUsersFollowerUserNo(userNo, membersProfile.getUserNo());
		if(isSuccess == 1) {
			map.put("isSuccess", true);
			//팔로워 수 가져오기
			List<MembersFollowInfo> userFollowInfo = membersFollowInfoRepository.findByFollowUsersFollowingUserNo(userNo);
			map.put("followerCnt", userFollowInfo.size());
		}else {
			map.put("isSuccess", false);
		}
		return map;
	}

	public void tmpPasswordChange() {
		Members members = membersRepository.findByEmail("wogus@naver.com");
		membersRepository.changePassword(members.getUserUniqId(), bCryptPasswordEncoder.encode("snack12!"));
	}
	
    
	public HashMap<String, Object> findEmail(MembersDto memberDto){
		HashMap<String, Object> map = new HashMap<>();
		MembersPrivate membersPrivate = membersPrivateRepository.findByPhoneNumberAndUserName(memberDto.getPhoneNumber(), memberDto.getUserName());
		if(membersPrivate != null) {
			Members members = membersRepository.findByUserUniqId(membersPrivate.getUserUniqId());
			map.put("isExist", true);
			map.put("email", members.getEmail());
		}else {
			map.put("isExist", false);
		}
		return map;
	}
	
	public HashMap<String, Object> findPassword(HttpServletRequest request, String email) throws AddressException, MessagingException{
		HashMap<String, Object> map = new HashMap<>();
		Members members = membersRepository.findByEmail(email);
		if(members != null) {
			map.put("isExist", true);
			String randPasswrod = CommonUtil.makeRandomPassword();
			MembersDto membersDto = modelMapper.map(members, MembersDto.class);
			membersDto.setPassword(bCryptPasswordEncoder.encode(randPasswrod));
			membersDto.setTmpPassword(true);
			membersRepository.save(membersDto.toEntity());
			CommonUtil.mailSender(request, email, randPasswrod);
		}else {
			map.put("isExist", false);
		}
		return map;
	}
	
	public HashMap<String, Object> confirmPassword(String password){
		HashMap<String, Object> map = new HashMap<>();
		Members members = StaticSecurityUtil.getMembers();
		Members corfirmMembers =membersRepository.findByEmail(members.getEmail());
		boolean isCertified = bCryptPasswordEncoder.matches(password, corfirmMembers.getPassword());
		if(isCertified) {
			map.put("isCertified", true);
			MembersPrivate membersPrivate= membersPrivateRepository.findByUserUniqId(corfirmMembers.getUserUniqId());
			map.put("memberInfo", membersPrivate);
		}else {
			map.put("isCertified", false);
		}
		return map;
	}
	
	public HashMap<String, Object> changePassword(PasswordModel passwordModel){
		HashMap<String, Object> map = new HashMap<>();
		Members members = StaticSecurityUtil.getMembers();
		Members corfirmMembers =membersRepository.findByEmail(members.getEmail());
		boolean isCertified = bCryptPasswordEncoder.matches(passwordModel.getOldPassword(), corfirmMembers.getPassword());
		if(isCertified) {
			MembersDto membersDto = modelMapper.map(corfirmMembers, MembersDto.class);
			membersDto.setTmpPassword(false);
			membersDto.setPassword(bCryptPasswordEncoder.encode(passwordModel.getNewPassword()));
			membersRepository.save(membersDto.toEntity());
			map.put("isPassChanged", true);
		}else {
			map.put("isPassChanged", false);
		}
		return map;
	}
	
	
	public HashMap<String, Object> changePhoneNumber(MembersDto memberDto){
		HashMap<String, Object> map = new HashMap<>();
		Members members = StaticSecurityUtil.getMembers();
		Members corfirmMembers =membersRepository.findByEmail(members.getEmail());
		MembersPrivate membersPrivate= membersPrivateRepository.findByUserUniqId(corfirmMembers.getUserUniqId());
		boolean isUserCertified = true;
		if(!membersPrivate.getUserName().equals(memberDto.getUserName())) {
			isUserCertified=false;
		}
		if(!membersPrivate.getBirth().equals(memberDto.getBirth())) {
			isUserCertified=false;
		}
		
		if(isUserCertified) {
			map.put("isChanged", true);
			MembersPrivateDto membersPrivateDto = modelMapper.map(membersPrivate, MembersPrivateDto.class);
			membersPrivateDto.setPhoneNumber(memberDto.getPhoneNumber());
			membersPrivateRepository.save(membersPrivateDto.toEntity());
			map.put("isChanged", true);
		}else {
			map.put("isChanged", false);
		}
		return map;
	}
	
}
