package com.numberbox.filestorage.storage;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.numberbox.appdomain.dto.image.ImgFileInDto;
import com.numberbox.appdomain.dto.image.ImgFileMetaInfoDto;
import com.numberbox.appusecase.image.port.in.ImgFileNameMaker;
import com.numberbox.appusecase.image.port.out.storage.ImgFileStorage;
import com.numberbox.filestorage.config.StorageProperties;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Random;

@Service
public class AwsS3Storage implements ImgFileStorage {
    private StorageProperties storageProperties;
    private String bucketName;
    private AmazonS3 amazonS3;

    private ImgFileNameMaker imgFileNameMaker;

    public AwsS3Storage(StorageProperties storageProperties, ImgFileNameMaker imgFileNameMaker) {
        this.storageProperties = storageProperties;
        this.bucketName = storageProperties.bucket().name();
        this.imgFileNameMaker = imgFileNameMaker;

        String accessKey = storageProperties.credentials().accessKey();
        String secretKey = storageProperties.credentials().secretKey();
        AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
        AWSCredentialsProvider credentialsProvider = new AWSStaticCredentialsProvider(credentials);
        this.amazonS3 = AmazonS3ClientBuilder.standard()
                .withCredentials(credentialsProvider)
                .withRegion(storageProperties.region().name()).build();
    }

    @Override
    public ImgFileMetaInfoDto upload(ImgFileInDto imgFileInDto) {
        // 저장할 이미지 파일 경로 및 이름 생성
        ImgFileMetaInfoDto imgMetaInfoDto =
                imgFileNameMaker.make(imgFileInDto.imagePathType(), imgFileInDto.originFileName());

        // 이미지 저장
        PutObjectRequest putObjectRequest =
                new PutObjectRequest(bucketName, imgMetaInfoDto.getImgFileFullName(), imgFileInDto.inputStream(), null);
        amazonS3.putObject(putObjectRequest.withCannedAcl(CannedAccessControlList.PublicRead));
        return imgMetaInfoDto;
    }

    // 단일 파일 S3 전달
<<<<<<<< HEAD:project/infra/file-storage/src/main/java/com/numberbox/filestorage/storage/AwsS3Storage.java
    public String uploadToS3SeverSingleFile(int actionId, MultipartFile file) throws IOException {
        Random random1 = new Random();
        long currentTime1 = System.currentTimeMillis();
        int randomValue1 = random1.nextInt(100);
        LocalDate now = LocalDate.now();
        String year = Integer.toString(now.getYear());
        int monthValue = now.getMonthValue();

        // 폴더 이름 생성 규칙은 actionId-연월
        String fileName = file.getOriginalFilename();
        if (actionId == 10) {
            fileName = "editorImgUpld/" + actionId + "" + year + "" + monthValue + "/" + currentTime1 + "_"
                    + randomValue1 + "_" + file.getOriginalFilename();
        } else if (actionId == 11) {
            fileName = "hwpToHtml/" + actionId + "" + year + "" + monthValue + "/" + currentTime1 + "_" + randomValue1
                    + "_" + file.getOriginalFilename();
        }

        amazonS3.putObject(new PutObjectRequest(bucketName, fileName, file.getInputStream(), null)
                .withCannedAcl(CannedAccessControlList.PublicRead));
        return amazonS3.getUrl(bucketName, fileName).toString();
    }
========
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
>>>>>>>> f9c8594 (모듈 분리 - file-storage 생성):project/app/src/main/java/com/numberbox/aws/s3/service/AwsS3Service.java

    // 단일 파일 S3 전달
    public String uploadToS3SeverSingleFile(int actionId, File file, String fileName) throws IOException {
        long currentTime1 = System.currentTimeMillis();
        int randomValue1 = new Random().nextInt(100);
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

        amazonS3.putObject(new PutObjectRequest(bucketName, fileName, new FileInputStream(file), null)
                .withCannedAcl(CannedAccessControlList.PublicRead));
        return amazonS3.getUrl(bucketName, fileName).toString();
    }

}