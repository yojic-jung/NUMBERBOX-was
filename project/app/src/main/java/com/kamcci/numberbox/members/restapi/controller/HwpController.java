package com.kamcci.numberbox.members.restapi.controller;

import com.kamcci.numberbox.members.restapi.dto.request.HwpJsonStrRequest;
import com.kamcci.numberbox.members.service.MembersService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

public class HwpController {
    private final MembersService membersService;

    public HwpController(MembersService membersService) {
        this.membersService = membersService;
    }

    // todo
    @PostMapping(value = "/takeHwpFile")
    public void takeHwpFile(HwpJsonStrRequest hwpRequest, HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String path = request.getSession().getServletContext().getRealPath("/static/") + "/userHwp/";
        String newFileName = membersService.connectPyServerForMakeHwp(path, hwpRequest);
        String saveFileName = path + newFileName;
        // 직접 파일 정보를 변수에 저장해 놨지만, 이 부분이 db에서 읽어왔다고 가정한다.
        String contentType = "application/x-hwp";
        File file = new File(path, newFileName);
        long fileLength = file.length();
        // 파일의 크기와 같지 않을 경우 프로그램이 멈추지 않고 계속 실행되거나, 잘못된 정보가 다운로드 될 수 있다.
        response.setHeader("Content-Disposition", "attachment; filename=\"" + newFileName + "\";");
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

    // todo
    @GetMapping(value = "/myContentsCheckForHwpDown")
    public Map<String, Object> myContentsCheckForHwpDown(HttpServletRequest request) {
        String contentsNo = request.getParameter("contentsNo");
        return membersService.myContentsCheckForHwpDown(contentsNo);
    }

}
