package com.innovation.myblog.models;


import com.innovation.myblog.dto.MyblogDto;
import com.innovation.myblog.dto.UpdateMyblogDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;


@NoArgsConstructor
@Getter
@Table(name = "auth_myblog")
@Entity
public class Myblog extends TimeStamped{

    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String author;

    public void update (UpdateMyblogDto requestDto) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
    }

    public Myblog(MyblogDto requestDto) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.author = requestDto.getAuthor();
    }


}
