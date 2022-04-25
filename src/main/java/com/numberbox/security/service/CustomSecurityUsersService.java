package com.numberbox.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.numberbox.members.entity.Members;
import com.numberbox.members.entity.MembersNo;
import com.numberbox.members.repository.MembersNoRepository;
import com.numberbox.members.repository.MembersRepository;
import com.numberbox.security.dto.CustomSecurityUser;

@Service
public class CustomSecurityUsersService implements UserDetailsService {

    @Autowired
    MembersRepository memberRepository;
    @Autowired
    MembersNoRepository membersNoRepository;
    
    @Override
    @Transactional
    public User loadUserByUsername(String email) throws UsernameNotFoundException {
        Members members = memberRepository.findByEmail(email);
        MembersNo membersNo = membersNoRepository.findByUserUniqId(members.getUserUniqId());
        User user = new CustomSecurityUser(members, membersNo);
        return user;
    }
}