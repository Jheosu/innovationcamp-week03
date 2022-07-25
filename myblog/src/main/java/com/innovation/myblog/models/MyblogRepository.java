package com.innovation.myblog.models;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MyblogRepository extends JpaRepository<Myblog, Long> {


    Myblog findByIdAndPassword(Long id, String password);
    List <Myblog> findAllByOrderByCreatedAtDesc();

}
