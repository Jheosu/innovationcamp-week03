package com.innovation.myblog.service;


import com.innovation.myblog.dto.*;
import com.innovation.myblog.models.Comment;
import com.innovation.myblog.models.Myblog;
import com.innovation.myblog.repository.CommentRepository;
import com.innovation.myblog.repository.MyblogRepository;
import com.innovation.myblog.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;

@RequiredArgsConstructor
@Service  //@Bean
public class MyblogService {
    private final MyblogRepository myblogRepository;
    private final CommentRepository commentRepository;
    private final MemberService memberService;
    private final AwsService awsService;

    public ResponseDto findall() {
//        return new ResponseDto(myblogRepository.findAllByOrderByCreatedAtDesc(), commentRepository.findAll());
        // 전체 게시글 목록에서 댓글은 보여주지 않도록 변경 (과제 요구사항)
        return new ResponseDto(myblogRepository.findAllByOrderByCreatedAtDesc());
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
        public Long update (Long id, UpdateMyblogDto requestDto, MultipartFile multipartFile){
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
        public Long deletepost (Long id){

            Myblog myblog = myblogRepository.findByAuthorAndId(getAuthor(), id);

            if (myblog == null) {
                throw new IllegalArgumentException("삭제하실 권한이 없습니다");
            }

            myblogRepository.deleteByAuthorAndId(getAuthor(), id);
            commentRepository.deleteByPostid(id);

            return id;
        }


        //댓글 생성
        public Comment createcomment (CommentDto requestDto){
            requestDto.setAuthor(getAuthor());

            Myblog myblog = myblogRepository.findById(requestDto.getPostid()).orElseThrow(
                    () -> new IllegalArgumentException("해당 게시물이 없습니다")
            );

            // 새 댓글 생성해서 저장하고
            Comment comment = commentRepository.save(new Comment(requestDto));

            // 저장된 댓글의 comment_id를 myblog에 저장한다.
            myblog.addComment(comment.getId());
            myblogRepository.save(myblog);

            return comment;
        }

        //모든 댓글 조회
        public List<Comment> getcomments () {
            return commentRepository.findAllByParentIsNull();
        }

        //대댓글만 조회
        public List<Comment> getrecomments () {
            return commentRepository.findAllByParentIsNotNull();
        }

        //특정 게시물 댓글 조회
        public List<Comment> getidcomments (Long id){
            return commentRepository.findByPostidAndParent(id, null);
        }

        //댓글 삭제
        @Transactional
        public Long deletecomment (Long id){

            Comment comment = commentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당하는 댓글이 없습니다"));

            if (!Objects.equals(comment.getAuthor(), getAuthor())) {
                throw new IllegalArgumentException("삭제하실 권한이 없습니다");
            }
            commentRepository.deleteById(id);
            return id;
        }

        // 대댓글 생성
        public Comment createrecomment (CommentDto requestDto){
            requestDto.setAuthor(getAuthor());

            Comment comment = new Comment(requestDto);

            comment.confirmParent(commentRepository.findById(requestDto.getParentid()).orElseThrow(() -> new RuntimeException("해당하는 부모가 없습니다")));

            commentRepository.save(comment);

            return comment;
        }

        @Transactional
        public Comment updatecomment (CommentDto requestDto, Long id){

            Comment comment = commentRepository.findByAuthorAndIdAndPostid(getAuthor(), id, requestDto.getPostid());

            if (comment == null) {
                throw new IllegalArgumentException("삭제할 권한이 없습니다");
            }

            comment.update(requestDto);

            return comment;
        }

        public List<Comment> getparentrecomment (Long parentid){

            return commentRepository.findByParent(
                    commentRepository.findById(parentid).orElseThrow(
                            () -> new IllegalArgumentException("해당하는 댓글이 없음")
                    )
            );
        }

        //현재 접근하는 token의 유저name 반환
        private String getAuthor () {
            return memberService.getMyInfo().getNickname();
        }


        //내가 쓴 글, 댓글 조회
        public BlogCommentDto findallmyblog() {
            return new BlogCommentDto(myblogRepository.findAllByAuthor(getAuthor()),commentRepository.findAllByAuthor(getAuthor()));
        }

        //내가 좋아요한 글, 댓글 조회
        public BlogCommentDto findallmylikedblog() {
            BlogCommentDto bcDto = new BlogCommentDto();

            Iterator<Long> blog = memberService.getMyInfo().getLikedMyBlogs().iterator();
            while (blog.hasNext()) {
                addBloglistbyId(bcDto, blog.next());
            }
            Iterator<Long> comment = memberService.getMyInfo().getLikedComments().iterator();
            while (comment.hasNext()) {
                addCommentlistbyId(bcDto, comment.next());
            }
            //return new BlogCommentDto(memberService.getMyLiked().getBlogList(), memberService.getMyLiked().getCommentList());
            return bcDto;
        }

        public void addBloglistbyId(BlogCommentDto blogcommentDto, Long id) {
            blogcommentDto.addBloglist((MyblogResponseMapping) myblogRepository.findOneById(id));
        }
        public void addCommentlistbyId(BlogCommentDto blogcommentDto, Long id) {
            blogcommentDto.addCommentlist((CommentResponseMapping) commentRepository.findOneById(id));
        }
    }



