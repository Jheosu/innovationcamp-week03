package com.innovation.myblog.service;


import com.innovation.myblog.dto.CommentDto;
import com.innovation.myblog.dto.ResponseDto;
import com.innovation.myblog.dto.UpdateMyblogDto;
import com.innovation.myblog.models.Comment;
import com.innovation.myblog.models.Myblog;
import com.innovation.myblog.dto.MyblogDto;
import com.innovation.myblog.repository.CommentRepository;
import com.innovation.myblog.repository.MyblogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service  //@Bean
public class MyblogService {

    private final MyblogRepository myblogRepository;
    private final CommentRepository commentRepository;
    private final MemberService memberService;
    private final AwsService awsService;


    public ResponseDto findall() {

        return new ResponseDto(myblogRepository.findAllByOrderByCreatedAtDesc(),commentRepository.findAll());
    }


    public void createPost(MyblogDto myblogDto, MultipartFile multipartFile) {

        try {
            String url = awsService.saveImageUrl(multipartFile);
            myblogDto.setImageUrl(url);
            myblogDto.setAuthor(getAuthor());
            Myblog myblog = new Myblog(myblogDto);
            myblogRepository.save(myblog);

        } catch (Exception e) {
            myblogDto.setAuthor(getAuthor());
            Myblog myblog = new Myblog(myblogDto);
            myblogRepository.save(myblog);
        }


    }

    //게시글 수정
    @Transactional
    public Long update(Long id, UpdateMyblogDto requestDto, MultipartFile multipartFile) {
        Myblog myblog = myblogRepository.findByAuthorAndId(getAuthor(), id);
        if (myblog == null) {
            throw new IllegalArgumentException("권한이없거나 해당 id가 존재하지않습니다");
        }
        try {
            String url = awsService.saveImageUrl(multipartFile);
            requestDto.setImageUrl(url);
            myblog.update(requestDto);
        } catch (Exception e) {
            myblog.update(requestDto);
        }

        return id;
    }

    //게시글 삭제
    @Transactional
    public Long deletepost(Long id) {

        Myblog myblog = myblogRepository.findByAuthorAndId(getAuthor(),id);
        if (myblog == null) {
            throw new IllegalArgumentException("삭제하실 권한이 없습니다");
        }

        myblogRepository.deleteByAuthorAndId(getAuthor(),id);
        commentRepository.deleteByPostid(id);

        return id;
    }



    //댓글 생성
    public Long createcomment(CommentDto requestDto) {
        requestDto.setAuthor(getAuthor());
        Long id = requestDto.getPostid();

        myblogRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 게시물이 없습니다")
        );

        Comment comment = new Comment(requestDto);
        commentRepository.save(comment);

        return id;
    }

    //모든 댓글 조회
    public List<Comment> getcomments() {
        return commentRepository.findAllByOrderByCreatedAtDesc();
    }


    public List<Comment> getidcomments(Long id) {
        return commentRepository.findByPostid(id);
    }

    public Long deletecomment(Long id) {

        Comment comment = commentRepository.findById(id).orElseThrow( () -> new IllegalArgumentException("해당하는 댓글이 없습니다"));
        if(!Objects.equals(comment.getAuthor(), getAuthor())) {
            throw new IllegalArgumentException("삭제하실 권한이 없습니다");
        }
        commentRepository.deleteById(id);
        return id;
    }

    @Transactional
    public Comment updatecomment(CommentDto requestDto, Long id) {

        Comment comment = commentRepository.findByAuthorAndIdAndPostid(getAuthor(),id,requestDto.getPostid());

        if(comment == null) {
            throw new IllegalArgumentException("삭제할 권한이 없습니다");
        }

        comment.update(requestDto);

        return comment;
    }

    //현재 접근하는 token의 유저name 반환
    private String getAuthor() {
        return memberService.getMyInfo().getNickname();
    }

}
