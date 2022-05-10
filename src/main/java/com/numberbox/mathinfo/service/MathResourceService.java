package com.numberbox.mathinfo.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.numberbox.mathinfo.dto.MathResourceDto;
import com.numberbox.mathinfo.entity.MathResource;
import com.numberbox.mathinfo.entity.MathResourceMenu;
import com.numberbox.mathinfo.repository.MathResourceMenuRepository;
import com.numberbox.mathinfo.repository.MathResourceRepository;
import com.numberbox.members.entity.MembersNo;
import com.numberbox.security.util.StaticSecurityUtil;

@Service
public class MathResourceService {

	@PersistenceContext
    EntityManager entityManager;

	@Autowired
	MathResourceMenuRepository mathResourceMenuRepository;
	
	@Autowired
	MathResourceRepository mathResourceRepository;
	
	public List<MathResourceMenu> takeResourceMenu() {
		return mathResourceMenuRepository.findAllByOrderByAlignOrderAsc();
	}
	
	public List<MathResource> takeResource(int mainCateNo) {
		return mathResourceRepository.findByMainCateNoOrderByDownCntDesc(mainCateNo);
	}
	
	@Transactional
	public void registerResource(String path, MathResourceDto mathResourceDto) throws IllegalStateException, IOException {
		
		MembersNo membersNo = StaticSecurityUtil.getMembersNo();
		long userNo = membersNo.getUserNo();
		mathResourceDto.setDownCnt(0);
		mathResourceDto.setUserNo(userNo);
		
		Random random1 = new Random();
		long currentTime1 = System.currentTimeMillis();
		int randomValue1 = random1.nextInt(100);

		String imgFileName = Long.toString(currentTime1) + "_"+randomValue1+"_"+mathResourceDto.getImgFile().getOriginalFilename();
		
		File imgFile = new File(path+"/resourceImg", imgFileName);
		mathResourceDto.getImgFile().transferTo(imgFile);
		mathResourceDto.setImgPath("/webapp/static/resourceImg/");
		mathResourceDto.setImgName(imgFileName);
		
		if(mathResourceDto.getPptFile()!=null && !mathResourceDto.getPptFile().isEmpty()) {

			String pptFileName = Long.toString(currentTime1) + "_"+randomValue1+"_"+mathResourceDto.getPptFile().getOriginalFilename();
			
			File pptFile = new File(path+"/resourcePpt" , pptFileName);
			mathResourceDto.getPptFile().transferTo(pptFile);
			mathResourceDto.setPptPath("/webapp/static/resourcePpt/");
			mathResourceDto.setPptName(pptFileName);
		}else {
			mathResourceDto.setPptPath(null);
			mathResourceDto.setPptName(null);
		}
		mathResourceRepository.save(mathResourceDto.toEntity());
	}
}
