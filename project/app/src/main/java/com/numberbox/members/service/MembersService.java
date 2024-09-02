package com.numberbox.members.service;

import com.numberbox.modules.auth.control.util.AuthPasswordEncoder;
import com.numberbox.common.util.ClientConnect;
import com.numberbox.common.util.CommonUtil;
import com.numberbox.common.util.CustomTenFieldDto;
import com.numberbox.jwt.service.RefreshTokenInfoService;
import com.numberbox.mathinfo.repository.MathContentsRepository;
import com.numberbox.members.dto.*;
import com.numberbox.members.entity.*;
import com.numberbox.members.repository.*;
import com.numberbox.members.restapi.dto.request.HwpJsonStrRequest;
import com.numberbox.members.restapi.dto.request.MembersRequest;
import com.numberbox.members.restapi.dto.request.PasswordRequest;
import com.numberbox.todo.StaticSecurityUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.WebUtils;

import javax.mail.MessagingException;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@Service
public class MembersService {

    @Value("${numberbox.hwpSocketIp}")
    private String customsocketip;
    @PersistenceContext
    EntityManager entityManager;
    @Autowired
    private CommonUtil commonUtil;
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
    private AccessLogInfoRepository accessLogInfoRepository;
    @Autowired
    private AuthPasswordEncoder authPasswordEncoder;

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private EmailIdCodeRepository emailIdCodeRepository;
    @Autowired
    private RefreshTokenInfoService refreshTokenService;

    @Transactional
    public void delRefreshToken(HttpServletRequest request, HttpServletResponse response) {
        String jwtToken = "";
        Cookie cookie = WebUtils.getCookie(request, "refresh-token");
        if (cookie != null) {
            jwtToken = cookie.getValue();
            cookie.setMaxAge(0);
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            cookie.setSecure(true);
            cookie.setValue("");
            response.addCookie(cookie);
        }
        refreshTokenService.deleteByToken(jwtToken);
    }

    @Transactional
    public Map<String, Object> takeProfile() {
        HashMap<String, Object> map = new HashMap<String, Object>();
        Members members = StaticSecurityUtil.getMembers();
        UUID userUniqId = members.getUserUniqId();
        MembersProfile profile = membersProfileRepository.findByUserUniqId(userUniqId);
        MembersProfileDto profileDto = modelMapper.map(profile, MembersProfileDto.class);
        map.put("isSuccess", true);
        map.put("profile", profileDto);

        // 내가 팔로잉한 팔로잉 정보 가져오기
        List<MembersFollowInfo> userFollowingInfo = membersFollowInfoRepository
                .findByFollowUsersFollowerUserNo(profile.getUserNo());
        map.put("followingCnt", userFollowingInfo.size());

        List<Long> userNoList = new ArrayList<>();
        for (MembersFollowInfo followInfo : userFollowingInfo) {
            userNoList.add(followInfo.getFollowUsers().getFollowingUserNo());
        }
        List<MembersProfile> followingProfile = membersProfileRepository.findByUserNoIn(userNoList);
        List<MembersProfileDto> followingDtoList = new ArrayList<>();
        for (MembersProfile following : followingProfile) {
            MembersProfileDto followingDto = modelMapper.map(following, MembersProfileDto.class);
            followingDtoList.add(followingDto);
        }
        map.put("myFollowing", followingDtoList);

        // 나를 팔로잉 한 팔로워 정보 가져오기
        List<MembersFollowInfo> userFollowInfo = membersFollowInfoRepository
                .findByFollowUsersFollowingUserNo(profile.getUserNo());
        map.put("followerCnt", userFollowInfo.size());

        List<Long> userNoList2 = new ArrayList<>();
        for (MembersFollowInfo followInfo : userFollowInfo) {
            userNoList2.add(followInfo.getFollowUsers().getFollowerUserNo());
        }
        List<MembersProfile> followerProfile = membersProfileRepository.findByUserNoIn(userNoList2);
        List<MembersProfileDto> followerDtoList = new ArrayList<>();
        for (MembersProfile follower : followerProfile) {
            MembersProfileDto followerDto = modelMapper.map(follower, MembersProfileDto.class);
            followerDtoList.add(followerDto);
        }
        map.put("myFollower", followerDtoList);

        return map;
    }

    @Transactional
    public Map<String, Object> takeUserProfile(long userNo) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        Members members = StaticSecurityUtil.getMembers();
        UUID userUniqId = members.getUserUniqId();
        MembersProfile userProfile = membersProfileRepository.findByUserNo(userNo);
        map.put("isSuccess", true);
        map.put("profile", userProfile);

        // 팔로우 여부 정보 가져오기
        MembersProfile myProfile = membersProfileRepository.findByUserUniqId(userUniqId);
        MembersFollowInfo membersFollowInfo = membersFollowInfoRepository
                .findByFollowUsersFollowingUserNoAndFollowUsersFollowerUserNo(userNo, myProfile.getUserNo());
        if (membersFollowInfo != null) {
            map.put("isFollowed", true);
        } else {
            map.put("isFollowed", false);
        }

        // 팔로워 수 가져오기
        List<MembersFollowInfo> userFollowInfo = membersFollowInfoRepository.findByFollowUsersFollowingUserNo(userNo);
        map.put("followerCnt", userFollowInfo.size());

        return map;
    }

    @Transactional
    public Map<String, Object> registerProfileImg(MembersProfileDto membersProfileDto, String path)
            throws IllegalStateException, IOException {
        HashMap<String, Object> map = new HashMap<>();
        Members members = StaticSecurityUtil.getMembers();
        UUID userUniqId = members.getUserUniqId();
        MembersProfile profile = membersProfileRepository.findByUserUniqId(userUniqId);

        String alreadyExistedImgName = profile.getProfileImgName();
        Random random1 = new Random();
        if (membersProfileDto.getProfileImgFile() != null && !membersProfileDto.getProfileImgFile().isEmpty()) {
            long currentTime1 = System.currentTimeMillis();
            int randomValue1 = random1.nextInt(100);

            String fileName = Long.toString(currentTime1) + "_" + randomValue1 + "_"
                    + membersProfileDto.getProfileImgFile().getOriginalFilename();
            File file = new File(path + "/profileImg", fileName);
            membersProfileDto.getProfileImgFile().transferTo(file);
            membersProfileDto.setProfileImgPath("/webapp/static/profileImg/");
            membersProfileDto.setProfileImgName(fileName);
            membersProfileRepository.changeProfileImg(userUniqId, "/webapp/static/profileImg/",
                    membersProfileDto.getProfileImgName());

            if (alreadyExistedImgName != null) {
                // 이미지삭제
                File file2 = new File(path + "/profileImg/" + alreadyExistedImgName);
                file2.delete();
            }
            map.put("isSuccess", true);
            map.put("profileImgPath", "/profileImg");
            map.put("profileImgName", membersProfileDto.getProfileImgName());
        } else {
            // 실패 메시지
            map.put("isSucess", false);
        }

        return map;
    }

    @Transactional
    public Map<String, Object> changeNickname(String nickname) {
        HashMap<String, Object> map = new HashMap<>();
        Members members = StaticSecurityUtil.getMembers();
        UUID userUniqId = members.getUserUniqId();
        int isSuccess = membersProfileRepository.changeNickname(userUniqId, nickname);

        if (isSuccess == 1)
            map.put("isSuccess", true);
        else
            map.put("isSuccess", false);

        return map;
    }

    @Transactional
    public Map<String, Object> followingUser(int userNo) {
        HashMap<String, Object> map = new HashMap<>();
        Members members = StaticSecurityUtil.getMembers();
        UUID userUniqId = members.getUserUniqId();
        MembersProfile membersProfile = membersProfileRepository.findByUserUniqId(userUniqId);
        MembersFollowInfoDto membersFollowInfoDto = new MembersFollowInfoDto();
        FollowUsersDto followUsersDto = new FollowUsersDto();
        followUsersDto.setFollowingUserNo(userNo);
        followUsersDto.setFollowerUserNo(membersProfile.getUserNo());
        membersFollowInfoDto.setFollowUsersDto(followUsersDto);
        MembersFollowInfo membersFollowInfo = membersFollowInfoRepository.save(membersFollowInfoDto.toEntity());
        boolean isSuccess = entityManager.contains(membersFollowInfo);
        if (isSuccess) {
            // 팔로워 수 가져오기
            List<MembersFollowInfo> userFollowInfo = membersFollowInfoRepository
                    .findByFollowUsersFollowingUserNo(userNo);
            map.put("followerCnt", userFollowInfo.size());
            map.put("isSuccess", true);
        } else {
            map.put("isSuccess", false);
        }

        return map;
    }

    @Transactional
    public Map<String, Object> followingCancel(int userNo) {
        HashMap<String, Object> map = new HashMap<>();
        Members members = StaticSecurityUtil.getMembers();
        UUID userUniqId = members.getUserUniqId();
        MembersProfile membersProfile = membersProfileRepository.findByUserUniqId(userUniqId);
        int isSuccess = membersFollowInfoRepository
                .deleteByFollowUsersFollowingUserNoAndFollowUsersFollowerUserNo(userNo, membersProfile.getUserNo());
        if (isSuccess == 1) {
            map.put("isSuccess", true);
            // 팔로워 수 가져오기
            List<MembersFollowInfo> userFollowInfo = membersFollowInfoRepository
                    .findByFollowUsersFollowingUserNo(userNo);
            map.put("followerCnt", userFollowInfo.size());
        } else {
            map.put("isSuccess", false);
        }
        return map;
    }

    public Map<String, Object> findEmail(MembersRequest memberDto) {
        HashMap<String, Object> map = new HashMap<>();
        MembersPrivate membersPrivate = membersPrivateRepository
                .findByPhoneNumberAndUserName(memberDto.getPhoneNumber(), memberDto.getUserName());
        if (membersPrivate != null) {
            Members members = membersRepository.findByUserUniqId(membersPrivate.getUserUniqId());
            map.put("isExist", true);
            map.put("email", members.getEmail());
        } else {
            map.put("isExist", false);
        }
        return map;
    }

    public Map<String, Object> findPassword(HttpServletRequest request, String email)
            throws MessagingException {
        HashMap<String, Object> map = new HashMap<>();
        Members members = membersRepository.findByEmail(email);
        if (members != null) {
            map.put("isExist", true);
            String randPasswrod = CommonUtil.makeRandomPassword();
            MembersRequest membersRequest = modelMapper.map(members, MembersRequest.class);
            membersRequest.setPassword(authPasswordEncoder.encode(randPasswrod));
            membersRequest.setTmpPassword(true);
            membersRepository.save(membersRequest.toEntity());

            String contents = "<div style='width:500px;height:600px; font-family:\"Malgun Gothic\";background: rgb(226, 224, 224);padding:30px 100px;'><div style='width:350px; margin:150px auto;line-height:180%; padding:20px;background:white;'><div style='color:#3e6599;font-size:25px;'>비밀번호 안내</div><br/><div style='font-size:15px;'>안녕하세요. 회원님의 요청으로 발급해드리는 <br/>임시 비밀번호는 <span style='font-weight:bold;'>"
                    + randPasswrod
                    + "</span> 입니다.</div><br/><div style='font-weight:bold;background:rgb(236, 250, 106);font-size:13px; padding:10px;word-break:keep-all;'>임시 비밀번호는 오전 06시까지 유효하니 로그인 후<br/>임시 비밀번호를 변경하여 주시기 바랍니다.</div><br/><div style='text-align:center;'><br/><a href='https://nsoohak.com/login' style='text-decoration:none'><span style='text-decoration:none;font-size:18px;border:none; border-radius:14px; padding:10px; background:#3e6599; color:white;cursor:pointer;font-weight:bold'>N명의수학 로그인하기</span></a></div></div></div>";

            commonUtil.sendMail(email, "[N명의수학] 비밀번호 안내", contents);
        } else {
            map.put("isExist", false);
        }
        return map;
    }

    public Map<String, Object> confirmPassword(String password) {
        HashMap<String, Object> map = new HashMap<>();
        Members members = StaticSecurityUtil.getMembers();
        Members corfirmMembers = membersRepository.findByEmail(members.getEmail());
        boolean isCertified = authPasswordEncoder.matches(password, corfirmMembers.getPassword());
        if (isCertified) {
            map.put("isCertified", true);
            MembersPrivate membersPrivate = membersPrivateRepository.findByUserUniqId(corfirmMembers.getUserUniqId());
            map.put("memberInfo", membersPrivate);
        } else {
            map.put("isCertified", false);
        }
        return map;
    }

    public Map<String, Object> changePassword(PasswordRequest passwordRequest) {
        HashMap<String, Object> map = new HashMap<>();
        Members members = StaticSecurityUtil.getMembers();
        Members corfirmMembers = membersRepository.findByEmail(members.getEmail());
        boolean isCertified = authPasswordEncoder.matches(passwordRequest.getOldPassword(),
                corfirmMembers.getPassword());
        if (isCertified) {
            MembersRequest membersRequest = modelMapper.map(corfirmMembers, MembersRequest.class);
            membersRequest.setTmpPassword(false);
            membersRequest.setPassword(authPasswordEncoder.encode(passwordRequest.getNewPassword()));
            membersRepository.save(membersRequest.toEntity());
            map.put("isPassChanged", true);
        } else {
            map.put("isPassChanged", false);
        }
        return map;
    }

    public Map<String, Object> changePhoneNumber(MembersRequest memberDto) {
        HashMap<String, Object> map = new HashMap<>();
        Members members = StaticSecurityUtil.getMembers();
        Members corfirmMembers = membersRepository.findByEmail(members.getEmail());
        MembersPrivate membersPrivate = membersPrivateRepository.findByUserUniqId(corfirmMembers.getUserUniqId());
        boolean isUserCertified = true;
        if (!membersPrivate.getUserName().equals(memberDto.getUserName())) {
            isUserCertified = false;
        }
        if (!membersPrivate.getBirth().equals(memberDto.getBirth())) {
            isUserCertified = false;
        }

        if (isUserCertified) {
            map.put("isChanged", true);
            MembersPrivateDto membersPrivateDto = modelMapper.map(membersPrivate, MembersPrivateDto.class);
            membersPrivateDto.setPhoneNumber(memberDto.getPhoneNumber());
            membersPrivateRepository.save(membersPrivateDto.toEntity());
            map.put("isChanged", true);
        } else {
            map.put("isChanged", false);
        }
        return map;
    }

    public Map<String, Object> myAccountDrop(MembersRequest memberDto) {
        HashMap<String, Object> map = new HashMap<>();
        boolean isCertified = true;
        Members members = StaticSecurityUtil.getMembers();
        List<MembersRole> roleList = members.getRole();
        boolean isAdminOrManager = false;
        for (MembersRole role : roleList) {
            if (role.getRoleName().equals("ADMIN") || role.getRoleName().equals("MANAGER"))
                isAdminOrManager = true; // 관리자는 넘버링크 문제 모두 수정가능
        }

        if (isAdminOrManager) {
            map.put("existMsg", true);
            map.put("serverMsg", "매니저 또는 관리자 계정은 탈퇴가 불가능합니다.");
            return map;
        }

        isCertified = memberDto.getEmail().equals(members.getEmail());
        if (!isCertified) {
            map.put("existMsg", true);
            map.put("serverMsg", "계정 정보가 올바르지 않습니다.");
            return map;
        }
        Members corfirmMembers = membersRepository.findByEmail(members.getEmail());
        isCertified = authPasswordEncoder.matches(memberDto.getPassword(), corfirmMembers.getPassword());
        if (!isCertified) {
            map.put("existMsg", true);
            map.put("serverMsg", "계정 정보가 올바르지 않습니다.");
            return map;
        }

        MembersPrivate membersPrivate = membersPrivateRepository.findByUserUniqId(corfirmMembers.getUserUniqId());
        if (!membersPrivate.getUserName().equals(memberDto.getUserName())
                || !membersPrivate.getPhoneNumber().equals(memberDto.getPhoneNumber())
                || !membersPrivate.getBirth().equals(memberDto.getBirth())) {
            map.put("existMsg", true);
            map.put("serverMsg", "가입하신 사용자 정보와 입력하신 휴대폰 본인인증 정보가 다릅니다.");
            return map;
        }

        MembersRequest corfirmMembersRequest = modelMapper.map(corfirmMembers, MembersRequest.class);
        corfirmMembersRequest.setHumanStatus(2);
        corfirmMembersRequest.setLastLoginDate(LocalDateTime.now());
        map.put("isSuccess", true);
        membersRepository.save(corfirmMembersRequest.toEntity());
        return map;
    }

    public Map<String, Object> myContentsCheckForHwpDown(String contentsNo) {
        Members members = StaticSecurityUtil.getMembers();
        List<MembersRole> roleList = members.getRole();
        boolean isTopTester = false;
        for (MembersRole role : roleList) {
            if (role.getRoleName().equals("TOP_TESTER"))
                isTopTester = true; // 관리자는 넘버링크 문제 모두 수정가능
        }
        MembersProfile memProfile = membersProfileRepository.findByUserUniqId(members.getUserUniqId());

        HashMap<String, Object> map = new HashMap<>();
        // 이미 3회 이상 다운 받은 경우 다운 불가
        if (!isTopTester && memProfile.getHwpDownCnt() >= 3) {
            map.put("existMsg", true);
            map.put("contentsNo", -1);
            map.put("serverMsg", "일일 한글 파일 다운로드 및 업로드 허용 횟수 3회를 모두 사용하셨습니다.");
            return map;
        }

        if (contentsNo.equals("all")) {
            return map;
        }

        int contentsNum = Integer.parseInt(contentsNo);

        UUID conOwnUuid = mathContentsRepository.findOnlyUuidByContentsNo(contentsNum);
        // 자기자신의 문제 아닌 경우 다운 불가
        if (!conOwnUuid.equals(members.getUserUniqId())) {
            map.put("existMsg", true);
            map.put("contentsNo", -1);
            map.put("serverMsg", "본인의 문제가 아닌 경우 다운이 불가능합니다.");
            return map;
        }

        map.put("contentsNo", contentsNum);
        return map;
    }

    public String connectPyServerForMakeHwp(String path, HwpJsonStrRequest hwpJsonDto) throws IOException {
        Members members = StaticSecurityUtil.getMembers();
        MembersProfile memProfile = membersProfileRepository.findByUserUniqId(members.getUserUniqId());
        ClientConnect cc = new ClientConnect(customsocketip);
        String newFileName = cc.getFile(path, hwpJsonDto.getJsonString());
        cc.closeConnections();
        // hwp 다운 카운트 +1 증가
        membersProfileRepository.changeHwpDownCnt(memProfile.getUserUniqId(), memProfile.getHwpDownCnt() + 1);
        return newFileName;
    }

    public Map<String, Object> registerMemberProfile(int profileType) {
        Members members = StaticSecurityUtil.getMembers();
        int isSuccess = membersProfileRepository.registerProfileType(members.getUserUniqId(), profileType);
        HashMap<String, Object> map = new HashMap<String, Object>();
        if (isSuccess == 1) {
            map.put("isSuccess", true);
        } else {
            map.put("isSuccess", false);
        }
        return map;
    }

    @Transactional
    public Map<String, Object> createEmailIdCode(String email) {
        HashMap<String, Object> map = new HashMap<>();
        Random rand = new Random();
        String idCode = "";
        for (int i = 0; i < 6; i++)
            idCode += (rand.nextInt(8) + 1);
        EmailIdCodeDto emailIdCodeDto = new EmailIdCodeDto();
        emailIdCodeDto.setEmail(email);
        emailIdCodeDto.setIdCode(authPasswordEncoder.encode(idCode));

        EmailIdCode emailIdCode = emailIdCodeRepository.save(emailIdCodeDto.toEntity());
        boolean isSuccess = entityManager.contains(emailIdCode);
        map.put("isSuccess", isSuccess);
        if (isSuccess) {
            // 메일 전송
            String contents = "<div>안녕하세요. N명의수학입니다.<br/> 요청하신 회원가입 이메일 인증코드는 아래와 같습니다.</div> <div style='margin:\"10px 0\"font-family:\"Malgun Gothic\";font-size:\"20px\"; '>"
                    + idCode + "</div>위 인증코드는 3분간 유효합니다.";
            try {
                commonUtil.sendMail(email, "[N명의수학] 이메일 인증코드 안내", contents);
            } catch (MessagingException e) {
                map.put("isSuccess", false);
                map.put("failReason", "이메일 전송 실패");
                return map;
            }

        } else
            map.put("failReason", "DB 저장 실패");
        return map;
    }

    // 프로필별 가입자 수
    public List<CustomTenFieldDto> statisticMembersCntByProfileType() {
        List<String> roleNameList = new ArrayList<>();
        roleNameList.add("ADMIN");
        roleNameList.add("MANAGER");
        List<MembersRole> membersRoleList = membersRoleRepository.findByRoleNameIn(roleNameList);
        List<UUID> uuidList = new ArrayList<>();
        for (MembersRole membersRole : membersRoleList) {
            UUID uuid = membersRole.getUserUniqId();
            uuidList.add(uuid);
        }
        List<CustomTenFieldDto> list = new ArrayList<>();
        CustomTenFieldDto customHeaderDto = new CustomTenFieldDto("미등록", "원장", "강사", "교사", "학부모", "학생", "기타", null,
                null, null);
        int cnt0 = membersProfileRepository.countByProfileTypeAndUserUniqIdNotIn(0, uuidList);
        int cnt1 = membersProfileRepository.countByProfileTypeAndUserUniqIdNotIn(1, uuidList);
        int cnt2 = membersProfileRepository.countByProfileTypeAndUserUniqIdNotIn(2, uuidList);
        int cnt3 = membersProfileRepository.countByProfileTypeAndUserUniqIdNotIn(3, uuidList);
        int cnt4 = membersProfileRepository.countByProfileTypeAndUserUniqIdNotIn(4, uuidList);
        int cnt5 = membersProfileRepository.countByProfileTypeAndUserUniqIdNotIn(5, uuidList);
        int cnt6 = membersProfileRepository.countByProfileTypeAndUserUniqIdNotIn(6, uuidList);
        CustomTenFieldDto customBodyDto = new CustomTenFieldDto(cnt0, cnt1, cnt2, cnt3, cnt4, cnt5, cnt6, null, null,
                null);
        list.add(customHeaderDto);
        list.add(customBodyDto);
        return list;
    }
    // todo jpql 정상적이지 않음 수정 필요(java 17 migration 과정 중)
//
//    // 시간대별 가입자 수
//    public List<CustomTenFieldDto> statisticMembersCntGrouBySignupDateHour() {
//        List<CustomTenFieldDto> list = membersRepository.statisticMembersCntGrouBySignupDateHour();
//        CustomTenFieldDto customHeaderDto = new CustomTenFieldDto("00시 ~ 03시", "03시 ~ 06시", "06시 ~ 09시", "09시 ~ 12시",
//                "12시 ~ 15시", "15시 ~ 18시", "18시 ~ 21시", "21시 ~ 24시", null, null);
//        list.add(0, customHeaderDto);
//        return list;
//    }

    // 날짜별 가입자 수
//    public List<CustomTenFieldDto> statisticMembersCntBySignupDate() {
//        List<String> roleNameList = new ArrayList<>();
//        roleNameList.add("ADMIN");
//        roleNameList.add("MANAGER");
//        List<MembersRole> membersRoleList = membersRoleRepository.findByRoleNameIn(roleNameList);
//        List<UUID> uuidList = new ArrayList<>();
//        for (MembersRole membersRole : membersRoleList) {
//            UUID uuid = membersRole.getUserUniqId();
//            uuidList.add(uuid);
//        }
//        // 전체
//        int totalCnt = membersRepository.countBySignupDateAfterAndUserUniqIdNotIn(LocalDateTime.of(2022, 4, 1, 0, 0, 0),
//                uuidList);
//        // 최근 한달 가입자수
//        int lastOneMonthCnt = membersRepository.countBySignupDateAfterAndUserUniqIdNotIn(
//                LocalDateTime.now().minusMonths(1).with(LocalTime.MIN), uuidList);
//        // 최근 일주일 가입자수
//        int lastOneWeekCnt = membersRepository.countBySignupDateAfterAndUserUniqIdNotIn(
//                LocalDateTime.now().minusWeeks(1).with(LocalTime.MIN), uuidList);
//        // 어제 가입자수
//        int yesterDayCnt = membersRepository.countBySignupDateAfterAndUserUniqIdNotIn(
//                LocalDateTime.now().minusDays(1).with(LocalTime.MIN), uuidList);
//        // 오늘 가입자수
//        int todayCnt = membersRepository
//                .countBySignupDateAfterAndUserUniqIdNotIn(LocalDateTime.now().with(LocalTime.MIN), uuidList);
//
//        yesterDayCnt = yesterDayCnt - todayCnt;
//
//        CustomTenFieldDto customHeaderDto = new CustomTenFieldDto("전체", "최근 한달", "최근 일주일", "어제", "오늘", null, null, null,
//                null, null);
//        CustomTenFieldDto customBodyDto = new CustomTenFieldDto(totalCnt, lastOneMonthCnt, lastOneWeekCnt, yesterDayCnt,
//                todayCnt, null, null, null, null, null);
//        List<CustomTenFieldDto> list = new ArrayList<>();
//        list.add(customHeaderDto);
//        list.add(customBodyDto);
//        return list;
//    }

    // 프로필에 따른 시간대별 가입자수
//    public List<CustomTenFieldDto> statisticMembersByHourGrouByProfileType() {
//        List<CustomTenFieldDto> list = membersRepository.statisticMembersByHourGrouByProfileType();
//        CustomTenFieldDto customHeaderDto = new CustomTenFieldDto("프로필", "00시 ~ 03시", "03시 ~ 06시", "06시 ~ 09시",
//                "09시 ~ 12시", "12시 ~ 15시", "15시 ~ 18시", "18시 ~ 21시", "21시 ~ 24시", null);
//        list.add(0, customHeaderDto);
//        return list;
//    }

    // 나이대별 회원가입자 수
    public List<CustomTenFieldDto> statisticMembersByAge() {
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
        int notAuthorizedCnt = 0;
        for (CustomTenFieldDto yearCntList : list) {
            if (yearCntList.getNbCol1() == null) {
                notAuthorizedCnt = Integer.parseInt(yearCntList.getNbCol2().toString());
                continue;
            }
            int memberBirthYear = Integer.parseInt(yearCntList.getNbCol1().toString());
            int memberBirthCnt = Integer.parseInt(yearCntList.getNbCol2().toString());

            int memberAge = 0;
            if (memberBirthYear > year) {
                memberBirthYear = memberBirthYear + 1900;
            } else {
                memberBirthYear = memberBirthYear + 2000;
            }
            memberAge = Integer.parseInt(fullYearStr) - memberBirthYear + 1;
            if (memberAge < 20) {
                teenAgersCnt += memberBirthCnt;
            } else if (memberAge < 30) {
                twoZeroMembersCnt += memberBirthCnt;
            } else if (memberAge < 40) {
                threeZeroMembersCnt += memberBirthCnt;
            } else if (memberAge < 50) {
                fourZeroMembersCnt += memberBirthCnt;
            } else if (memberAge < 60) {
                fiveZeroMembersCnt += memberBirthCnt;
            } else if (memberAge >= 60) {
                overSixZeroMembersCnt += memberBirthCnt;
            }
        }
        CustomTenFieldDto customHeaderDto = new CustomTenFieldDto("미성년자", "20대", "30대", "40대", "50대", "60대 이상", "미인증",
                null, null, null);
        CustomTenFieldDto customBodyrDto = new CustomTenFieldDto(teenAgersCnt, twoZeroMembersCnt, threeZeroMembersCnt,
                fourZeroMembersCnt, fiveZeroMembersCnt, overSixZeroMembersCnt, notAuthorizedCnt, null, null, null);
        List<CustomTenFieldDto> newList = new ArrayList<>();
        newList.add(customHeaderDto);
        newList.add(customBodyrDto);
        return newList;
    }
// todo jpql 정상적이지 않음 수정 필요(java 17 migration 과정 중)
    // 월별 가입자 접속자 통계
//    public List<CustomTenFieldDto> monthlyMembersCnt() {
//        // 월별 가입자
//        List<CustomTenFieldDto> list = membersRepository.countMembersGroupBySysCreateDateMonth();
//        CustomTenFieldDto customHeaderDto = new CustomTenFieldDto(list.get(0).getNbCol1(), list.get(1).getNbCol1(),
//                list.get(2).getNbCol1(), list.get(3).getNbCol1(), list.get(4).getNbCol1(), list.get(5).getNbCol1(),
//                list.size() > 6 ? list.get(6).getNbCol1() : null, list.size() > 7 ? list.get(7).getNbCol1() : null,
//                list.size() > 8 ? list.get(8).getNbCol1() : null, list.size() > 9 ? list.get(9).getNbCol1() : null);
//        CustomTenFieldDto customBodyDto = new CustomTenFieldDto(list.get(0).getNbCol2(), list.get(1).getNbCol2(),
//                list.get(2).getNbCol2(), list.get(3).getNbCol2(), list.get(4).getNbCol2(), list.get(5).getNbCol2(),
//                list.size() > 6 ? list.get(6).getNbCol2() : null, list.size() > 7 ? list.get(7).getNbCol2() : null,
//                list.size() > 8 ? list.get(8).getNbCol2() : null, list.size() > 9 ? list.get(9).getNbCol2() : null);
//        List<CustomTenFieldDto> list2 = new ArrayList<>();
//        list2.add(0, customBodyDto);
//        list2.add(0, customHeaderDto);
//        return list2;
//    }

//    // 일일 접속자 통계
//    public List<CustomTenFieldDto> statisticMembersCntByMonthly() {
//
//        StringBuffer queryString = new StringBuffer();
//        queryString.append("SELECT DATE_FORMAT(A.login_time,'%Y년 %m월') as nbCol1, count(*) as nbCol2,");
//        queryString.append(
//                " 0 as nbCol3,0 as nbCol4, 0 as nbCol5, 0 as nbCol6, 0 as nbCol7, 0 as nbCol8, 0 as nbCol9, 0 as nbCol10");
//        queryString.append(" FROM ");
//        queryString.append(" (SELECT ");
//        queryString.append(" user_uniq_id, DATE_FORMAT(login_time,'%Y-%m-%d') as login_time");
//        queryString.append(" FROM access_log_info");
//        queryString.append(" GROUP BY DATE_FORMAT(login_time,'%Y-%m-%d'), user_uniq_id) as A");
//        queryString.append(" where A.login_time>='2023-04-01'");
//        queryString.append(
//                " and A.user_uniq_id not in (SELECT mr.user_uniq_id FROM members_role mr where mr.role_name='ADMIN' or mr.role_name='MANAGER')");
//        queryString.append(" GROUP BY DATE_FORMAT(A.login_time,'%Y-%m')");
//        queryString.append(" ORDER BY DATE_FORMAT(A.login_time,'%Y-%m') ASC");
//        Query query = (Query) entityManager.createNativeQuery(queryString.toString());
//        JpaResultMapper result = new JpaResultMapper();
//        List<CustomTenFieldDto> list = result.list(query, CustomTenFieldDto.class);
//        CustomTenFieldDto customHeaderDto = new CustomTenFieldDto(list.size() != 0 ? list.get(0).getNbCol1() : null,
//                list.size() > 1 ? list.get(1).getNbCol1() : null, list.size() > 2 ? list.get(2).getNbCol1() : null,
//                list.size() > 3 ? list.get(3).getNbCol1() : null, list.size() > 4 ? list.get(4).getNbCol1() : null,
//                list.size() > 5 ? list.get(5).getNbCol1() : null, list.size() > 6 ? list.get(6).getNbCol1() : null,
//                list.size() > 7 ? list.get(7).getNbCol1() : null, list.size() > 8 ? list.get(8).getNbCol1() : null,
//                list.size() > 9 ? list.get(9).getNbCol1() : null);
//        CustomTenFieldDto customBodyDto = new CustomTenFieldDto(list.size() != 0 ? list.get(0).getNbCol2() : null,
//                list.size() > 1 ? list.get(1).getNbCol2() : null, list.size() > 2 ? list.get(2).getNbCol2() : null,
//                list.size() > 3 ? list.get(3).getNbCol2() : null, list.size() > 4 ? list.get(4).getNbCol2() : null,
//                list.size() > 5 ? list.get(5).getNbCol2() : null, list.size() > 6 ? list.get(6).getNbCol2() : null,
//                list.size() > 7 ? list.get(7).getNbCol2() : null, list.size() > 8 ? list.get(8).getNbCol2() : null,
//                list.size() > 9 ? list.get(9).getNbCol2() : null);
//        List<CustomTenFieldDto> list2 = new ArrayList<>();
//        list2.add(0, customBodyDto);
//        list2.add(0, customHeaderDto);
//        return list2;
//    }

    // 일일 접속자 통계
    public List<CustomTenFieldDto> statisticMembersCntByLoginDate() {
        // 최근 한달 접속자
        int lastOneMonthCnt = accessLogInfoRepository.countDistinctUserUniqIdByLoginTimeBetweenAndUserUniqIdNotIn(
                LocalDateTime.now().minusMonths(1).with(LocalTime.MIN), LocalDateTime.now().with(LocalTime.MAX));
        // 최근 일주일 접속자
        int lastOneWeekCnt = accessLogInfoRepository.countDistinctUserUniqIdByLoginTimeBetweenAndUserUniqIdNotIn(
                LocalDateTime.now().minusWeeks(1).with(LocalTime.MIN), LocalDateTime.now().with(LocalTime.MAX));
        // 어제 접속자
        int yesterDayCnt = accessLogInfoRepository.countDistinctUserUniqIdByLoginTimeBetweenAndUserUniqIdNotIn(
                LocalDateTime.now().minusDays(1).with(LocalTime.MIN),
                LocalDateTime.now().minusDays(1).with(LocalTime.MAX));
        // 오늘 접속자
        int todayCnt = accessLogInfoRepository.countDistinctUserUniqIdByLoginTimeBetweenAndUserUniqIdNotIn(
                LocalDateTime.now().with(LocalTime.MIN), LocalDateTime.now().with(LocalTime.MAX));

        CustomTenFieldDto customHeaderDto = new CustomTenFieldDto("최근 한달", "최근 일주일", "어제", "오늘", null, null, null, null,
                null, null);
        CustomTenFieldDto customBodyDto = new CustomTenFieldDto(lastOneMonthCnt, lastOneWeekCnt, yesterDayCnt, todayCnt,
                null, null, null, null, null, null);
        List<CustomTenFieldDto> list = new ArrayList<>();
        list.add(customHeaderDto);
        list.add(customBodyDto);
        return list;
    }
// todo jpql 정상적이지 않음 수정 필요(java 17 migration 과정 중)
    // 월별 가입자 재로그인 비율
//    public List<CustomTenFieldDto> reLoginRatioPerMonth() {
//        List<String> roleNameList = new ArrayList<>();
//        roleNameList.add("ADMIN");
//        roleNameList.add("MANAGER");
//        List<MembersRole> membersRoleList = membersRoleRepository.findByRoleNameIn(roleNameList);
//        List<UUID> uuidList = new ArrayList<>();
//        for (MembersRole membersRole : membersRoleList) {
//            UUID uuid = membersRole.getUserUniqId();
//            uuidList.add(uuid);
//        }
//
//        // 최근 1년
//        LocalDateTime productDate = LocalDateTime.of(2022, 11, 1, 0, 0, 0);
//        LocalDateTime compareDate = LocalDateTime.now().with(LocalTime.MIN).withDayOfMonth(1).minusMonths(12);
//        LocalDateTime currentDate = LocalDateTime.now().with(LocalTime.MIN).plusMonths(1).withDayOfMonth(2);
//
//        int idx = 0;
//        CustomTenFieldDto customHeaderDto = new CustomTenFieldDto();
//        CustomTenFieldDto customBodyDto = new CustomTenFieldDto();
//        List<CustomTenFieldDto> list = new ArrayList<>();
//        while (compareDate.plusMonths(1).isBefore(currentDate)) {
//            if (compareDate.plusMonths(1).isAfter(productDate)) { // 출시 이후에 날짜에 대하여 통계
//                String compareDateStr = compareDate.format(DateTimeFormatter.ofPattern("yyyy년 MM월"));
//                Long reLoginRatio = membersRepository.reLoginRatioPerMonth(compareDate.plusMonths(1), compareDate,
//                        uuidList);
//                idx++;
//                switch (idx) {
//                    case 1:
//                        customHeaderDto.setNbCol1(compareDateStr);
//                        customBodyDto.setNbCol1(reLoginRatio);
//                        break;
//                    case 2:
//                        customHeaderDto.setNbCol2(compareDateStr);
//                        customBodyDto.setNbCol2(reLoginRatio);
//                        break;
//                    case 3:
//                        customHeaderDto.setNbCol3(compareDateStr);
//                        customBodyDto.setNbCol3(reLoginRatio);
//                        break;
//                    case 4:
//                        customHeaderDto.setNbCol4(compareDateStr);
//                        customBodyDto.setNbCol4(reLoginRatio);
//                        break;
//                    case 5:
//                        customHeaderDto.setNbCol5(compareDateStr);
//                        customBodyDto.setNbCol5(reLoginRatio);
//                        break;
//                    case 6:
//                        customHeaderDto.setNbCol6(compareDateStr);
//                        customBodyDto.setNbCol6(reLoginRatio);
//                        break;
//                    case 7:
//                        customHeaderDto.setNbCol7(compareDateStr);
//                        customBodyDto.setNbCol7(reLoginRatio);
//                        break;
//                    case 8:
//                        customHeaderDto.setNbCol8(compareDateStr);
//                        customBodyDto.setNbCol8(reLoginRatio);
//                        break;
//                    case 9:
//                        customHeaderDto.setNbCol9(compareDateStr);
//                        customBodyDto.setNbCol9(reLoginRatio);
//                        break;
//                    case 10:
//                        customHeaderDto.setNbCol10(compareDateStr);
//                        customBodyDto.setNbCol10(reLoginRatio);
//                        break;
//                }
//            }
//
//            compareDate = compareDate.plusMonths(1);
//        }
//        list.add(customHeaderDto);
//        list.add(customBodyDto);
//        return list;
//    }
//
//    // 가입 사용자 조회
//    public List<CustomTenFieldDto> lastSignupUserLimit() {
//        Pageable topHundreds = PageRequest.of(0, 100);
//        List<CustomTenFieldDto> list = membersRepository.lastSignupUserLimit(topHundreds);
//
//        List<CustomTenFieldDto> newList = new ArrayList<>();
//        for (CustomTenFieldDto customDto : list) {
//            CustomTenFieldDto newCustomDto = new CustomTenFieldDto();
//            newCustomDto.setNbCol1(customDto.getNbCol1());
//            if (customDto.getNbCol2() == null) {
//                newCustomDto.setNbCol2("미인증");
//            } else {
//                String birthYearStr = ((String) customDto.getNbCol2()).substring(0, 2);
//                int birthYear = Integer.parseInt(birthYearStr);
//                int currentYear = LocalDateTime.now().getYear();
//                int currentYearLastTwoChar = Integer.parseInt(Integer.toString(currentYear).substring(2, 4));
//                if (birthYear <= currentYearLastTwoChar) {
//                    birthYear += 2000;
//                } else {
//                    birthYear += 1900;
//                }
//                int age = currentYear - birthYear + 1;
//                newCustomDto.setNbCol2(age);
//            }
//
//            newCustomDto.setNbCol3(customDto.getNbCol3());
//
//            String loginDate = ((LocalDateTime) customDto.getNbCol4())
//                    .format(DateTimeFormatter.ofPattern("yyyy년-MM월-dd일 HH시:mm분"));
//            String signupDate = ((LocalDateTime) customDto.getNbCol5())
//                    .format(DateTimeFormatter.ofPattern("yyyy년-MM월-dd일 HH시:mm분"));
//            newCustomDto.setNbCol4(loginDate);
//            newCustomDto.setNbCol5(signupDate);
//            newList.add(newCustomDto);
//        }
//
//        CustomTenFieldDto customHeaderDto = new CustomTenFieldDto("이메일", "나이", "프로필", "최근 로그인 날짜", "가입 날짜", null, null,
//                null, null, null);
//        newList.add(0, customHeaderDto);
//        return newList;
//    }

}
