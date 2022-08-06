package com.innovation.myblog.controller;


import com.innovation.myblog.dto.ResponseDto;
import com.innovation.myblog.models.Comment;
import com.innovation.myblog.models.Myblog;
import com.innovation.myblog.repository.MyblogRepository;
import com.innovation.myblog.service.MyblogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class MyblogController {

    private final MyblogRepository myblogRepository;
    private final MyblogService myblogService;



    @GetMapping("") //  조회
    public ResponseDto getposts() {
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

    @GetMapping("/recomment") // 대댓글 조회
    public List<Comment> getrecomment() {
        return myblogService.getrecomments();
    }

    @GetMapping("/comment/{id}") // 특정 게시물 댓글 조회
    public List<Comment> getidcomments(@PathVariable Long id) {
        return myblogService.getidcomments(id);
    }

    @GetMapping("/recomment/{parentid}") //부모 댓글이 parent id인 대댓글 조회
    public List<Comment> getrecomments(@PathVariable Long parentid) {
        return myblogService.getparentrecomment(parentid);
    }

}
