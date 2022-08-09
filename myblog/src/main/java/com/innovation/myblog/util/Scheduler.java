package com.innovation.myblog.util;

import com.innovation.myblog.repository.MyblogRepository;
import com.innovation.myblog.service.AwsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class Scheduler {

    private final MyblogRepository myblogRepository;
    private final AwsService awsService;



    // 초, 분, 시, 일, 월, 주 순서
    @Scheduled(cron = "0 0 1 * * *")
    @Transactional
    public void deletepost() {
        myblogRepository.deleteByCommentCount(0);
        log.info("게시글이 삭제 되었습니다");
    }
    //사용되지 않는 사진을 S3에서 삭제하는 스케쥴러
    @Scheduled(cron = "0 0 1 * * *") // 초, 분, 시, 일, 월, 주 순서
    public void deleteS3File() {

        List<String> s3List = awsService.S3FileList(); // s3에 있는 파일 리스트
        List<String> deleteList = new ArrayList<>(); // 삭제할 목록을 담을 리스트
        int count = 0;
        int DB_size = myblogRepository.findAll().size();

        for (String s : s3List) {
            for (int i = 0; i < myblogRepository.findAll().size(); i++) {
                if (!s.substring(0,37).equals(myblogRepository.findAll().get(i).getImageUrl().substring(50,87))) {
                    count++;
                }
            }
            if(count == DB_size) {
                deleteList.add(s);
            }
            count = 0;
        }

        for (String s : deleteList) {
            awsService.deleteS3(s);
        }
        log.info("사용하지 않는 파일이 삭제되었습니다.");
    }
}
