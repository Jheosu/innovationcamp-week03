package com.innovation.myblog.controller;


import com.innovation.myblog.dto.CommentDto;
import com.innovation.myblog.dto.MyblogDto;
import com.innovation.myblog.dto.UpdateMyblogDto;
import com.innovation.myblog.models.Comment;
import com.innovation.myblog.models.Myblog;
import com.innovation.myblog.service.MyblogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class MyblogAuthController {

    private final MyblogService myblogService;

    @PostMapping("/post") //생성
    public Myblog createPost(@RequestBody MyblogDto requestDto) {
        return myblogService.createPost(requestDto);
    }

    @PutMapping("/post/{id}") //수정
    public Long putPosts(@PathVariable Long id, @RequestBody UpdateMyblogDto requestDto) {
        return myblogService.update(id, requestDto);
    }

    @DeleteMapping("/post/{id}") //삭제
    public Long deletePosts(@PathVariable Long id) {
        return myblogService.deletepost(id);
    }

    // ------------------댓글--------------------

    @PostMapping("/comment")    //댓글 생성
    public Long postcomments(@RequestBody CommentDto requestDto) {
        return myblogService.createcomment(requestDto);
    }

    @PutMapping("/comment/{id}") //댓글 수정
    public Comment updatecomments(@RequestBody CommentDto requstDto, @PathVariable Long id){
        return myblogService.updatecomment(requstDto,id);
    }

    @DeleteMapping("/comment/{id}") //댓글 삭제
    public Long deletecomment(@PathVariable Long id){
        return myblogService.deletecomment(id);
    }

}
