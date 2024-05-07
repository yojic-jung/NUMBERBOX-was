package com.numberbox.common.util;

import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.charset.Charset;
import java.util.List;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Component
@RequiredArgsConstructor
public class CommonUtil {

    @Value("${numberbox.emailAddress}")
    private String emailAddress;

    @Value("${numberbox.emailPassword}")
    private String emailPassword;

    private static String[] randomStr = {"~", "!", "@", "#", "%", "^", "&", "*", "-", "_", "=", "+", "?", ";", ":",
            ",", "."};

    public static String makeRandomPassword() {
        Random random = new Random();
        String generatedString = random.ints(97, 123).limit(10)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
        generatedString = generatedString + randomStr[random.nextInt(randomStr.length - 1)]
                + randomStr[random.nextInt(randomStr.length - 1)] + randomStr[random.nextInt(randomStr.length - 1)]
                + randomStr[random.nextInt(randomStr.length - 1)] + randomStr[random.nextInt(randomStr.length - 1)];
        return generatedString;
    }

    public static HashMap<String, Object> convertPPtSlidePngImge(String filePath, String fileName, boolean onlyOne)
            throws FileNotFoundException, IOException {
        final List<String> imageStrList = new ArrayList<>();
        XMLSlideShow originalPpt = new XMLSlideShow(new FileInputStream(filePath + "/" + fileName));
        originalPpt.close();
        Dimension pgsize = originalPpt.getPageSize();
        List<XSLFSlide> slides = originalPpt.getSlides();
        HashMap<String, Object> map = new HashMap<String, Object>();
        if (onlyOne) {
            final BufferedImage img = new BufferedImage(pgsize.width, pgsize.height, BufferedImage.SCALE_SMOOTH);
            final Graphics2D graphics = img.createGraphics();
            // clear the drawing area
            graphics.setPaint(Color.white);
            graphics.fill(new Rectangle2D.Float(0, 0, pgsize.width, pgsize.height));

            // render
            slides.get(0).draw(graphics);

            File slideFile = null;
            slideFile = File.createTempFile(filePath + "/" + fileName, ".png");
            ImageIO.write(img, "png", slideFile);

            byte[] fileContent = FileUtils.readFileToByteArray(slideFile);
            String encodedString = Base64.getEncoder().encodeToString(fileContent);

            imageStrList.add(encodedString);
        } else {
            for (XSLFSlide slide : slides) {
                final BufferedImage img = new BufferedImage(pgsize.width, pgsize.height, BufferedImage.SCALE_SMOOTH);
                final Graphics2D graphics = img.createGraphics();

                // clear the drawing area
                graphics.setPaint(Color.white);
                graphics.fill(new Rectangle2D.Float(0, 0, pgsize.width, pgsize.height));

                // render
                slide.draw(graphics);

                File slideFile = null;
                slideFile = File.createTempFile(filePath + "/" + fileName, ".png");
                ImageIO.write(img, "png", slideFile);

                byte[] fileContent = FileUtils.readFileToByteArray(slideFile);
                String encodedString = Base64.getEncoder().encodeToString(fileContent);

                imageStrList.add(encodedString);
            }
        }

        map.put("imgList", imageStrList);
        return map;
    }

    public static String savePPtFirstSlideToPngImge(String filePath, String fileName, String imgFilePath)
            throws FileNotFoundException, IOException {
        XMLSlideShow originalPpt = new XMLSlideShow(new FileInputStream(filePath + "/" + fileName));
        originalPpt.close();
        Dimension pgsize = originalPpt.getPageSize();
        List<XSLFSlide> slides = originalPpt.getSlides();
        final BufferedImage img = new BufferedImage(pgsize.width, pgsize.height, BufferedImage.SCALE_SMOOTH);
        final Graphics2D graphics = img.createGraphics();

        // clear the drawing area
        graphics.setPaint(Color.white);
        graphics.fill(new Rectangle2D.Float(0, 0, pgsize.width, pgsize.height));

        // render
        slides.get(0).draw(graphics);
        String[] imgNames = fileName.split("\\.");
        File slideFile = new File(imgFilePath, imgNames[0] + ".png");
        ImageIO.write(img, "png", slideFile);
        return imgNames[0] + ".png";
    }

    public static HashMap<String, Object> savePPtSlideToPngImge(String filePath, String fileName, String imgFilePath)
            throws FileNotFoundException, IOException {
        final List<String> imageStrList = new ArrayList<>();
        XMLSlideShow originalPpt = new XMLSlideShow(new FileInputStream(filePath + "/" + fileName));
        originalPpt.close();
        Dimension pgsize = originalPpt.getPageSize();
        List<XSLFSlide> slides = originalPpt.getSlides();
        HashMap<String, Object> map = new HashMap<String, Object>();
        int index = 0;
        for (XSLFSlide slide : slides) {
            Random random1 = new Random();
            long currentTime1 = System.currentTimeMillis();
            int randomValue1 = random1.nextInt(100);

            final BufferedImage img = new BufferedImage(pgsize.width, pgsize.height, BufferedImage.SCALE_SMOOTH);
            final Graphics2D graphics = img.createGraphics();

            // clear the drawing area
            graphics.setPaint(Color.white);
            graphics.fill(new Rectangle2D.Float(0, 0, pgsize.width, pgsize.height));

            // render
            slide.draw(graphics);
            String[] imgNames = fileName.split("\\.");
            String imgName = imgNames[0] + "_" + index + ".png";
            String imgFileName = Long.toString(currentTime1) + "_" + randomValue1 + "_" + imgName;
            File slideFile = new File(imgFilePath, imgFileName);
            ImageIO.write(img, "png", slideFile);

            imageStrList.add(imgFileName);
            index++;
        }

        map.put("imgNameList", imageStrList);
        return map;
    }

    public File convertMultipartFileToFile(MultipartFile mfile, String path) throws IOException {
        File file = new File(path, mfile.getOriginalFilename());
        mfile.transferTo(file);
        return file;
    }

    /**
     * 압축풀기 메소드
     *
     * @param zipFileName 압축파일
     * @param directory   압축 풀 폴더
     */
    public String unZip(String zipPath, String zipFileName, String zipUnzipPath) {

        boolean isChk = false;

        // 해제할 홀더 위치를 재조정
        zipUnzipPath = zipUnzipPath + zipFileName.replace(".zip", "");

        // zip 파일
        File zipFile = new File(zipPath + zipFileName);

        FileInputStream fis = null;
        ZipInputStream zis = null;
        ZipEntry zipentry = null;

        try {
            // zipFileName을 통해서 폴더 만들기
            makeFolder(zipUnzipPath);

            // 파일 스트림
            fis = new FileInputStream(zipFile);

            // Zip 파일 스트림
            zis = new ZipInputStream(fis, Charset.forName("UTF-8"));

            // 압축되어 있는 ZIP 파일의 목록 조회
            while ((zipentry = zis.getNextEntry()) != null) {
                String filename = zipentry.getName();
                File file = new File(zipUnzipPath, filename);

                // entiry가 폴더면 폴더 생성
                if (zipentry.isDirectory()) {
                    file.mkdirs();
                } else {
                    // 파일이면 파일 만들기
                    try {
                        createFile(file, zis);
                    } catch (Throwable e) {
                        e.printStackTrace();
                    }
                }
            }
            isChk = true;

        } catch (Exception e) {
            isChk = false;
        } finally {
            if (zis != null) {
                try {
                    zis.close();
                } catch (IOException e) {
                }
            }
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                }
            }
        }

        return zipUnzipPath;
    }

    /**
     * @param folder - 생성할 폴더 경로와 이름
     */
    private boolean makeFolder(String folder) {
        if (folder.length() < 0) {
            return false;
        }

        String path = folder; // 폴더 경로
        File Folder = new File(path);

        // 해당 디렉토리가 없을경우 디렉토리를 생성합니다.
        if (!Folder.exists()) {
            try {
                Folder.mkdir(); // 폴더 생성합니다.
            } catch (Exception e) {
                e.getStackTrace();
            }
        }
        return true;
    }

    /**
     * 파일 만들기 메소드
     *
     * @param file 파일
     * @param zis  Zip스트림
     */
    private void createFile(File file, ZipInputStream zis) throws Throwable {

        // 디렉토리 확인
        File parentDir = new File(file.getParent());
        // 디렉토리가 없으면 생성하자
        if (!parentDir.exists()) {
            parentDir.mkdirs();
        }
        FileOutputStream fos = null;
        // 파일 스트림 선언
        try {

            fos = new FileOutputStream(file);
            byte[] buffer = new byte[256];
            int size = 0;
            // Zip스트림으로부터 byte뽑아내기
            while ((size = zis.read(buffer)) > 0) {
                // byte로 파일 만들기
                fos.write(buffer, 0, size);
            }
        } catch (Throwable e) {
            throw e;
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                }

            }

        }

    }

    public void sendMail(String recipient, String title, String contents) throws AddressException, MessagingException {
        // 네이버일 경우 smtp.naver.com 을 입력합니다.
        // Google일 경우 smtp.gmail.com 을 입력합니다.
        String host = "smtp.gmail.com";
        // 네이버 아이디를 입력해주세요. @naver.com은 입력하지 마시구요.
        // 네이버 이메일 비밀번호를 입력해주세요.
        int port = 465; // 포트번호 // 메일 내용
        // 받는 사람의 메일주소를 입력해주세요.

        // 메일 내용 입력해주세요.
        Properties props = System.getProperties();
        // 정보를 담기 위한 객체 생성 // SMTP 서버 정보 설정
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.ssl.trust", host);

        // Session 생성
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            String un = emailAddress.split("@")[0];
            String pw = emailPassword;

            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                return new javax.mail.PasswordAuthentication(un, pw);
            }
        });
        session.setDebug(true);
        // for debug
        Message mimeMessage = new MimeMessage(session);
        // MimeMessage 생성
        mimeMessage.setFrom(new InternetAddress(emailAddress));
        // 발신자 셋팅 , 보내는 사람의 이메일주소를 한번 더 입력합니다. 이때는 이메일 풀 주소를 다 작성해주세요.
        mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
        // 수신자셋팅 //.TO 외에 .CC(참조) .BCC(숨은참조) 도 있음
        mimeMessage.setSubject(title);
        // 제목셋팅
        mimeMessage.setContent(contents, "text/html; charset=utf-8");
        // 내용셋팅
        Transport.send(mimeMessage);
        // javax.mail.Transport.send() 이용 }
    }
}
