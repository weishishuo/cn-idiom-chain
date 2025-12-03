package com.example.idiomchain.repository;

import com.example.idiomchain.entity.Idiom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IdiomRepository extends JpaRepository<Idiom, Long> {
    Optional<Idiom> findByContent(String content);

    @Query("SELECT i FROM Idiom i WHERE SUBSTRING(i.content, 1, 1) = SUBSTRING(:lastCharacter, 1, 1)")
    List<Idiom> findByFirstCharacter(@Param("lastCharacter") String lastCharacter);
}