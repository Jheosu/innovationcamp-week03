package com.innovation.myblog.models;


import com.innovation.myblog.dto.MyblogDto;
import com.innovation.myblog.dto.UpdateMyblogDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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

    @Column
    private String imageUrl;

    @Column(nullable = false)
    private String author;



    public void update (UpdateMyblogDto requestDto) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.imageUrl = requestDto.getImageUrl();
    }

    public Myblog(MyblogDto myblogDto) {
        this.title = myblogDto.getTitle();
        this.content = myblogDto.getContent();
        this.imageUrl = myblogDto.getImageUrl();
        this.author = myblogDto.getAuthor();
    }
}
