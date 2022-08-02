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
public class ResponseDto {

    private List<Myblog> myblogList;

    private List<Comment> commentList ;


}
