package com.innovation.myblog.dto;

import com.innovation.myblog.models.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.LinkedHashSet;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class MemberResponseDto {
    private String nickname;
    private LinkedHashSet<Long> likedMyBlogs;
    private LinkedHashSet<Long> likedComments;

    public static MemberResponseDto of(Member member) {
        return new MemberResponseDto(member.getNickname(), member.getLikedMyBlogs(), member.getLikedComments());
    }

}
