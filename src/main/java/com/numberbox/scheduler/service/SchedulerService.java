package com.numberbox.scheduler.service;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.numberbox.common.util.CommonUtil;
import com.numberbox.mathdocs.repository.MathDocsPaperRepository;
import com.numberbox.mathinfo.entity.MathContents;
import com.numberbox.mathinfo.entity.MathResource;
import com.numberbox.mathinfo.entity.MathResourceImg;
import com.numberbox.mathinfo.repository.MathConLikeInfoRepository;
import com.numberbox.mathinfo.repository.MathConRepoInfoRepository;
import com.numberbox.mathinfo.repository.MathContentsRepository;
import com.numberbox.mathinfo.repository.MathResourceCateRepository;
import com.numberbox.mathinfo.repository.MathResourceImgRepository;
import com.numberbox.mathinfo.repository.MathResourceRepository;
import com.numberbox.members.dto.MembersDto;
import com.numberbox.members.dto.MembersPrivateDto;
import com.numberbox.members.dto.MembersRoleDto;
import com.numberbox.members.entity.Members;
import com.numberbox.members.entity.MembersPrivate;
import com.numberbox.members.entity.MembersProfile;
import com.numberbox.members.entity.MembersRole;
import com.numberbox.members.repository.MembersFollowInfoRepository;
import com.numberbox.members.repository.MembersPrivateRepository;
import com.numberbox.members.repository.MembersProfileRepository;
import com.numberbox.members.repository.MembersRepository;
import com.numberbox.members.repository.MembersRoleRepository;

@Service
public class SchedulerService {

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
	private MathConLikeInfoRepository mathConLikeInfoRepository;
	@Autowired
	private MathConRepoInfoRepository mathConRepoInfoRepository;
	@Autowired
	private MathDocsPaperRepository mathDocsPaperRepository;
	@Autowired
	private MathResourceRepository mathResourceRepository;
	@Autowired
	MathResourceCateRepository mathResourceCateRepository;
	@Autowired
	private MathResourceImgRepository mathResourceImgRepository;
	
	@Autowired
	ModelMapper modelMapper;
	@Autowired
	ServletContext context; 
	
	
	@Transactional
	public void tmpPassChange() {
		//tmpPassword가 1인 값 새로운 비밀번호로 변경
		int tmpPasswordLength = membersRepository.countByTmpPassword(true);
		int loopCnt = tmpPasswordLength/10000;
		if(tmpPasswordLength%10000 > 0) {
			loopCnt = loopCnt+1;
		}
		for(int i=0; i<loopCnt; i++) {
			List<Members> membersList = membersRepository.findTop10000ByTmpPassword(true);
			List<Members> targetMembersList = new ArrayList<>();
			for(Members members : membersList) {
				MembersDto membersDto = modelMapper.map(members, MembersDto.class);
				membersDto.setTmpPassword(false);
				membersDto.setPassword(bCryptPasswordEncoder.encode(CommonUtil.makeRandomPassword()));
				targetMembersList.add(membersDto.toEntity());
			}
			membersRepository.saveAll(targetMembersList);
		}
	}
	
	@Transactional
	public void dropAccount() {
		List<Members> membersList = membersRepository.findByHumanStatusAndLastLoginDateLessThan(2, LocalDateTime.now().minusDays(14));
		//파일 삭제, 프로필 이미지(membersProfile), ppt파일 및 이미지(math_resource, math_resource_img)
		for(Members members : membersList) {
			//관리자, 매니저 건너뛰기
			List<MembersRole> roleList = membersRoleRepository.findByUserUniqId(members.getUserUniqId());
			boolean isAmdinOrManager = false;
			for(MembersRole role : roleList) {
				if(role.getRoleName().equals("ADMIN") || role.getRoleName().equals("MANAGER")) isAmdinOrManager=true;
			}
			if(isAmdinOrManager) {
				continue;
			}
			
			//프로필 이미지 삭제
			MembersProfile profile = membersProfileRepository.findByUserUniqId(members.getUserUniqId());
			if(profile.getProfileImgName() != null) {
				File profileImgfile = new File(context.getRealPath("/static")+profile.getProfileImgPath().replace("/webapp/static", "")+profile.getProfileImgName());
				profileImgfile.delete();
			}
			
			List<MathResource> mathResourceList = mathResourceRepository.findByUserUniqId(members.getUserUniqId());
			for(MathResource mathResource : mathResourceList) {
				if(mathResource.getImgName() != null) {
					File resourceImgFile = new File(context.getRealPath("/static")+mathResource.getImgPath().replace("/webapp/static", "")+mathResource.getImgName());
					resourceImgFile.delete();
				}
				
				if(mathResource.getPptName() != null) {
					File resourcePptFile = new File(context.getRealPath("/static")+mathResource.getPptPath().replace("/webapp/static", "")+mathResource.getPptName());
					resourcePptFile.delete();
				}
				
				for(MathResourceImg resourceImg : mathResourceImgRepository.findByResourceNo(mathResource.getResourceNo())) {
					if(resourceImg.getImgName() != null) {
						File resourcePptImgFile = new File(context.getRealPath("/static")+resourceImg.getImgPath().replace("/webapp/static", "")+resourceImg.getImgName());
						resourcePptImgFile.delete();
					}
				}
			}
			//1. 개인정보 파기
			MembersPrivate membersPrivate = membersPrivateRepository.findByUserUniqId(members.getUserUniqId());
			MembersPrivateDto privateDto = modelMapper.map(membersPrivate, MembersPrivateDto.class);
			privateDto.setUserName("");
			privateDto.setPhoneNumber("");
			privateDto.setBirth("");
			membersPrivateRepository.save(privateDto.toEntity());
			
			long userNo = profile.getUserNo();
			//2. 팔로우 및 팔로잉 정보 삭제
			membersFollowInfoRepository.deleteByFollowUsersFollowingUserNo(userNo);	
			membersFollowInfoRepository.deleteByFollowUsersFollowerUserNo(userNo);
		
			//3. 사용자 프로필 탈퇴회원으로 표시 전환
			membersProfileRepository.changeProfileImg(members.getUserUniqId() , null, null);
			membersProfileRepository.changeNickname(members.getUserUniqId() , "탈퇴회원");
			
			//4. 사용자 제작 문제 contents_classify 3으로 변경
			List<MathContents> mathContentsList = mathContentsRepository.findByUserUniqId(members.getUserUniqId());
			for(MathContents contents : mathContentsList) {
				if(contents.getContentsClassify() == 2) {		//변형문제는 삭제
					int transConCnt = mathContentsRepository.countByOrgContentsNo(contents.getOrgContentsNo());
					mathConLikeInfoRepository.deleteByMathConLikeDomainContentsNo(contents.getContentsNo());
					mathConRepoInfoRepository.deleteByMathConRepoDomainContentsNo(contents.getContentsNo());
					mathContentsRepository.updateTransConCnt(contents.getOrgContentsNo(), transConCnt-1);
					mathContentsRepository.deleteByContentsNo(contents.getContentsNo());
				
				}else {		//사용자 제작문제는 contentsClassify=3으로 변경
					mathConLikeInfoRepository.deleteByMathConLikeDomainContentsNo(contents.getContentsNo());
					mathConRepoInfoRepository.deleteByMathConRepoDomainContentsNo(contents.getContentsNo());
					mathContentsRepository.updateContentsClassify(contents.getContentsNo(), 3);
				}
			}
			
			//5. resource 삭제
			for(MathResource mathResource : mathResourceList) {
				mathResourceImgRepository.deleteByResourceNo(mathResource.getResourceNo());
				mathResourceCateRepository.deleteByResourceNo(mathResource.getResourceNo());
				
			}
			mathResourceRepository.deleteByUserUniqId(members.getUserUniqId());
			
			//6. 좋아요 및 저장소 정보 삭제
			mathConLikeInfoRepository.deleteByMathConLikeDomainUserUniqId(members.getUserUniqId());
			mathConRepoInfoRepository.deleteByMathConRepoDomainUserUniqId(members.getUserUniqId());
			
			//7. 학습지 생성내역 삭제
			mathDocsPaperRepository.deleteByUserUniqId(members.getUserUniqId());
			
			//8. 최종 탈퇴 처리(human_status=3(탈퇴회원), enabled=false)
			MembersDto membersDto = modelMapper.map(members, MembersDto.class);
			membersDto.setHumanStatus(3);
			membersRepository.save(membersDto.toEntity());
			for(MembersRole role : roleList) {
				MembersRoleDto roleDto = modelMapper.map(role, MembersRoleDto.class);
				roleDto.setEnabled(false);
				membersRoleRepository.save(roleDto.toEntity());
			}
		}
		
	}
	
	
	@Transactional
	public void initHwpDownCnt() {
		membersProfileRepository.initHwpDownCnt();
	}
	
	
}
