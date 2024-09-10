package com.kamcci.numberbox.todo;

import com.kamcci.numberbox.members.entity.Members;
import com.kamcci.numberbox.members.entity.MembersRole;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class StaticSecurityUtil {

    // todo 나중에 없애야함
    public static Members getMembers() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<GrantedAuthority> authorities = (List<GrantedAuthority>) authentication.getAuthorities();
        List<MembersRole> roleList = new ArrayList<>();
        for (GrantedAuthority authority : authorities) {
            MembersRole role = new MembersRole();
            role.setRoleName(authority.getAuthority());
            roleList.add(role);
        }
        System.out.println(authentication.getDetails());
        Members members = new Members();
        members.setEmail((String) authentication.getPrincipal());
        members.setUserUniqId((UUID) authentication.getDetails());
        members.setMemberRole(roleList);
        return members;
    }

}
