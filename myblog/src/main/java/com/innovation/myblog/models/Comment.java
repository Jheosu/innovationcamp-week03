package com.innovation.myblog.models;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.innovation.myblog.dto.CommentDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@NoArgsConstructor
@Getter
@Entity
public class Comment extends TimeStamped{


    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private String comment;

    @Column(nullable = false)
    private Long postid;

    @JsonBackReference
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "parent_id")
    private Comment parent;

    @JsonManagedReference
    @OneToMany(mappedBy = "parent",cascade = CascadeType.REMOVE)
    private List<Comment> childList = new ArrayList<>();


    public Comment(CommentDto commentDto) {
        this.author = commentDto.getAuthor();
        this.comment = commentDto.getComment();
        this.postid = commentDto.getPostid();
    }
    public void update(CommentDto commentDto) {
        this.comment = commentDto.getComment();
    }

    public void addChild(Comment child){
        childList.add(child);
    }

    public void confirmParent(Comment parent){
        this.parent = parent;
        parent.addChild(this);
    }


}
