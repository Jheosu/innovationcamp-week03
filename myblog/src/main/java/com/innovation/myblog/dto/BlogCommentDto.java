package com.innovation.myblog.dto;

import com.innovation.myblog.models.Comment;
import com.innovation.myblog.models.Myblog;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@NoArgsConstructor
@Getter
@Setter
//@AllArgsConstructor
public class BlogCommentDto {

    private List<Myblog> blogList;
    private List<Comment> commentList;

    public BlogCommentDto(List<Myblog> blogs, List<Comment> comments) {
        this.blogList = blogs;
        this.commentList = comments;
    }

    public void addBloglist(Myblog blog) {
        if (this.blogList == null) {
            this.blogList =  new ArrayList<>();
        }
        this.blogList.add(blog);
    }

    public void addCommentlist(Comment comment) {
        if (this.commentList == null) {
            this.commentList =  new ArrayList<>();
        }
        this.commentList.add(comment);
    }
}
