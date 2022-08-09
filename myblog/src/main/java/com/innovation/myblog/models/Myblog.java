package com.innovation.myblog.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.innovation.myblog.dto.MyblogDto;
import com.innovation.myblog.dto.UpdateMyblogDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

@NoArgsConstructor
@Getter
@Table(name = "auth_myblog")
@Entity
public class Myblog extends TimeStamped {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column
    private String imageUrl;

    @Column(nullable = false)
    private String author;

    // 댓글 개수
    @Column(nullable = false)
    private int commentCount;

    // 달린 댓글 id 목록
    @Column(nullable = false)
    @Type(type = "json")
    LinkedHashSet<Long> commentIds;

    // 좋아요 개수
    @Column(nullable = false)
    private int likeCount;

    // 좋아요를 누른 사용자 닉네임 목록
    @Column(nullable = false)
    @Type(type = "json")
    LinkedHashMap<Long, String> likedMembers;

    // Myblog 1 : Comment N
    @OneToMany(mappedBy = "myblog", cascade = ALL, orphanRemoval = true)
    private List<Comment> commentList = new ArrayList<>();



    public void update(UpdateMyblogDto requestDto) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.commentCount = requestDto.getCommentCount();
    }

    public Myblog(MyblogDto requestDto) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.author = requestDto.getAuthor();
        this.imageUrl = requestDto.getImageUrl();
        // nullable = false 이기 때문에 초기화
        this.commentCount = 0;
        this.commentIds = new LinkedHashSet<>();
        this.likeCount = 0;
        this.likedMembers = new LinkedHashMap<>();
    }

    // 좋아요 등록
    public void addLikedMember(Long id, String author) {
        if (likedMembers.containsKey(id)) {
            throw new IllegalArgumentException("이미 좋아요를 누른 게시물입니다.");
        }
        likedMembers.put(id, author);
        likeCount = likedMembers.size();
    }

    // 좋아요 취소
    public void removeLikedMember(Long id) {
        if (!likedMembers.containsKey(id)) {
            throw new IllegalArgumentException("좋아요를 누른 적 없는 게시물입니다.");
        }

        likedMembers.remove(id);
        likeCount = likedMembers.size();
    }

    // 댓글 등록
    public void addComment(Long id) {
        if (commentIds.contains(id)) {
            throw new IllegalArgumentException("이미 등록된 댓글입니다.");
        }
        commentIds.add(id);
        commentCount = commentIds.size();
    }

    // 댓글 취소
    public void removeComment(Long id) {
        if (!commentIds.contains(id)) {
            throw new IllegalArgumentException("작성한 적 없는 댓글입니다.");
        }
        commentIds.remove(id);
        commentCount = commentIds.size();
    }

    public void addCommentlist(Comment comment) {
        commentList.add(comment);
    }
}
