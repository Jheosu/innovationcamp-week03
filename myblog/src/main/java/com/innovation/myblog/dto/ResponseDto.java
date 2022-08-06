package com.innovation.myblog.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDto {
    // 원하는 정보만 보여주도록 매핑
    private List<MyblogResponseMapping> myblogList;

    //private List<Comment> commentList;
}
