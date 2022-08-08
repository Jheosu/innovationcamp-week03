package com.innovation.myblog.util;

import com.innovation.myblog.repository.MyblogRepository;
import com.innovation.myblog.service.AwsService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class Scheduler {

    private final MyblogRepository myblogRepository;
    private final AwsService awsService;

    @Scheduled(cron = "0 0 1 * * *") // 초, 분, 시, 일, 월, 주 순서
    public void deleteS3File() {
        List<String> s3List = awsService.S3FileList();
        List<String> deleteList = new ArrayList<>();

        for(int i=0; i<s3List.size(); i++) {
            for(int j=0; j<myblogRepository.findAll().size(); j++) {
                if(s3List.get(i).equals(myblogRepository.findAll().get(j).getImageUrl())) {
                    break;
                }else {
                    deleteList.add(s3List.get(i));
                }
            }
        }
        for(int i=0; i<deleteList.size(); i++) {
            awsService.deleteS3(deleteList.get(i));
        }


    }
}
