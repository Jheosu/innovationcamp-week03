package com.innovation.myblog.models;


import com.innovation.myblog.dto.CommentDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class Comment extends TimeStamped{


    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private String comment;

    @Column(nullable = false)
    private Long postid;

    public Comment(CommentDto commentDto) {
        this.author = commentDto.getAuthor();
        this.comment = commentDto.getComment();
        this.postid = commentDto.getPostid();
    }
    public void update(CommentDto commentDto) {
        this.comment = commentDto.getComment();
    }
}
