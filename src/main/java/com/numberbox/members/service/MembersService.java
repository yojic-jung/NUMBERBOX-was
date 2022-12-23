package com.numberbox.members.service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.numberbox.common.util.ClientConnect;
import com.numberbox.common.util.CommonUtil;
import com.numberbox.common.util.CustomTenFieldDto;
import com.numberbox.jwt.service.ExpiredRefreshTokenService;
import com.numberbox.jwt.util.JwtUtil;
import com.numberbox.mathinfo.repository.MathContentsRepository;
import com.numberbox.members.dto.FollowUsersDto;
import com.numberbox.members.dto.HwpJsonStrDto;
import com.numberbox.members.dto.MembersDto;
import com.numberbox.members.dto.MembersFollowInfoDto;
import com.numberbox.members.dto.MembersPrivateDto;
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
	
	@Value("${numberbox.hwpSocketIp}")
	private String customsocketip;
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
	private MathContentsRepository mathContentsRepository;
	
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
		membersDto.setHumanStatus(0);
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
			
			//탈퇴회원인 경우 로그인 불가 처리
			boolean isDropAccount = false;
			for(MembersRole role : roleList) {
				if(!role.isEnabled()) {
					isDropAccount = true;
				}
			}
			
			if(isDropAccount) {
				map.put("isSuccess", "dropAccount");
				return map;
			}
			
			//로그인 시간 및 휴먼상태 초기화
			membersRepository.initLastLoginDate(members.getUserUniqId());
	        membersRepository.initHumanStatus(members.getUserUniqId());
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
			membersDto.setHumanStatus(0);
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
		Members corfirmMembers = membersRepository.findByEmail(members.getEmail());
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
	
	public HashMap<String, Object> myAccountDrop(MembersDto memberDto){
		HashMap<String, Object> map = new HashMap<>();
		boolean isCertified = true;
		Members members = StaticSecurityUtil.getMembers();
		List<MembersRole> roleList =  members.getRole();
		boolean isAdminOrManager = false;
		for(MembersRole role : roleList) {
			if(role.getRoleName().equals("ADMIN") || role.getRoleName().equals("MANAGER")) isAdminOrManager=true;		//관리자는 넘버링크 문제 모두 수정가능
		}
		
		if(isAdminOrManager) {
			map.put("existMsg", true);
			map.put("serverMsg", "매니저 또는 관리자 계정은 탈퇴가 불가능합니다.");
			return map;
		}
		
		isCertified = memberDto.getEmail().equals(members.getEmail());
		if(!isCertified) {
			map.put("existMsg", true);
			map.put("serverMsg", "계정 정보가 올바르지 않습니다.");
			return map;
		}
		Members corfirmMembers = membersRepository.findByEmail(members.getEmail());
		isCertified = bCryptPasswordEncoder.matches(memberDto.getPassword(), corfirmMembers.getPassword());
		if(!isCertified) {
			map.put("existMsg", true);
			map.put("serverMsg", "계정 정보가 올바르지 않습니다.");
			return map;
		}
		
		MembersPrivate membersPrivate= membersPrivateRepository.findByUserUniqId(corfirmMembers.getUserUniqId());
		if(!membersPrivate.getUserName().equals(memberDto.getUserName())
			|| !membersPrivate.getPhoneNumber().equals(memberDto.getPhoneNumber())
			|| !membersPrivate.getBirth().equals(memberDto.getBirth())) {
			map.put("existMsg", true);
			map.put("serverMsg", "가입하신 사용자 정보와 입력하신 휴대폰 본인인증 정보가 다릅니다.");
			return map;
		}
		
		MembersDto corfirmMembersDto = modelMapper.map(corfirmMembers, MembersDto.class);
		corfirmMembersDto.setHumanStatus(2);
		corfirmMembersDto.setLastLoginDate(LocalDateTime.now());
		map.put("isSuccess", true);
		membersRepository.save(corfirmMembersDto.toEntity());
		return map;
	}
	
	
	public HashMap<String, Object> myContentsCheckForHwpDown(String contentsNo) {
		Members members = StaticSecurityUtil.getMembers();
		MembersProfile memProfile = membersProfileRepository.findByUserUniqId(members.getUserUniqId());
		
		HashMap<String, Object> map = new HashMap<>();
		//이미 3회 이상 다운 받은 경우 다운 불가
		if(memProfile.getHwpDownCnt() >= 3) {
			map.put("existMsg", true);
			map.put("contentsNo", -1);
			map.put("serverMsg", "일일 다운로드 및 업로드 허용 횟수 3회를 모두 사용하셨습니다.");
			return map;
		}
		
		if(contentsNo.equals("all")) {
			return map;
		}
		
		int contentsNum = Integer.parseInt(contentsNo);
		
		UUID conOwnUuid = mathContentsRepository.findOnlyUuidByContentsNo(contentsNum);
		//자기자신의 문제 아닌 경우 다운 불가
		if(!conOwnUuid.equals(members.getUserUniqId())) {
			map.put("existMsg", true);
			map.put("contentsNo", -1);
			map.put("serverMsg", "본인의 문제가 아닌 경우 다운이 불가능합니다.");
			return map;
		}
		
		map.put("contentsNo", contentsNum);
		return map;
	}
	
	
	public String connectPyServerForMakeHwp(String path, HwpJsonStrDto hwpJsonDto) throws IOException {
		Members members = StaticSecurityUtil.getMembers();
		MembersProfile memProfile = membersProfileRepository.findByUserUniqId(members.getUserUniqId());
		ClientConnect cc = new ClientConnect(customsocketip);	
		String newFileName= cc.getFile(path, hwpJsonDto.getJsonString());
		cc.closeConnections();
		//hwp 다운 카운트 +1 증가
		membersProfileRepository.changeHwpDownCnt(memProfile.getUserUniqId(), memProfile.getHwpDownCnt()+1);
		return newFileName;
	}
	
	public HashMap<String, Object> registerMemberProfile(int profileType){
		Members members = StaticSecurityUtil.getMembers();
		int isSuccess = membersProfileRepository.registerProfileType(members.getUserUniqId(), profileType);
		HashMap<String, Object> map = new HashMap<String, Object>();
		if(isSuccess == 1) {
			map.put("isSuccess", true);
		}else {
			map.put("isSuccess", false);
		}
		return map;
	}
	
	
	
	//프로필별 가입자 수 
	public List<CustomTenFieldDto> statisticMembersCntByProfileType(){
		List<CustomTenFieldDto> list = new ArrayList<>();
		CustomTenFieldDto customHeaderDto = new CustomTenFieldDto("미등록", "원장", "강사", "교사", "학부모", "학생", "기타", null, null, null);
		int cnt0 = membersProfileRepository.countByProfileType(0);
		int cnt1 = membersProfileRepository.countByProfileType(1);
		int cnt2 = membersProfileRepository.countByProfileType(2);
		int cnt3 = membersProfileRepository.countByProfileType(3);
		int cnt4 = membersProfileRepository.countByProfileType(4);
		int cnt5 = membersProfileRepository.countByProfileType(5);
		int cnt6 = membersProfileRepository.countByProfileType(6);
		CustomTenFieldDto customBodyDto = new CustomTenFieldDto(cnt0, cnt1, cnt2, cnt3, cnt4, cnt5, cnt6, null, null, null);
		list.add(customHeaderDto);
		list.add(customBodyDto);
		return list;
	}
	
	//시간대별 가입자 수 
	public List<CustomTenFieldDto> statisticMembersCntGrouBySignupDateHour(){
		List<CustomTenFieldDto> list = membersRepository.statisticMembersCntGrouBySignupDateHour();
		CustomTenFieldDto customHeaderDto = new CustomTenFieldDto("00시 ~ 03시", "03시 ~ 06시", "06시 ~ 09시","09시 ~ 12시","12시 ~ 15시", "15시 ~ 18시", "18시 ~ 21시", "21시 ~ 24시",null, null);
		list.add(0, customHeaderDto);
		return list;
	}
	
	//날짜별 가입자 수
	public List<CustomTenFieldDto> statisticMembersCntBySignupDate(){
		//전체
		int totalCnt = membersRepository.countBySignupDateAfter(LocalDateTime.of(2022, 4, 1, 0, 0, 0));
		//지난 한달 가입자수
		int lastOneMonthCnt = membersRepository.countBySignupDateAfter(LocalDateTime.now().minusMonths(1).with(LocalTime.MIN));
		//지난 일주일 가입자수
		int lastOneWeekCnt = membersRepository.countBySignupDateAfter(LocalDateTime.now().minusWeeks(1).with(LocalTime.MIN));
		//어제 가입자수
		int yesterDayCnt = membersRepository.countBySignupDateAfter(LocalDateTime.now().minusDays(1).with(LocalTime.MIN));
		//오늘 가입자수
		int todayCnt = membersRepository.countBySignupDateAfter(LocalDateTime.now().with(LocalTime.MIN));
		
		yesterDayCnt= yesterDayCnt-todayCnt;
		
		CustomTenFieldDto customHeaderDto = new CustomTenFieldDto("전체", "지난 한달", "지난 일주일","어제","오늘", null, null, null,null, null);
		CustomTenFieldDto customBodyDto = new CustomTenFieldDto(totalCnt, lastOneMonthCnt, lastOneWeekCnt, yesterDayCnt, todayCnt, null, null, null,null, null);
		List<CustomTenFieldDto> list = new ArrayList<>();
		list.add(customHeaderDto);
		list.add(customBodyDto);
		return list;
	}
	
	
	
	//프로필에 따른 시간대별 가입자수
	public List<CustomTenFieldDto> statisticMembersByHourGrouByProfileType(){
		List<CustomTenFieldDto> list = membersRepository.statisticMembersByHourGrouByProfileType();
		CustomTenFieldDto customHeaderDto = new CustomTenFieldDto("프로필", "00시 ~ 03시", "03시 ~ 06시", "06시 ~ 09시","09시 ~ 12시","12시 ~ 15시", "15시 ~ 18시", "18시 ~ 21시", "21시 ~ 24시",null);
		list.add(0, customHeaderDto);
		return list;
	}
	
	//나이대별 회원가입자 수
	public List<CustomTenFieldDto> statisticMembersByAge(){
		List<CustomTenFieldDto> list = membersPrivateRepository.statisticMembersByAge();
		LocalDate now = LocalDate.now();
		String fullYearStr = Integer.toString(now.getYear());
		int year = Integer.parseInt(fullYearStr.substring(2));
		int teenAgersCnt = 0;
		int twoZeroMembersCnt = 0;
		int threeZeroMembersCnt = 0;
		int fourZeroMembersCnt = 0;
		int fiveZeroMembersCnt = 0;
		int overSixZeroMembersCnt = 0;
		for(CustomTenFieldDto yearCntList: list) {
			int memberBirthYear = Integer.parseInt(yearCntList.getNbCol1().toString());
			int memberBirthCnt = Integer.parseInt(yearCntList.getNbCol2().toString());
			
			int memberAge = 0;
			if(memberBirthYear>year) {
				memberBirthYear=memberBirthYear+1900;
			}else {
				memberBirthYear=memberBirthYear+2000;
			}
			memberAge = Integer.parseInt(fullYearStr)-memberBirthYear+1;
			if(memberAge<20) {
				teenAgersCnt += memberBirthCnt;
			}else if(memberAge<30) {
				twoZeroMembersCnt += memberBirthCnt;
			}else if(memberAge<40) {
				threeZeroMembersCnt += memberBirthCnt;
			}else if(memberAge<50) {
				fourZeroMembersCnt += memberBirthCnt;
			}else if(memberAge<60) {
				fiveZeroMembersCnt += memberBirthCnt;
			}else if(memberAge>=60) {
				overSixZeroMembersCnt  += memberBirthCnt;
			}
		}
		
		CustomTenFieldDto customHeaderDto = new CustomTenFieldDto("미성년자", "20대", "30대", "40대","50대","60대 이상", null, null, null, null);
		CustomTenFieldDto customBodyrDto = new CustomTenFieldDto(teenAgersCnt, twoZeroMembersCnt, threeZeroMembersCnt, fourZeroMembersCnt, fiveZeroMembersCnt, overSixZeroMembersCnt, null, null, null, null);
		List<CustomTenFieldDto> newList = new ArrayList<>();
		newList.add(customHeaderDto);
		newList.add(customBodyrDto);
		return newList;
	}
	
}
