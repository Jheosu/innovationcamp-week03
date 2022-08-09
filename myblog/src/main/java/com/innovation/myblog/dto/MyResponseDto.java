package com.innovation.myblog.dto;

import com.innovation.myblog.models.Comment;
import com.innovation.myblog.models.Myblog;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class MyResponseDto {

    private List<MyblogResponseMapping> myblogList;

    private List<CommentResponseMapping> mycommentList;

    private List<MyblogResponseMapping> mylikedblogList;

    private List<CommentResponseMapping> mylikedcommentList ;
}
