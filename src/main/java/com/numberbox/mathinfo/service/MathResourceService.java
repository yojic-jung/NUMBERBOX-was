package com.numberbox.mathinfo.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.numberbox.mathinfo.dto.MathResourceCateDto;
import com.numberbox.mathinfo.dto.MathResourceDto;
import com.numberbox.mathinfo.entity.MathResource;
import com.numberbox.mathinfo.entity.MathResourceCate;
import com.numberbox.mathinfo.entity.MathResourceMenu;
import com.numberbox.mathinfo.repository.MathResourceCateRepository;
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
	
	@Autowired
	MathResourceCateRepository mathResourceCateRepository;
	
	public List<MathResourceMenu> takeResourceMenu() {
		return mathResourceMenuRepository.findAllByOrderByAlignOrderAsc();
	}
	
	public List<MathResourceCate> takeResource(int mainCateNo) {
		List<MathResourceCate> resource = mathResourceCateRepository.findByMainCateNo(mainCateNo);
		Collections.sort(resource, (a, b) -> b.getMathResource().getDownCnt() - a.getMathResource().getDownCnt());
		return resource;
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
		
		MathResource resource = mathResourceRepository.save(mathResourceDto.toEntity());
		System.out.println(resource.getResourceNo());
		int resourceNo = resource.getResourceNo();
		List<MathResourceCate> resourceCateList = new ArrayList<MathResourceCate>();
		String cateList = mathResourceDto.getCateList();
		String[] cateArr = cateList.split(",");
		for(int i=0; i<cateArr.length; i++) {
			String[] cateNo = cateArr[i].split("-");
			MathResourceCateDto mathResourceCate = new MathResourceCateDto();
			mathResourceCate.setResourceNo(resourceNo);
			mathResourceCate.setMainCateNo(Integer.parseInt(cateNo[0]));
			mathResourceCate.setMidCateNo(Integer.parseInt(cateNo[1]));
			resourceCateList.add(mathResourceCate.toEntity());
		}
		
		for(MathResourceCate cate : resourceCateList) {
			System.out.println(cate.getResourceNo());
			System.out.println(cate.getMainCateNo());
			System.out.println(cate.getMidCateNo());
		}
		
		mathResourceCateRepository.saveAll(resourceCateList);
	}
}
