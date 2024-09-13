package com.project.shop.util;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CustomS3Util {

    @Value("${spring.cloud.aws.s3.bucket}")
    private String bucket;

    private final S3Client s3Client;

    @Value("${com.test.upload.path}")
    private String uploadPath;

    @PostConstruct
    public void init(){
        File tempFolder = new File(uploadPath);
        if(tempFolder.exists() == false){
            tempFolder.mkdirs();
        }

        uploadPath = tempFolder.getAbsolutePath();
    }

    public List<String> saveFiles(List<MultipartFile> files) throws RuntimeException{
        if(files == null || files.isEmpty()){
            return List.of();
        }

        List<String> uploadNames = new ArrayList<>();
        for(MultipartFile multipartFile : files){
            String savedName = UUID.randomUUID().toString() + "_"+multipartFile.getOriginalFilename();
            Path savePath = Paths.get(uploadPath, savedName);
            List<Path> uploadTargetPaths = new ArrayList<>();
            try {
                Files.copy(multipartFile.getInputStream(),savePath);
                uploadTargetPaths.add(savePath);
                uploadNames.add(savedName);

                uploadFiles(uploadTargetPaths,true);


            }catch (Exception e){

                e.printStackTrace();
                throw new RuntimeException(e.getMessage());
            }
        }
        return uploadNames;
    }


    public void uploadFiles(List<Path> filePaths, boolean delFalg){

        if(filePaths ==null || filePaths.isEmpty()){
            return;
        }

        for(Path filePath : filePaths){

            PutObjectRequest request = PutObjectRequest.builder()
                    .bucket(bucket)
                    .key(filePath.getFileName().toString())
                    .build();
            s3Client.putObject(request, filePath);
            if(delFalg){
                try{
                    Files.delete(filePath);
                }catch (IOException e){
                    throw new RuntimeException(e.getMessage());
                }
            }

        }
    }




    public void deleteFiles(List<String> filePaths){
        if(filePaths == null || filePaths.isEmpty()){
            return;
        }
        for(String filePath : filePaths){
            s3Client.deleteObject(builder -> builder.bucket(bucket).key(filePath));
        }
    }

    public void deleteFile(List<Path> filePaths){
        if(filePaths == null || filePaths.isEmpty()){
            return;
        }
        for(Path filePath : filePaths){
            s3Client.deleteObject(builder -> builder.bucket(bucket).key(filePath.getFileName().toString()));
        }
    }
}
