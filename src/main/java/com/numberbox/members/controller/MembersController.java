package com.numberbox.members.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.numberbox.common.util.CustomTenFieldDto;
import com.numberbox.iamport.IamportClient;
import com.numberbox.members.dto.HwpJsonStrDto;
import com.numberbox.members.dto.MembersDto;
import com.numberbox.members.dto.MembersProfileDto;
import com.numberbox.members.dto.PasswordModel;
import com.numberbox.members.entity.Members;
import com.numberbox.members.repository.MembersFollowInfoRepository;
import com.numberbox.members.service.MembersService;
import com.numberbox.security.util.StaticSecurityUtil;
import com.siot.IamportRestClient.response.Certification;
import com.siot.IamportRestClient.response.IamportResponse;

@RestController
public class MembersController {
	
	@Autowired
	MembersService membersService;
	@Autowired
	MembersFollowInfoRepository membersFollowInfoRepository;
	/*
	@PostMapping("/login")
	public HashMap<String, Object> login(@ModelAttribute MembersDto membersDto, HttpServletRequest request, HttpServletResponse response) {
		HashMap<String, Object> map = membersService.login(membersDto, request);
		Cookie refreshTokenCookie = new Cookie("refresh-token", (String)map.get("refreshToken"));
        String loginState = (String)request.getParameter("loginState");
        if(loginState !=null && loginState.equals("keep")) {
        	refreshTokenCookie.setMaxAge(60*60*24*60);
        }
        response.setHeader("access-token", (String)map.get("accessToken"));
        response.addCookie(refreshTokenCookie);
        
        HashMap<String, Object> returnMap = new  HashMap<>();
        returnMap.put("isLogin", map.get("isLogin"));	
		return returnMap;
	}
	*/
	
	@PostMapping("/loginSuccess")
	public HashMap<String, Object> loginSuccess(HttpServletRequest request, HttpServletResponse response) {
		String refreshToken = (String)request.getAttribute("refreshToken");
		String loginState = (String)request.getAttribute("loginState");
		Cookie refreshTokenCookie = new Cookie("refresh-token", refreshToken);
		refreshTokenCookie.setPath("/");		//context-path를 myWasApi로 설정하면서 쿠키 Path가 /myWasApi로 바뀜 다시 / 루트 컨텐스트로 쿠키 패쓰 설정
		if(loginState !=null && loginState.equals("keep")) {
        	refreshTokenCookie.setMaxAge(60*60*24*30);
        }else {
        	refreshTokenCookie.setMaxAge(60*60*6);			//6시간
        }
		
		response.addCookie(refreshTokenCookie);
		
		/*
		ResponseCookie cookie = ResponseCookie.from("refresh-token", refreshToken)
            	.path("/")
                .sameSite("None")
                .httpOnly(true)
                .secure(true)
                .maxAge(60*60*24*30)
                .build();
    	
        response.setHeader("Set-Cookie", cookie.toString());
        */
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("isLogin", true);
		return map;
	}
	
	
	@PostMapping("/loginFail")
	public HashMap<String, Object> loginFailure(HttpServletRequest request, HttpServletResponse response) {
		String customErrMsg = (String)request.getAttribute("customErrMsg");
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("isSuccess", false);
		map.put("customErrMsg", customErrMsg);
		return map;
	}
	
	@PostMapping("/signup")
	public HashMap<String, Object> signup(MembersDto members, HttpServletResponse response) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		HashMap<String, String> returnMap = membersService.signUp(members);
		String isSuccess = returnMap.get("isSuccess");
		if(isSuccess.equals("success")) {
			Cookie refreshTokenCookie = new Cookie("refresh-token", returnMap.get("refreshToken"));
			response.setHeader("access-token", returnMap.get("accessToken"));
			refreshTokenCookie.setPath("/");		//context-path를 myWasApi로 설정하면서 쿠키 Path가 /myWasApi로 바뀜 다시 / 루트 컨텐스트로 쿠키 패쓰 설정
        	refreshTokenCookie.setMaxAge(60*60*6);			//6시간
	        response.addCookie(refreshTokenCookie);
		}
		map.put("isSuccess", isSuccess);
		return map;
	}
	
	@PostMapping("/naverLogin")
	public HashMap<String, Object> naverLogin(MembersDto members, HttpServletRequest request, HttpServletResponse response) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		HashMap<String, String> returnMap = membersService.naverLogin(members, request);
		String isSuccess = returnMap.get("isSuccess");
		if(isSuccess.equals("loginSuccess") || isSuccess.equals("signUpSuccess")) {
			Cookie refreshTokenCookie = new Cookie("refresh-token", returnMap.get("refreshToken"));
			response.setHeader("access-token", returnMap.get("accessToken"));
			response.setHeader("role", returnMap.get("role"));
			String loginState = (String)request.getParameter("loginState");
	        if(loginState !=null && loginState.equals("keep")) {
	        	refreshTokenCookie.setMaxAge(60*60*24*30);		//30일
	        }else {
	        	refreshTokenCookie.setMaxAge(60*60*6);			//6시간
	        }
	        refreshTokenCookie.setPath("/");		//context-path를 myWasApi로 설정하면서 쿠키 Path가 /myWasApi로 바뀜 다시 / 루트 컨텐스트로 쿠키 패쓰 설정
	        response.addCookie(refreshTokenCookie);
	        
		}
		map.put("isSuccess", isSuccess);
		return map;
	}
	
	@RequestMapping("/accessDenied")
	public HashMap<String, Object> accessDenied(HttpServletResponse response) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("existMsg", true);
		map.put("serverMsg", "해당 요청에 접근 권한이 없습니다.");
		return map;
	}
	
	@GetMapping("/takeProfile")
	public HashMap<String, Object> takeProfile(HttpServletRequest request, HttpServletResponse response) {
		HashMap<String, Object> map = membersService.takeProfile();
		return map;
	}
	
	@GetMapping("/takeUserProfile")
	public HashMap<String, Object> takeUserProfile(@RequestParam int userNo, HttpServletRequest request, HttpServletResponse response) {
		HashMap<String, Object> map = membersService.takeUserProfile(userNo);
		return map;
	}
	
	@PostMapping("/registerProfileImg")
	public HashMap<String, Object> registerProfileImg(@ModelAttribute MembersProfileDto membersProfileDto, HttpServletRequest request, HttpServletResponse response) throws IllegalStateException, IOException {
		String path = request.getSession().getServletContext().getRealPath("/static");	//임시용, 배포 이후 프로젝트 바깥 경로로 설정하는게 좋음(배포용 개발용 따로 관리 필요)
		HashMap<String, Object> map = membersService.registerProfileImg(membersProfileDto, path);
		return map;
	}
	
	@GetMapping("/changeNickname")
	public HashMap<String, Object> changeNickname(@RequestParam String nickname, HttpServletRequest request, HttpServletResponse response) throws IllegalStateException, IOException {
		HashMap<String, Object> map = membersService.changeNickname(nickname);
		return map;
	}
	
	@GetMapping("/followingUser")
	public HashMap<String, Object> followingUser(@RequestParam int userNo, HttpServletRequest request, HttpServletResponse response) throws IllegalStateException, IOException {
		HashMap<String, Object> map = membersService.followingUser(userNo);
		return map;
	}
	
	@GetMapping("/followingCancel")
	public HashMap<String, Object> followingCancel(@RequestParam int userNo, HttpServletRequest request, HttpServletResponse response) throws IllegalStateException, IOException {
		HashMap<String, Object> map = membersService.followingCancel(userNo);
		return map;
	}
	
	@GetMapping("/tmpPasswordChange")
	public String tmpPasswordChange() {
		membersService.tmpPasswordChange();
		return "";
	}
	
	@GetMapping(value="/certifications/{imp_uid}")
	public Object certifications(@PathVariable String imp_uid, HttpServletRequest request, HttpServletResponse response) throws Exception {

		IamportClient iam = new IamportClient("2626730431329357", "jm37bnUp381Ov6hQjE8fXJZry3Tj53NopRwAeq0hz1548nVr14HYNGqmKjGPntdMlJnzanRKpXOykK0m");
		IamportResponse<Certification> cer = iam.certificationByImpUid(imp_uid);
		
		SimpleDateFormat date = new SimpleDateFormat("yyMMdd");
		
		Certification cer1 = cer.getResponse();
		cer1.getPhone();
		HashMap<String, String> map = new HashMap<>();
		map.put("name", cer.getResponse().getName());
		map.put("birth", date.format(cer.getResponse().getBirth()) );
		map.put("phone", cer.getResponse().getPhone());
		
		return map;
	}
	
	@GetMapping(value="/takeMerchantUid")
	public Object takeMerchantUid() {
		HashMap<String, String> map = new HashMap<>();
		map.put("merchantUid", "ORD20180131-0000011");
		map.put("merchantIdCode", "imp48047014");
		
		return map;
	}
	
	@PostMapping(value="/findEmail")
	public Object findEmail(MembersDto memberDto) {
		HashMap<String, Object> map = membersService.findEmail(memberDto);
		return map;
	}
	
	@GetMapping(value="/findPassword")
	public Object findPassWd(HttpServletRequest request) throws AddressException, MessagingException {
		String email = (String) request.getParameter("email");
		HashMap<String, Object> map = membersService.findPassword(request, email);
		return map;
	}
	
	@GetMapping(value="/takeMyEmail")
	public HashMap<String, String> takeMyEmail(){
		Members members = StaticSecurityUtil.getMembers();
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("email", members.getEmail());
		return map;
	}
	
	@PostMapping(value="/confirmPassword")
	public HashMap<String, Object> confirmPassword(MembersDto memberDto) {
		HashMap<String, Object> map = membersService.confirmPassword(memberDto.getPassword());
		return map;
	}
	
	
	@PostMapping(value="/changePassword")
	public HashMap<String, Object> changePassword(PasswordModel passwordModel) {
		HashMap<String, Object> map = membersService.changePassword(passwordModel);
		return map;
	}
	
	@PostMapping(value="/changePhoneNumber")
	public HashMap<String, Object> changePhoneNumber(MembersDto MembersDto) {
		HashMap<String, Object> map = membersService.changePhoneNumber(MembersDto);
		return map;
	}
	
	@PostMapping(value="/myAccountDrop")
	public HashMap<String, Object> myAccountDrop(MembersDto MembersDto) {
		HashMap<String, Object> map = membersService.myAccountDrop(MembersDto);
		return map;
	}
	
	@GetMapping(value="/myContentsCheckForHwpDown")
	public HashMap<String, Object> myContentsCheckForHwpDown(HttpServletRequest request) {
		String contentsNo = (String)request.getParameter("contentsNo");
		HashMap<String, Object> map = membersService.myContentsCheckForHwpDown(contentsNo);
		return map;
	}
	
	@GetMapping(value="/registerMemberProfile")
	public HashMap<String, Object> registerMemberProfile(HttpServletRequest request) {
		String profileType = (String)request.getParameter("profileType");
		HashMap<String, Object> map = membersService.registerMemberProfile(Integer.parseInt(profileType));
		return map;
	}
	
	@PostMapping(value="/takeHwpFile")
	public void takeHwpFile(HwpJsonStrDto hwpJsonDto, HttpServletRequest request, HttpServletResponse response) throws IOException {
		String path = request.getSession().getServletContext().getRealPath("/static/")+"/userHwp/";
		String newFileName = membersService.connectPyServerForMakeHwp(path, hwpJsonDto);
		String saveFileName = path+newFileName;
		// 직접 파일 정보를 변수에 저장해 놨지만, 이 부분이 db에서 읽어왔다고 가정한다.
		String contentType = "application/x-hwp";
        File file = new File(path, newFileName);
        long fileLength = file.length();
        //파일의 크기와 같지 않을 경우 프로그램이 멈추지 않고 계속 실행되거나, 잘못된 정보가 다운로드 될 수 있다.
        response.setHeader("Content-Disposition", "attachment; filename=\"" +newFileName+ "\";");
        response.setHeader("Content-Transfer-Encoding", "binary");
        response.setHeader("Content-Type", contentType);
        response.setHeader("Content-Length", "" + fileLength);
        response.setHeader("Pragma", "no-cache;");
        response.setHeader("Expires", "-1;");
        try(
                FileInputStream fis = new FileInputStream(saveFileName);
                OutputStream out = response.getOutputStream();
        ){
                int readCount = 0;
                byte[] buffer = new byte[1024];
            while((readCount = fis.read(buffer)) != -1){
                    out.write(buffer,0,readCount);
            }
        }catch(Exception ex){
            throw new RuntimeException("file Save Error");
        }
	}
	
	
	@GetMapping(value="/takeMembersStatistic")
	public HashMap<String, Object> takeMembersStatistic(HttpServletRequest request) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		//날짜별 가입자 수 
		List<CustomTenFieldDto> membesrCntBySignupDate = membersService.statisticMembersCntBySignupDate();
		//프로필별 가입자수
		List<CustomTenFieldDto> membesrCntByProfile = membersService.statisticMembersCntByProfileType();
		//시간대별 가입자 수 
		List<CustomTenFieldDto> membesrCntByHourPeriod = membersService.statisticMembersCntGrouBySignupDateHour();
		//프로필에 따른 시간대별 가입자수
		List<CustomTenFieldDto> membesrCntByProAndHourPeriod = membersService.statisticMembersByHourGrouByProfileType();
		//나이대별 회원가입자 수
		List<CustomTenFieldDto> membersCntByAge = membersService.statisticMembersByAge();
		membersService.statisticMembersCntBySignupDate();
		
		map.put("membesrCntBySignupDate", membesrCntBySignupDate);
		map.put("membesrCntByProfile", membesrCntByProfile);
		map.put("membesrCntByHourPeriod", membesrCntByHourPeriod);
		map.put("membesrCntByProAndHourPeriod", membesrCntByProAndHourPeriod);
		map.put("membersCntByAge", membersCntByAge);
		return map;
	}
}
