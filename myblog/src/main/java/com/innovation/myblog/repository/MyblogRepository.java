package com.innovation.myblog.repository;

import com.innovation.myblog.dto.ExcludepwMyblogDto;
import com.innovation.myblog.models.Myblog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MyblogRepository extends JpaRepository<Myblog, Long> {
    Myblog findByIdAndPassword(Long id, String password);
    List <Myblog> findAllByOrderByCreatedAtDesc();

    @Query("select new com.innovation.myblog.dto.ExcludepwMyblogDto( m.title,m.content,m.author) from Myblog m ")
    List<ExcludepwMyblogDto> test();
}

