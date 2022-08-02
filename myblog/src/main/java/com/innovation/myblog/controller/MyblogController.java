package com.innovation.myblog.controller;


import com.innovation.myblog.dto.CommentDto;
import com.innovation.myblog.dto.UpdateMyblogDto;
import com.innovation.myblog.models.Comment;
import com.innovation.myblog.models.Myblog;
import com.innovation.myblog.dto.MyblogDto;
import com.innovation.myblog.repository.MyblogRepository;
import com.innovation.myblog.service.MyblogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController  // @Bean
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class MyblogController {

    private final MyblogRepository myblogRepository;
    private final MyblogService myblogService;



    @GetMapping("") // 전체 조회
    public List<Myblog> getposts() {
        return myblogService.findall();
    }

    @GetMapping("/{id}") // 특정 게시물 조회
    public Myblog getpost(@PathVariable Long id) {
        return myblogRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당하는 id가 없습니다")
        );
    }

    @GetMapping("/comment") // 댓글 조회
    public List<Comment> getcomment() {
        return myblogService.getcomments();
    }

    @GetMapping("/comment/{id}") // 특정 게시물 댓글 조회
    public List<Comment> getidcomments(@PathVariable Long id) {
        return myblogService.getidcomments(id);
    }

}
