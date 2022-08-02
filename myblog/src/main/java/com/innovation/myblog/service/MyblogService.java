package com.innovation.myblog.service;


import com.innovation.myblog.dto.CommentDto;
import com.innovation.myblog.dto.UpdateMyblogDto;
import com.innovation.myblog.models.Comment;
import com.innovation.myblog.models.Myblog;
import com.innovation.myblog.dto.MyblogDto;
import com.innovation.myblog.repository.CommentRepository;
import com.innovation.myblog.repository.MyblogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service  //@Bean
public class MyblogService {

    private final MyblogRepository myblogRepository;

    private final CommentRepository commentRepository;
    private final MemberService memberService;


    public List<Myblog> findall() {
        return myblogRepository.findAllByOrderByCreatedAtDesc();
    }


    public Myblog createPost(MyblogDto requestDto) {

        requestDto.setAuthor(getAuthor());

        Myblog myblog = new Myblog(requestDto);

        return myblogRepository.save(myblog);
    }



    //게시글 수정
    @Transactional
    public Long update(Long id, UpdateMyblogDto requestDto) {
        Myblog myblog = myblogRepository.findByAuthorAndId(getAuthor(),id);

        if(myblog == null) {
            throw new RuntimeException("권한이없거나 해당 id가 존재하지않습니다");
        }

        myblog.update(requestDto);

        return id;
    }

    //게시글 삭제
    @Transactional
    public Long deletepost(Long id) {

        Myblog myblog = myblogRepository.findByAuthorAndId(getAuthor(),id);
        if (myblog == null) {
            throw new RuntimeException("삭제하실 권한이 없습니다");
        }

        myblogRepository.deleteByAuthorAndId(getAuthor(),id);
        commentRepository.deleteByPostid(id);

        return id;
    }



    //댓글 생성
    public Long createcomment(CommentDto requestDto) {
        requestDto.setAuthor(getAuthor());
        Long id = requestDto.getPostid();
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
        commentRepository.deleteById(id);
        return id;
    }

    @Transactional
    public Comment updatecomment(CommentDto requestDto, Long id) {
        Comment comment = commentRepository.findByAuthorAndIdAndPostid(getAuthor(),id,requestDto.getPostid());

        if(comment == null) {
            throw new RuntimeException("삭제할 권한이 없습니다");
        }

        comment.update(requestDto);

        return comment;
    }

    private String getAuthor() {
        return memberService.getMyInfo().getNickname();
    }
}
