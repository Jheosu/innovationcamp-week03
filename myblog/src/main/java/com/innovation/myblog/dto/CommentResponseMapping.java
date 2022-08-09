package com.innovation.myblog.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.innovation.myblog.models.Comment;

import java.time.LocalDateTime;

@JsonPropertyOrder({"postid", "id", "parent", "author","comment", "likeCount",
        "createdAt", "modifiedAt"})
public interface CommentResponseMapping {
    Long getId();

    Long getPostid();

    //Comment getParent();

    String getAuthor();

    String getComment();

    String getLikeCount();

    LocalDateTime getCreatedAt();

    LocalDateTime getModifiedAt();
}
