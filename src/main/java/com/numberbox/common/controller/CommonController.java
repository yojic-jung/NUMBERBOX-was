package com.numberbox.common.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.numberbox.aws.s3.service.AwsS3Service;
import com.numberbox.common.dto.ImgFileModel;
import com.numberbox.common.service.ImgFileService;

@RestController
@RequestMapping("/common")
public class CommonController {

	@Autowired
	AwsS3Service awsS3Service;
	@Autowired
	ImgFileService imgFileService;

	@PostMapping("/imgUpload")
	public HashMap<String, Object> imgUpload(ImgFileModel imgFileModel) throws IOException {
		HashMap<String, Object> map = new HashMap<String, Object>();
		// s3에 저장
		String s3ImgUrl = awsS3Service.uploadToS3SeverSingleFile(imgFileModel.getActionId(),
				imgFileModel.getMultipartFile());
		// tmpImgInfo에 저장
		imgFileService.registerTmpImgFileInfo(imgFileModel, s3ImgUrl);
		map.put("s3ImgUrl", s3ImgUrl);
		return map;
	}

	@GetMapping("/download")
	public void download(HttpServletRequest request, HttpServletResponse response) {
		// 직접 파일 정보를 변수에 저장해 놨지만, 이 부분이 db에서 읽어왔다고 가정한다.
		String fileName = request.getParameter("fileName");
		String filePath = request.getParameter("filePath");
		String saveFileName = request.getSession().getServletContext().getRealPath("/static/") + filePath + "/"
				+ fileName;
		String contentType = "application/vnd.ms-PowerPoint";
		File file = new File(saveFileName);
		// 파일의 크기와 같지 않을 경우 프로그램이 멈추지 않고 계속 실행되거나, 잘못된 정보가 다운로드 될 수 있다.
		long fileLength = file.length();

		response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\";");
		response.setHeader("Content-Transfer-Encoding", "binary");
		response.setHeader("Content-Type", contentType);
		response.setHeader("Content-Length", "" + fileLength);
		response.setHeader("Pragma", "no-cache;");
		response.setHeader("Expires", "-1;");

		try (FileInputStream fis = new FileInputStream(saveFileName); OutputStream out = response.getOutputStream();) {
			int readCount = 0;
			byte[] buffer = new byte[1024];
			while ((readCount = fis.read(buffer)) != -1) {
				out.write(buffer, 0, readCount);
			}
		} catch (Exception ex) {
			throw new RuntimeException("file Save Error");
		}
	}
}
