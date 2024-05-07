package com.numberbox.members.restapi.controller;

import com.numberbox.members.entity.Members;
import com.numberbox.members.restapi.dto.request.MembersRequest;
import com.numberbox.members.service.MembersService;
import com.numberbox.todo.StaticSecurityUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class MembersReadController {

    private final MembersService membersService;

    public MembersReadController(MembersService membersService) {
        this.membersService = membersService;
    }

    @PostMapping(value = "/findEmail")
    public Object findEmail(MembersRequest memberDto) {
        return membersService.findEmail(memberDto);
    }

    @GetMapping(value = "/findPassword")
    public Object findPassWd(HttpServletRequest request) throws MessagingException {
        String email = request.getParameter("email");
        return membersService.findPassword(request, email);
    }

    @GetMapping(value = "/takeMyEmail")
    public Map<String, String> takeMyEmail() {
        Members members = StaticSecurityUtil.getMembers();
        Map<String, String> map = new HashMap<>();
        map.put("email", members.getEmail());
        return map;
    }
// todo jpql 정상적이지 않음 수정 필요(java 17 migration 과정 중)
//    @GetMapping(value = "/takeMembersStatistic")
//    public Map<String, Object> takeMembersStatistic() {
//        Map<String, Object> map = new HashMap<String, Object>();
//        // 가입자 정보 조회
//        List<CustomTenFieldDto> membersInfo = membersService.lastSignupUserLimit();
//
//        // 날짜별 가입자 수
//        List<CustomTenFieldDto> membersCntBySignupDate = membersService.statisticMembersCntBySignupDate();
//        // 프로필별 가입자수
//        List<CustomTenFieldDto> membersCntByProfile = membersService.statisticMembersCntByProfileType();
//        // 시간대별 가입자 수
//        List<CustomTenFieldDto> membersCntByHourPeriod = membersService.statisticMembersCntGrouBySignupDateHour();
//        // 프로필에 따른 시간대별 가입자수
//        List<CustomTenFieldDto> membersCntByProAndHourPeriod = membersService.statisticMembersByHourGrouByProfileType();
//        // 나이대별 회원가입자 수
//        List<CustomTenFieldDto> membersCntByAge = membersService.statisticMembersByAge();
//        // 일일 접속자 통계
//        List<CustomTenFieldDto> dailyLoginUserCnt = membersService.statisticMembersCntByLoginDate();
//        // 월별 접속자 통계
////        List<CustomTenFieldDto> monthlyLoginUserCnt = membersService.statisticMembersCntByMonthly();
//        // 월별 가입자
//        List<CustomTenFieldDto> monthlyMembersCnt = membersService.monthlyMembersCnt();
//        // 월별 가입자 재로그인 비율
//        List<CustomTenFieldDto> reLoginRatioPerMonth = membersService.reLoginRatioPerMonth();
//
//        map.put("membersInfo", membersInfo);
//        map.put("membesrCntBySignupDate", membersCntBySignupDate);
//        map.put("membesrCntByProfile", membersCntByProfile);
//        map.put("membesrCntByHourPeriod", membersCntByHourPeriod);
//        map.put("membesrCntByProAndHourPeriod", membersCntByProAndHourPeriod);
//        map.put("membersCntByAge", membersCntByAge);
//        map.put("monthlyMembersCnt", monthlyMembersCnt);
//        map.put("dailyLoginUserCnt", dailyLoginUserCnt);
////        map.put("monthlyLoginUserCnt", monthlyLoginUserCnt);
//        map.put("reLoginRatioPerMonth", reLoginRatioPerMonth);
//        return map;
//    }
}
