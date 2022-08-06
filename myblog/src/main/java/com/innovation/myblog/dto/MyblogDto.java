package com.innovation.myblog.dto;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
public class MyblogDto {

    private String title;

    private String content;

    private String imageUrl;

    private String author;

}
