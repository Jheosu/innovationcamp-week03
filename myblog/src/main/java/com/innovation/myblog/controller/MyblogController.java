package com.innovation.myblog.controller;


import com.innovation.myblog.dto.ExcludepwMyblogDto;
import com.innovation.myblog.models.Myblog;
import com.innovation.myblog.dto.MyblogDto;
import com.innovation.myblog.repository.MyblogRepository;
import com.innovation.myblog.service.MyblogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController  // @Bean
@RequiredArgsConstructor
public class MyblogController {

    private final MyblogRepository myblogRepository;
    private final MyblogService myblogService;

    @PostMapping("/api/posts")
    public Myblog createPost(@RequestBody MyblogDto requestDto) {
        Myblog myblog = new Myblog(requestDto);
        return myblogRepository.save(myblog);
    }


    @GetMapping("/api/posts")
    public List<ExcludepwMyblogDto> getposts() {
        return myblogRepository.test();
    }

    @GetMapping("/api/posts/{id}")
    public Myblog getpost(@PathVariable Long id) {
        Myblog myblog = myblogRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당하는 id가 없습니다")
        );
        return myblog;
    }

    @PutMapping("api/posts/{id}")
    public Long putPosts(@PathVariable Long id, @RequestBody MyblogDto requestDto) {

        return myblogService.update(id, requestDto);
    }


    @DeleteMapping("api/posts/{id}")
    public Long deletePosts(@PathVariable Long id, @RequestBody MyblogDto requsetDto) {
        String password = requsetDto.getPassword();

        Myblog myblog = myblogRepository.findByIdAndPassword(id, password);

        if (myblog != null) {
            myblogRepository.deleteById(id);
            return id;
        } else {
            return -1l;
        }
    }


}
