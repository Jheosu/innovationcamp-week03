package com.innovation.myblog.controller;


import com.innovation.myblog.dto.CommentDto;
import com.innovation.myblog.dto.MyResponseDto;
import com.innovation.myblog.dto.MyblogDto;
import com.innovation.myblog.dto.UpdateMyblogDto;
import com.innovation.myblog.exception.RestApiException;
import com.innovation.myblog.models.Comment;
import com.innovation.myblog.service.MyblogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class MyblogAuthController {

    private final MyblogService myblogService;

    @PostMapping("/post") //생성
    public void createPost(@RequestPart(value = "file", required = false) MultipartFile multipartFile,
                           @RequestPart(value = "request") MyblogDto requestDto) {

            myblogService.createPost(requestDto, multipartFile);
    }

    @PutMapping("/post/{id}") //수정
    public Long putPosts(@PathVariable Long id,
                         @RequestPart("request") UpdateMyblogDto requestDto,
                         @RequestPart(value = "file", required = false) MultipartFile multipartFile) {
        return myblogService.update(id, requestDto, multipartFile);
    }

    @DeleteMapping("/post/{id}") //삭제
    public Long deletePosts(@PathVariable Long id) {
        return myblogService.deletepost(id);
    }

    // ------------------댓글--------------------

    @PostMapping("/comment")    //댓글 생성
    public Comment postcomments(@RequestBody CommentDto requestDto) {
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


    // ------------------대댓글--------------------

    @PostMapping("/recomment")    //대댓글 생성
    public Comment postrecomments(@RequestBody CommentDto requestDto) {
        return myblogService.createrecomment(requestDto);
    }

    @ExceptionHandler({ IllegalArgumentException.class })
    public ResponseEntity<RestApiException> handleException(IllegalArgumentException ex) {
        RestApiException restApiException = new RestApiException();
        restApiException.setHttpStatus(HttpStatus.UNAUTHORIZED);
        restApiException.setErrorMessage(ex.getMessage());
        return new ResponseEntity<>(
                // HTTP body
                restApiException,
                // HTTP status code
                HttpStatus.UNAUTHORIZED
        );
    }

    @GetMapping("/mypage")  //마이페이지
    public MyResponseDto mypage() {
        MyResponseDto mypageList = new MyResponseDto();
        mypageList.setMyblogList(myblogService.findallmyblog().getBlogList());
        mypageList.setMycommentList(myblogService.findallmyblog().getCommentList());
        mypageList.setMylikedblogList(myblogService.findallmylikedblog().getBlogList());
        mypageList.setMylikedcommentList(myblogService.findallmylikedblog().getCommentList());
        return mypageList;
    }
}
