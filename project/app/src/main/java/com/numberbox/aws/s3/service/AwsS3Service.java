package com.numberbox.aws.s3.service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AwsS3Service {
    private String bucket;
    private AmazonS3 s3Client;

    /**
     * todo
     * properties 객체로 빼기
     * 스프링 빈에 상태 있는것 좋은 방식 아님
     */
    public AwsS3Service(
            @Value("${cloud.aws.credentials.accessKey}") String accessKey,
            @Value("${cloud.aws.credentials.secretKey}") String secretKey,
            @Value("${cloud.aws.s3.bucket}") String bucket,
            @Value("${cloud.aws.region.static}") String region) {
        this.bucket = bucket;

        AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
        AWSCredentialsProvider credentialsProvider = new AWSStaticCredentialsProvider(credentials);
        s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(credentialsProvider)
                .withRegion(region).build();

    }

    // 단일 파일 S3 전달
//    public String uploadToS3SeverSingleFile(int actionId, MultipartFile file) throws IOException {
//        Random random1 = new Random();
//        long currentTime1 = System.currentTimeMillis();
//        int randomValue1 = random1.nextInt(100);
//        LocalDate now = LocalDate.now();
//        String year = Integer.toString(now.getYear());
//        int monthValue = now.getMonthValue();
//
//        // 폴더 이름 생성 규칙은 actionId-연월
//        String fileName = file.getOriginalFilename();
//        if (actionId == 10) {
//            fileName = "editorImgUpld/" + actionId + "" + year + "" + monthValue + "/" + currentTime1 + "_"
//                    + randomValue1 + "_" + file.getOriginalFilename();
//        } else if (actionId == 11) {
//            fileName = "hwpToHtml/" + actionId + "" + year + "" + monthValue + "/" + currentTime1 + "_" + randomValue1
//                    + "_" + file.getOriginalFilename();
//        }
//
//        s3Client.putObject(new PutObjectRequest(bucket, fileName, file.getInputStream(), null)
//                .withCannedAcl(CannedAccessControlList.PublicRead));
//        return s3Client.getUrl(bucket, fileName).toString();
//    }

    // 단일 파일 S3 전달
    public String uploadToS3SeverSingleFile(int actionId, File file, String fileName) throws IOException {
        Random random1 = new Random();
        long currentTime1 = System.currentTimeMillis();
        int randomValue1 = random1.nextInt(100);
        LocalDate now = LocalDate.now();
        String year = Integer.toString(now.getYear());
        int monthValue = now.getMonthValue();

        // 폴더 이름 생성 규칙은 actionId-연월
        if (actionId == 10) {
            if (fileName == null) {
                fileName = "editorImgUpld/" + actionId + "" + year + "" + monthValue + "/" + currentTime1 + "_"
                        + randomValue1 + "_" + file.getName();
            } else {
                fileName = "editorImgUpld/" + actionId + "" + year + "" + monthValue + "/" + fileName;
            }

        } else if (actionId == 11) {
            if (fileName == null) {
                fileName = "hwpToHtml/" + actionId + "" + year + "" + monthValue + "/" + currentTime1 + "_"
                        + randomValue1 + "_" + file.getName();
            } else {
                fileName = "hwpToHtml/" + actionId + "" + year + "" + monthValue + "/" + fileName;
            }
        }

        s3Client.putObject(new PutObjectRequest(bucket, fileName, new FileInputStream(file), null)
                .withCannedAcl(CannedAccessControlList.PublicRead));
        return s3Client.getUrl(bucket, fileName).toString();
    }

}