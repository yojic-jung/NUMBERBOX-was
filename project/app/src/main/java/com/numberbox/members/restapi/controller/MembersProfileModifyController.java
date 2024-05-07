package com.numberbox.members.restapi.controller;

import com.numberbox.members.dto.MembersProfileDto;
import com.numberbox.members.service.MembersService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@RestController
public class MembersProfileModifyController {

    private final MembersService membersService;

    public MembersProfileModifyController(MembersService membersService) {
        this.membersService = membersService;
    }

    @GetMapping(value = "/registerMemberProfile")
    public Map<String, Object> registerMemberProfile(HttpServletRequest request) {
        String profileType = request.getParameter("profileType");
        return membersService.registerMemberProfile(Integer.parseInt(profileType));
    }

    @PostMapping("/registerProfileImg")
    public Map<String, Object> registerProfileImg(@ModelAttribute MembersProfileDto membersProfileDto,
                                                  HttpServletRequest request) throws IllegalStateException, IOException {
        String path = request.getSession().getServletContext().getRealPath("/static"); // 임시용, 배포 이후 프로젝트 바깥 경로로 설정하는게
        // 좋음(배포용 개발용 따로 관리 필요)
        return membersService.registerProfileImg(membersProfileDto, path);
    }

    @GetMapping("/changeNickname")
    public Map<String, Object> changeNickname(@RequestParam String nickname) throws IllegalStateException {
        return membersService.changeNickname(nickname);
    }

}
