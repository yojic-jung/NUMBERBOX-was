package com.numberbox.convert.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.numberbox.common.util.ClientConnect;
import com.numberbox.convert.dto.HwpConvertContentsDto;
import com.numberbox.convert.dto.HwpToWebDto;
import com.numberbox.convert.service.ConvertService;

@RestController
@RequestMapping("/convert")
public class ConvertController {

	@Value("${numberbox.hwpSocketIp}")
	private String customsocketip;
	
	@Autowired
	ConvertService convertService;
	
	/*특수문자 인코딩 에러 테스트
	@PostMapping("/test")
	public void test(HwpConvertContentsDto hwpConvertContentsDto) {
		System.out.println(hwpConvertContentsDto.getConvertContents());
	}
	*/
	
	@GetMapping("/myHwpConvertContents")
	public HashMap<String, Object> myHwpConvertContents(HttpServletRequest request) {
		List<HwpConvertContentsDto> list = convertService.takeConvertContents();
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("myList", list);
		return map;
	}
	
	@GetMapping("/errHwpConvertContents")
	public HashMap<String, Object> errHwpConvertContents(HttpServletRequest request) {
		String convertNo = (String)request.getParameter("convertNo");
		System.out.println(convertNo);
		HwpConvertContentsDto convertContents = convertService.takeErrConvertContents(convertNo);
		List<HwpConvertContentsDto> list = new ArrayList<>();
		list.add(convertContents);
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("myList", list);
		return map;
	}
	
	@GetMapping("/removeConvertContents")
	public HashMap<String, Object> removeConvertContents(HttpServletRequest request) {
		String convertNo = (String)request.getParameter("convertNo");
		HashMap<String, Object> map = convertService.removeConvertContents(Long.parseLong(convertNo));
		List<HwpConvertContentsDto> list = convertService.takeConvertContents();
		map.put("myList", list);
		return map;
	}
	
	@PostMapping("/saveMyHwpContents")
	public HashMap<String, Object> saveMyHwpContents(HwpConvertContentsDto hwpConvertContentsDto, HttpServletRequest request) {
		HashMap<String, Object> map = convertService.registerConvertContents(hwpConvertContentsDto, false);
		List<HwpConvertContentsDto> contentsList = convertService.takeConvertContents();
		map.put("contentsList", contentsList);
		return map;
	}
	
	@GetMapping("/changeErrStts")
	public HashMap<String, Object> changeErrStts(HttpServletRequest request) {
		String convertNo = (String)request.getParameter("convertNo");
		HashMap<String, Object> map = convertService.changeErrStts(convertNo);
		List<HwpConvertContentsDto> list = convertService.takeConvertContents();
		map.put("myList", list);
		return map;
	}
	
	@PostMapping("/convertHwpToWeb")
	public HashMap<String, Object> contentsInfo(HwpToWebDto hwpToWebDto, HttpServletRequest request) throws IOException {
		HashMap<String, Object> checkMap = convertService.checkHwpConvertCnt();
		if((boolean)checkMap.get("existMsg") == true) {
			return checkMap;
		}
		String path = request.getSession().getServletContext().getRealPath("/static/")+"/hwpToHtml/";
		ClientConnect cc = new ClientConnect(customsocketip);
		HashMap<String, Object> map = cc.sendFile(hwpToWebDto.getHwpFile(), path);
		cc.closeConnections();
		
		String document = "";
		try{
            //파일 객체 생성
            File file = new File(map.get("unzipPath")+"/index.xhtml");
            //입력 스트림 생성
            FileReader filereader = new FileReader(file);
            //입력 버퍼 생성
            BufferedReader bufReader = new BufferedReader(filereader);
            String line = "";
            while((line = bufReader.readLine()) != null){
            	document += line;
            }
            //.readLine()은 끝에 개행문자를 읽지 않는다.            
            bufReader.close();
        }catch (FileNotFoundException e) {
        }catch(IOException e){
        }
		
		String fileName = hwpToWebDto.getHwpFile().getOriginalFilename();
		HwpConvertContentsDto hwpConvertContentsDto = new HwpConvertContentsDto();
		hwpConvertContentsDto.setConvertFileName(fileName);
		hwpConvertContentsDto.setConvertContents(document);
		hwpConvertContentsDto.setImgPath((String)map.get("imgPath"));
		HashMap<String, Object> map2 =convertService.registerConvertContents(hwpConvertContentsDto, true);
		List<HwpConvertContentsDto> contentsList = convertService.takeConvertContents();
		map2.put("contentsList", contentsList);
		return map2;
	}
	
}
