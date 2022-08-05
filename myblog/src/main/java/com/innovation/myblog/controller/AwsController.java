package com.innovation.myblog.controller;

import com.innovation.myblog.service.AwsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
public class AwsController {

    private final AwsService awsService;

    @PostMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile multipartFile) throws Exception {
        awsService.uploadFile(multipartFile, "static");
        return "test";
    }
}
