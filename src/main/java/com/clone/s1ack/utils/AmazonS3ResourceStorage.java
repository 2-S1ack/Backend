//package com.clone.s1ack.utils;
//
//import com.amazonaws.services.s3.AmazonS3Client;
//import com.amazonaws.services.s3.model.CannedAccessControlList;
//import com.amazonaws.services.s3.model.PutObjectRequest;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.File;
//
//@Component
//@RequiredArgsConstructor
//@Slf4j
//public class AmazonS3ResourceStorage {
//    @Value("${cloud.aws.s3.bucket}")
//    private String bucket;
//    private final AmazonS3Client amazonS3Client;
//
//    public void store(String fullPath, MultipartFile multipartFile) {
//        File file = new File(MultipartUtil.getLocalHomeDir(), fullPath);
//        log.info("file = {}", file);
//        try {
//            multipartFile.transferTo(file);
//            amazonS3Client.putObject(new PutObjectRequest(bucket, fullPath, file)
//                    .withCannedAcl(CannedAccessControlList.PublicRead));
//        } catch (Exception e) {
//            throw new RuntimeException();
//        } finally {
//            if (file.exists()) {
//                file.delete();
//            }
//        }
//    }
//}
