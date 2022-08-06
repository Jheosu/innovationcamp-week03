package com.innovation.myblog.models;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.LinkedHashSet;

@Getter
@NoArgsConstructor
@Table(name = "member")
@Entity
public class Member {
    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30, unique = true)
    private String nickname;
    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Authority authority;

    // 좋아요 누른 게시글 id 목록
    @Column(nullable = false)
    @Type(type = "json")
    LinkedHashSet<Long> likedMyBlogs;

    // 게시글에 좋아요를 누른 개수
    @Column(nullable = false)
    private int likedBlogsCount;

    // 좋아요를 누른 댓글 id 목록
    @Column(nullable = false)
    @Type(type = "json")
    LinkedHashSet<Long> likedComments;

    // 좋아요를 누른 댓글 개수
    @Column(nullable = false)
    private int likedCommentsCount;

    @Builder
    public Member(String nickname, String password, Authority authority) {
        this.nickname = nickname;
        this.password = password;
        this.authority = authority;
        // 초기화
        this.likedMyBlogs = new LinkedHashSet<>();
        this.likedComments = new LinkedHashSet<>();
    }

    // 게시글 좋아요 등록
    public void addLikedMyBlogs(Long myBlogId) {
        if (likedMyBlogs.contains(myBlogId)) {
            throw new IllegalArgumentException("이미 좋아요를 누른 게시글입니다.");
        }
        this.likedMyBlogs.add(myBlogId);
        likedBlogsCount = this.likedMyBlogs.size();
    }

    // 게시글 좋아요 취소
    public void removeLikedMyBlogs(Long myBlogId) {
        if (!likedMyBlogs.contains(myBlogId)) {
            throw new IllegalArgumentException("좋아요를 누른 적 없는 게시글입니다.");
        }
        this.likedMyBlogs.remove(myBlogId);
        likedBlogsCount = this.likedMyBlogs.size();
    }

    // 댓글 좋아요 등록
    public void addLikedComments(Long commetId) {
        if (likedComments.contains(commetId)) {
            throw new IllegalArgumentException("이미 좋아요를 누른 댓글입니다.");
        }
        this.likedComments.add(commetId);
        likedCommentsCount = this.likedMyBlogs.size();
    }

    // 댓글 좋아요 취소
    public void removeLikedComments(Long commetId) {
        if (!likedComments.contains(commetId)) {
            throw new IllegalArgumentException("좋아요를 누른 적 없는 댓글입니다.");
        }
        this.likedComments.remove(commetId);
        likedCommentsCount = this.likedMyBlogs.size();
    }
}
