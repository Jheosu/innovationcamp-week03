package com.innovation.myblog.models;

import com.innovation.myblog.dto.CommentDto;
import org.junit.jupiter.api.Test;

import javax.persistence.Table;

import static org.junit.jupiter.api.Assertions.*;

class CommentTest {

    @Test
    void 댓글테스트 () {

        //given

        String comment = "댓글테스트";

        Long post_id = 1l;

        String author = "글쓴이 테스트";

        CommentDto commentDto = new CommentDto();

        commentDto.setComment(comment);
        commentDto.setPostid(post_id);
        commentDto.setAuthor(author);


        //when
        Comment commenttest = new Comment(commentDto);

        //then

        assertEquals(author,commenttest.getAuthor());
        assertEquals(post_id,commenttest.getPostid());
        assertEquals(comment,commenttest.getComment());



    }



}