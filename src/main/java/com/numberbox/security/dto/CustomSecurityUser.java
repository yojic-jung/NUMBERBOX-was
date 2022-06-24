package com.numberbox.security.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.numberbox.members.entity.Members;
import com.numberbox.members.entity.MembersRole;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomSecurityUser extends User {

	
	private static final long serialVersionUID = 1L;

	private static final String ROLE_PREFIX = "ROLE_";

    private Members members;
    public CustomSecurityUser(Members members) {
        super(members.getEmail(), members.getPassword(), enableCheck(members.getRole()), true, true, true, makeGrantedeAuth(members.getRole()) );
       
        this.members = members;
    }

    private static List<GrantedAuthority> makeGrantedeAuth(List<MembersRole> roles) {
        List<GrantedAuthority> list = new ArrayList<>();
        roles.forEach(membersRole -> 
            list.add(new SimpleGrantedAuthority(ROLE_PREFIX + membersRole.getRoleName())));
        return list;
    }
    
    
    //enabled의 경우 컬렉션 객체 타입을 갖지 못함(User.class), 하나라도 enabled가 true라면 true로 구현 
    private static boolean enableCheck(List<MembersRole> roles) {
    	boolean isEnabled = false;
    	for(MembersRole role : roles) {
    		if(role.isEnabled()) {
    			isEnabled = true;
    		}
    	}
        return isEnabled;
    }
    
}