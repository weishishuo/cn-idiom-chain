package com.example.cnidiomchain.repository;

import com.example.cnidiomchain.entity.Idiom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

public interface IdiomRepository extends JpaRepository<Idiom, Long> {
    
    @Query("SELECT i FROM Idiom i WHERE i.firstChar = :startChar ORDER BY FUNCTION('RANDOM') LIMIT 1")
    Optional<Idiom> findRandomIdiomByFirstChar(@Param("startChar") String startChar);
    
    @Query("SELECT CASE WHEN COUNT(i) > 0 THEN true ELSE false END FROM Idiom i WHERE i.idiom = :idiom")
    boolean existsByIdiom(@Param("idiom") String idiom);
}