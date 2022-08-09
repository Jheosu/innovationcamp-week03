package com.innovation.myblog.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.innovation.myblog.models.Comment;

import java.time.LocalDateTime;

@JsonPropertyOrder({"postid", "id", "parent", "author", "likeCount",
        "createdAt", "modifiedAt"})
public interface CommentResponseMapping {
    Long getId();

    Long getPostid();

    //Comment getParent();

    String getAuthor();

    String getLikeCount();

    LocalDateTime getCreatedAt();

    LocalDateTime getModifiedAt();
}
