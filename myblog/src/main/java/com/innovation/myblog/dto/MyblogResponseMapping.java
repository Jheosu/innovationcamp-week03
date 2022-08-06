package com.innovation.myblog.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.time.LocalDateTime;

// 출력된 json파일의 순서를 지정하는 어노테이션
@JsonPropertyOrder({"id", "title", "content", "author", "likeCount",
        "commentCount", "createdAt", "modifiedAt"})
// 전체 게시글 목록에서 원하는 항목만 보여주도록 하기 위한 인터페이스
public interface MyblogResponseMapping {
    Long getId();

    String getTitle();

    String getContent();

    String getAuthor();

    String getLikeCount();

    String getCommentCount();

    LocalDateTime getCreatedAt();

    LocalDateTime getModifiedAt();
}
