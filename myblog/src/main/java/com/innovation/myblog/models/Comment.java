package com.innovation.myblog.models;


import com.innovation.myblog.dto.CommentDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.LinkedHashMap;

//@TypeDef(name = "json", typeClass = JsonStringType.class)
@Getter
@NoArgsConstructor
@Entity
public class Comment extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private String comment;

    @Column(nullable = false)
    private Long postid;

    // 좋아요 개수
    @Column(nullable = false)
    private int likeCount;

    // 댓글에 좋아요를 누른 사용자 닉네임 목록
    @Column(nullable = false)
    @Type(type = "json")
    LinkedHashMap<Long, String> likedMembers;

    public Comment(CommentDto commentDto) {
        this.author = commentDto.getAuthor();
        this.comment = commentDto.getComment();
        this.postid = commentDto.getPostid();
        // 초기화
        this.likeCount = 0;
        this.likedMembers = new LinkedHashMap<>();
    }

    public void update(CommentDto commentDto) {
        this.comment = commentDto.getComment();
    }

    // 댓글 좋아요 등록
    public void addLikedMember(Long memberId, String author) {
        if (likedMembers.containsKey(memberId)) {
            throw new IllegalArgumentException("이미 좋아요를 누른 댓글입니다.");
        }
        this.likedMembers.put(memberId, author);
        likeCount = this.likedMembers.size();
    }

    // 댓글 좋아요 취소
    public void removeLikedMember(Long memberId) {
        if (!likedMembers.containsKey(memberId)) {
            throw new IllegalArgumentException("좋아요를 누른 적 없는 댓글입니다.");
        }
        this.likedMembers.remove(memberId);
        likeCount = this.likedMembers.size();
    }
}
