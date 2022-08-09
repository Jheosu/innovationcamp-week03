package com.innovation.myblog.scheduler;

import com.innovation.myblog.repository.MyblogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor // final 멤버 변수를 자동으로 생성합니다.
@Component // 스프링이 필요 시 자동으로 생성하는 클래스 목록에 추가합니다.
@Slf4j
public class Scheduler {

    private final MyblogRepository myblogRepository;


    // 초, 분, 시, 일, 월, 주 순서
    @Scheduled(cron = "0 0 1 * * *")
    @Transactional
    public void updatePrice() throws InterruptedException {
        // 저장된 모든 관심상품을 조회합니다.
        myblogRepository.deleteByCommentCount(0);
        log.info("게시글이 삭제 되었습니다");
        }
    }
