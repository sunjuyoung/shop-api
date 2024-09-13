package com.project.shop.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@Slf4j
@SpringBootTest
class CustomS3UtilTest {

    @Autowired
    CustomS3Util s3Util;

    @Test
    public void testupload(){

        Path filePath = new File("C:\\dev\\boardList.png").toPath();
        List<Path> filePath1 = List.of(filePath);

        s3Util.uploadFiles(filePath1,false);
    }

}