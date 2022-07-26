package com.numberbox.common.util;

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
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFSlide;

public class CommonUtil {

	public static HashMap<String, Object> convertPPtSlidePngImge(String filePath, String fileName, boolean onlyOne) throws FileNotFoundException, IOException {
		final List<String> imageStrList = new ArrayList<>();
		XMLSlideShow originalPpt = new XMLSlideShow(new FileInputStream(filePath+"/"+fileName));
		originalPpt.close();
		Dimension pgsize = originalPpt.getPageSize();
		List<XSLFSlide> slides = originalPpt.getSlides();
		HashMap<String, Object> map = new HashMap<String, Object>();
		if(onlyOne) {
			final BufferedImage img = new BufferedImage(pgsize.width, pgsize.height, BufferedImage.SCALE_SMOOTH);
	        final Graphics2D graphics = img.createGraphics();
	        //clear the drawing area
	        graphics.setPaint(Color.white);
	        graphics.fill(new Rectangle2D.Float(0, 0, pgsize.width, pgsize.height));
			
	        //render
	        slides.get(0).draw(graphics);
	        
	        File slideFile = null;
	        slideFile = File.createTempFile(filePath+"/"+fileName, ".png");
        	ImageIO.write(img, "png", slideFile);
        	
    	    byte[] fileContent = FileUtils.readFileToByteArray(slideFile);
        	String encodedString = Base64.getEncoder().encodeToString(fileContent);
        	
        	imageStrList.add(encodedString);
		}else {
			for(XSLFSlide slide : slides) {
				final BufferedImage img = new BufferedImage(pgsize.width, pgsize.height, BufferedImage.SCALE_SMOOTH);
		        final Graphics2D graphics = img.createGraphics();

		        //clear the drawing area
		        graphics.setPaint(Color.white);
		        graphics.fill(new Rectangle2D.Float(0, 0, pgsize.width, pgsize.height));
				
		        //render
		        slide.draw(graphics);
		        
		        File slideFile = null;
		        slideFile = File.createTempFile(filePath+"/"+fileName, ".png");
	        	ImageIO.write(img, "png", slideFile);
	        	
	    	    byte[] fileContent = FileUtils.readFileToByteArray(slideFile);
	        	String encodedString = Base64.getEncoder().encodeToString(fileContent);
	        	
	        	imageStrList.add(encodedString);
			}
		}
		
		map.put("imgList", imageStrList);
        return map;
	}
	
	public static String savePPtFirstSlideToPngImge(String filePath, String fileName, String imgFilePath) throws FileNotFoundException, IOException {
		XMLSlideShow originalPpt = new XMLSlideShow(new FileInputStream(filePath+"/"+fileName));
		originalPpt.close();
		Dimension pgsize = originalPpt.getPageSize();
		List<XSLFSlide> slides = originalPpt.getSlides();
		final BufferedImage img = new BufferedImage(pgsize.width, pgsize.height, BufferedImage.SCALE_SMOOTH);
        final Graphics2D graphics = img.createGraphics();

        //clear the drawing area
        graphics.setPaint(Color.white);
        graphics.fill(new Rectangle2D.Float(0, 0, pgsize.width, pgsize.height));
		
        //render
        slides.get(0).draw(graphics);
        String[] imgNames = fileName.split("\\.");
        File slideFile = new File(imgFilePath, imgNames[0]+ ".png");
    	ImageIO.write(img, "png", slideFile);
        return imgNames[0]+ ".png";
	}
	
	
	public static HashMap<String, Object> savePPtSlideToPngImge(String filePath, String fileName, String imgFilePath) throws FileNotFoundException, IOException {
		final List<String> imageStrList = new ArrayList<>();
		XMLSlideShow originalPpt = new XMLSlideShow(new FileInputStream(filePath+"/"+fileName));
		originalPpt.close();
		Dimension pgsize = originalPpt.getPageSize();
		List<XSLFSlide> slides = originalPpt.getSlides();
		HashMap<String, Object> map = new HashMap<String, Object>();
		int index = 0;
		for(XSLFSlide slide : slides) {
			Random random1 = new Random();
			long currentTime1 = System.currentTimeMillis();
			int randomValue1 = random1.nextInt(100);
			
			final BufferedImage img = new BufferedImage(pgsize.width, pgsize.height, BufferedImage.SCALE_SMOOTH);
	        final Graphics2D graphics = img.createGraphics();

	        //clear the drawing area
	        graphics.setPaint(Color.white);
	        graphics.fill(new Rectangle2D.Float(0, 0, pgsize.width, pgsize.height));
			
	        //render
	        slide.draw(graphics);
	        String[] imgNames = fileName.split("\\.");
	        String imgName = imgNames[0]+"_"+index+".png";
	        String imgFileName = Long.toString(currentTime1) + "_"+randomValue1+"_"+imgName;
	        File slideFile = new File(imgFilePath, imgFileName);
	    	ImageIO.write(img, "png", slideFile);
	    	
	    	imageStrList.add(imgFileName);
	    	index++;
		}
		
		map.put("imgNameList", imageStrList);
        return map;
	}
	
}
