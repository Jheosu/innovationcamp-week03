package com.innovation.myblog.service;


import com.innovation.myblog.models.Myblog;
import com.innovation.myblog.models.MyblogDto;
import com.innovation.myblog.models.MyblogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service  //@Bean
public class MyblogService {

    private final MyblogRepository myblogRepository;


    @Transactional
    public Long update(Long id, MyblogDto requestDto) {
        String password = requestDto.getPassword();

        Myblog myblog = myblogRepository.findByIdAndPassword(id,password);

        myblog.update(requestDto);

        return id;
    }





}
