package com.numberbox.mathinfo.service;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.io.FileUtils;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.numberbox.common.util.CommonUtil;
import com.numberbox.mathinfo.dto.MathResourceCateDto;
import com.numberbox.mathinfo.dto.MathResourceDto;
import com.numberbox.mathinfo.entity.MathResource;
import com.numberbox.mathinfo.entity.MathResourceCate;
import com.numberbox.mathinfo.entity.MathResourceMenu;
import com.numberbox.mathinfo.repository.MathResourceCateRepository;
import com.numberbox.mathinfo.repository.MathResourceMenuRepository;
import com.numberbox.mathinfo.repository.MathResourceRepository;
import com.numberbox.members.entity.Members;
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
	@Autowired
	ModelMapper modelMapper;
	
	public List<MathResourceMenu> takeResourceMenu() {
		return mathResourceMenuRepository.findAllByOrderByAlignOrderAsc();
	}
	
	@Transactional
	public List<MathResourceDto> takeResource(int mainCateNo, String path) throws FileNotFoundException, IOException {
		List<MathResource> resourceList = mathResourceRepository.findDistinctByMathResourceCateMainCateNo(mainCateNo);
		Collections.sort(resourceList, (a, b) -> b.getDownCnt() - a.getDownCnt());
		List<MathResourceDto> mathResourceDtoList = new ArrayList<>();
		for(MathResource resource : resourceList) {
			MathResourceDto resourceDto = modelMapper.map(resource, MathResourceDto.class);
			
			List<MathResourceCateDto> mathResourceCateDtoList = new ArrayList<>();
			List<MathResourceCate> mathResourceCateList = resource.getMathResourceCate();
			for(MathResourceCate mathResourceCate : mathResourceCateList) {
				MathResourceCateDto resourceCateDto = modelMapper.map(mathResourceCate, MathResourceCateDto.class);
				mathResourceCateDtoList.add(resourceCateDto);
			}
			
			resourceDto.setUserUniqId(null);
			mathResourceDtoList.add(resourceDto);
		}
		
		return mathResourceDtoList;
	}
	
	@Transactional
	public List<MathResourceDto> takeMyResource() throws FileNotFoundException, IOException {
		Members members = StaticSecurityUtil.getMembers();
		UUID userUniqId = members.getUserUniqId();
		List<MathResource> resourceList = mathResourceRepository.findByUserUniqIdOrderBySysCreateDateDesc(userUniqId);
		List<MathResourceDto> mathResourceDtoList = new ArrayList<>();
		for(MathResource resource : resourceList) {
			MathResourceDto resourceDto = modelMapper.map(resource, MathResourceDto.class);
			
			List<MathResourceCateDto> mathResourceCateDtoList = new ArrayList<>();
			List<MathResourceCate> mathResourceCateList = resource.getMathResourceCate();
			for(MathResourceCate mathResourceCate : mathResourceCateList) {
				MathResourceCateDto resourceCateDto = modelMapper.map(mathResourceCate, MathResourceCateDto.class);
				mathResourceCateDtoList.add(resourceCateDto);
			}
			resourceDto.setMathResourceCate(mathResourceCateDtoList);
			resourceDto.setUserUniqId(null);
			mathResourceDtoList.add(resourceDto);
		}
		
		return mathResourceDtoList;
	}
	
	@Transactional
	public HashMap<String, Object> registerResource(String path, MathResourceDto mathResourceDto) throws IllegalStateException, IOException {
		HashMap<String, Object> map = new HashMap<String, Object>();
		Members members = StaticSecurityUtil.getMembers();
		UUID userUniqId = members.getUserUniqId();
		mathResourceDto.setDownCnt(0);
		mathResourceDto.setUserUniqId(userUniqId);
		Random random1 = new Random();
		long currentTime1 = System.currentTimeMillis();
		int randomValue1 = random1.nextInt(100);
		
		String pptFileName = Long.toString(currentTime1) + "_"+randomValue1+"_"+mathResourceDto.getPptFile().getOriginalFilename();
		File pptFile = new File(path+"/resourcePpt" , pptFileName);
		mathResourceDto.getPptFile().transferTo(pptFile);
		mathResourceDto.setPptPath("/webapp/static/resourcePpt/");
		mathResourceDto.setPptName(pptFileName);
		XMLSlideShow originalPpt = new XMLSlideShow(new FileInputStream(path+"/resourcePpt/"+pptFileName));
		List<XSLFSlide> slides = originalPpt.getSlides();
		originalPpt.close();
		if(slides.size() >50) {
			pptFile.delete();
			map.put("existMsg", true);
			map.put("serverMsg", "ppt슬라이드 개수는 최대 50장입니다.\nppt슬라이드 개수를 줄여주세요.");
			map.put("isSuccess", false);
			return map;
		}
		mathResourceDto.setPptPageCnt(slides.size());
		
		
		if(!mathResourceDto.getImgFile().getOriginalFilename().equals("")) {
			String imgFileName = Long.toString(currentTime1) + "_"+randomValue1+"_"+mathResourceDto.getImgFile().getOriginalFilename();
			
			File imgFile = new File(path+"/resourceImg", imgFileName);
			mathResourceDto.getImgFile().transferTo(imgFile);
			mathResourceDto.setImgPath("/webapp/static/resourceImg/");
			mathResourceDto.setImgName(imgFileName);
		}else {
			String savedImgName = CommonUtil.savePPtFirstSlideToPngImge(path+"/resourcePpt/", pptFileName, path+"/resourceImg/");
			mathResourceDto.setImgName(savedImgName);
			mathResourceDto.setImgPath("/webapp/static/resourceImg/");
		}
		
		
		MathResource resource = mathResourceRepository.save(mathResourceDto.toEntity());
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
		
		mathResourceCateRepository.saveAll(resourceCateList);
		map.put("isSuccess", true);
		return map;
	}
	
	
	@Transactional
	public HashMap<String, Object> updateResource(String path, MathResourceDto mathResourceDto) throws IllegalStateException, IOException {
		HashMap<String, Object> map = new HashMap<String, Object>();
		Members members = StaticSecurityUtil.getMembers();
		UUID userUniqId = members.getUserUniqId();
		mathResourceDto.setDownCnt(0);
		mathResourceDto.setUserUniqId(userUniqId);
		Random random1 = new Random();
		long currentTime1 = System.currentTimeMillis();
		int randomValue1 = random1.nextInt(100);
		
		MathResource mathResource =mathResourceRepository.findByResourceNo(mathResourceDto.getResourceNo());
		if(!userUniqId.equals(mathResource.getUserUniqId())) {
			map.put("existMsg", true);
			map.put("serverMsg", "본인이 만든 컨텐츠 외의 컨텐츠는 수정할 수 없습니다.");
			map.put("isSuccess", false);
			return map;
		}
		
		String pptName = mathResourceDto.getPptFile().getOriginalFilename();
		String imgName = mathResourceDto.getImgFile().getOriginalFilename();
		String pptFileName="";
		//ppt파일 없는 경우 기존 ppt파일 그대로 저장
		if(pptName.equals("")) {
			mathResourceDto.setPptPath(mathResource.getPptPath());
			mathResourceDto.setPptName(mathResource.getPptName());
			mathResourceDto.setPptPageCnt(mathResource.getPptPageCnt());
		}else {
			pptFileName = Long.toString(currentTime1) + "_"+randomValue1+"_"+mathResourceDto.getPptFile().getOriginalFilename();
			File pptFile = new File(path+"/resourcePpt" , pptFileName);
			mathResourceDto.getPptFile().transferTo(pptFile);
			mathResourceDto.setPptPath("/webapp/static/resourcePpt/");
			mathResourceDto.setPptName(pptFileName);
			XMLSlideShow originalPpt = new XMLSlideShow(new FileInputStream(path+"/resourcePpt/"+pptFileName));
			List<XSLFSlide> slides = originalPpt.getSlides();
			originalPpt.close();
			if(slides.size() >50) {
				pptFile.delete();
				map.put("existMsg", true);
				map.put("serverMsg", "ppt슬라이드 개수는 최대 50장입니다.\nppt슬라이드 개수를 줄여주세요.");
				map.put("isSuccess", false);
				return map;
			}
			
			//기존 ppt 삭제
			File existPptFile = new File(path+"/resourcePpt" , mathResource.getPptName());
			existPptFile.delete();
			
			mathResourceDto.setPptPageCnt(slides.size());
		}
		
		//이미지파일 없는 경우
		if(imgName.equals("")) {
			mathResourceDto.setImgPath(mathResource.getImgPath());
			mathResourceDto.setImgName(mathResource.getImgName());
		}else {
			String imgFileName = Long.toString(currentTime1) + "_"+randomValue1+"_"+mathResourceDto.getImgFile().getOriginalFilename();
			
			File imgFile = new File(path+"/resourceImg", imgFileName);
			mathResourceDto.getImgFile().transferTo(imgFile);
			mathResourceDto.setImgPath("/webapp/static/resourceImg/");
			mathResourceDto.setImgName(imgFileName);
			//기존 이미지 삭제
			File existImgFile = new File(path+"/resourceImg" , mathResource.getImgName());
			existImgFile.delete();
		}
		
		MathResource resource = mathResourceRepository.save(mathResourceDto.toEntity());
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
		
		mathResourceCateRepository.deleteByResourceNo(mathResourceDto.getResourceNo());
		
		mathResourceCateRepository.saveAll(resourceCateList);
		
		map.put("isSuccess", true);
		return map;
	}
	
	@Transactional
	public HashMap<String, Object> takeNewMathResource(int resourceNo){
		HashMap<String, Object> map = new HashMap<String, Object>();
		MathResource newMathResource = mathResourceRepository.findByResourceNo(resourceNo);
		
		MathResourceDto resourceDto = modelMapper.map(newMathResource, MathResourceDto.class);
		List<MathResourceCateDto> mathResourceCateDtoList = new ArrayList<>();
		List<MathResourceCate> mathResourceCateList = newMathResource.getMathResourceCate();
		for(MathResourceCate mathResourceCate : mathResourceCateList) {
			MathResourceCateDto resourceCateDto = modelMapper.map(mathResourceCate, MathResourceCateDto.class);
			mathResourceCateDtoList.add(resourceCateDto);
		}
		resourceDto.setUserUniqId(null);
		resourceDto.setMathResourceCate(mathResourceCateDtoList);
		map.put("newMathResource", resourceDto);
		return map;
	}
	
	
	@Transactional
	public HashMap<String, Object> takePPtSlideImge() throws FileNotFoundException, IOException {
		List<String> imageStrList = new ArrayList<>();
		XMLSlideShow originalPpt = new XMLSlideShow(new FileInputStream("C:\\Users\\82108\\git\\NUMBERBOX-was\\src\\main\\webapp\\static\\resourcePpt\\aaa.pptx"));
		originalPpt.close();
		Dimension pgsize = originalPpt.getPageSize();
		List<XSLFSlide> slides = originalPpt.getSlides();
		HashMap<String, Object> map = new HashMap<String, Object>();
		for(XSLFSlide slide : slides) {
			final BufferedImage img = new BufferedImage(pgsize.width, pgsize.height, BufferedImage.SCALE_SMOOTH);
	        final Graphics2D graphics = img.createGraphics();

	        //clear the drawing area
	        graphics.setPaint(Color.white);
	        graphics.fill(new Rectangle2D.Float(0, 0, pgsize.width, pgsize.height));
			
	        //render
	        slide.draw(graphics);
	        
	        File slideFile = null;
	        slideFile = File.createTempFile("C:\\Users\\82108\\git\\NUMBERBOX-was\\src\\main\\webapp\\static\\resourcePpt\\aaa.pptx", ".png");
        	ImageIO.write(img, "png", slideFile);
        	
    	    byte[] fileContent = FileUtils.readFileToByteArray(slideFile);
        	String encodedString = Base64.getEncoder().encodeToString(fileContent);
        	
        	imageStrList.add(encodedString);
		}
		
		map.put("imgList", imageStrList);
        return map;
	}
	
	
	@Transactional
	public HashMap<String, Object> myResourceDel(int resourceNo, String path) {
		Members members = StaticSecurityUtil.getMembers();
		UUID userUniqId = members.getUserUniqId();
		HashMap<String, Object> map = new HashMap<String, Object>();
		MathResource mathResource = mathResourceRepository.findByResourceNo(resourceNo);
		//자기 자신의 리소스 아니면 삭제 불가
		if(!mathResource.getUserUniqId().equals(userUniqId)) {
			map.put("existMsg", true);
			map.put("serverMsg", "자기 자신의 컨텐츠 외에는 삭제할 수 없습니다.");
			return map;
		}
		File file = new File(path+"/resourcePpt/"+mathResource.getPptName());
		file.delete();
		File file2 = new File(path+"/resourceImg/"+mathResource.getImgName());
		file2.delete();
		mathResourceRepository.deleteByResourceNo(resourceNo);
		mathResourceCateRepository.deleteByResourceNo(resourceNo);
		map.put("existMsg", false);
		return map;
	}
}
