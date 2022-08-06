package com.innovation.myblog.repository;

import com.innovation.myblog.dto.MyblogResponseMapping;
import com.innovation.myblog.models.Myblog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MyblogRepository extends JpaRepository<Myblog, Long> {
    //List <Myblog> findAllByOrderByCreatedAtDesc();
    // 원하는 항목만 보여주도록 매핑
    List<MyblogResponseMapping> findAllByOrderByCreatedAtDesc();

    Myblog findByAuthorAndId(String author, Long id);
    void deleteByAuthorAndId(String author, Long id);

    //    @Query("select new com.innovation.myblog.dto.ExcludepwMyblogDto( m.title,m.content,m.author) from Myblog m ")
//    List<ExcludepwMyblogDto> test();
}

