package com.example.idiomchain.repository;

import com.example.idiomchain.entity.Idiom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IdiomRepository extends JpaRepository<Idiom, Long> {
    Optional<Idiom> findByContent(String content);

    List<Idiom> findByFirstChar(String firstChar);

    @Query("SELECT i FROM Idiom i WHERE i.firstChar = :firstChar AND i.content != :excludedContent")
    List<Idiom> findByFirstCharAndContentNot(@Param("firstChar") String firstChar, @Param("excludedContent") String excludedContent);
}