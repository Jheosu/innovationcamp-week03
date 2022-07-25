package com.innovation.myblog.controller;


import com.innovation.myblog.models.Myblog;
import com.innovation.myblog.models.MyblogDto;
import com.innovation.myblog.models.MyblogRepository;
import com.innovation.myblog.service.MyblogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MyblogController {

    private final MyblogRepository myblogRepository;
    private final MyblogService myblogService;

    @PostMapping("/api/posts")
    public Myblog createPost(@RequestBody MyblogDto requestDto) {
        Myblog myblog = new Myblog(requestDto);
        return myblogRepository.save(myblog);
    }

    @PostMapping("/api/posts/{id}")
    public String checkpassword(@PathVariable Long id,@RequestBody MyblogDto requestDto) {
        String password = requestDto.getPassword();
        Myblog myblog = myblogRepository.findByIdAndPassword(id,password);

        if (myblog != null) {
            return "비밀번호가 맞습니다";
        }
        else {
            return "비밀번호가 틀립니다";
        }
    }



    @GetMapping("/api/posts")
    public List<Myblog> getposts() {
        return myblogRepository.findAll();
    }

    @PutMapping("api/posts/{id}")
    public Long putPosts(@PathVariable Long id,@RequestBody MyblogDto requestDto) {
        return myblogService.update(id, requestDto);
    }

    @DeleteMapping("api/posts/{id}")
    public String deletePosts(@PathVariable Long id,@RequestBody MyblogDto requsetDto) {
        String password = requsetDto.getPassword();

        Myblog myblog = myblogRepository.findByIdAndPassword(id,password);

        if (myblog != null) {
            myblogRepository.deleteById(id);
            return "게시글이 삭제 되었습니다";
        }

        else {
            return "비밀번호가 틀립니다";
        }
    }
}
