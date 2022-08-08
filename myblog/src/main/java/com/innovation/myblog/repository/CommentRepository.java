package com.innovation.myblog.repository;

import com.innovation.myblog.models.Comment;
import com.innovation.myblog.models.Myblog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {
//    List<Comment> findAllByOrderByCreatedAtDesc();
    List<Comment> findByPostidAndParent(Long id,Comment parent);
    List<Comment> findAllByParentIsNotNull();
    List<Comment> findAllByParentIsNull();
    List<Comment> findByParent(Comment comment);
    List<Comment> findAllByAuthor(String author);
    void deleteByPostid(Long postid);
    Comment findByAuthorAndIdAndPostid(String author,Long id,Long postid);




}
