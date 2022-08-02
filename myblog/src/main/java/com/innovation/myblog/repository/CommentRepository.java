package com.innovation.myblog.repository;

import com.innovation.myblog.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {
    List<Comment> findAllByOrderByCreatedAtDesc();
    List<Comment> findByPostid(Long id);


    void deleteByPostid(Long postid);

    Comment findByAuthorAndIdAndPostid(String author,Long id,Long postid);

}
