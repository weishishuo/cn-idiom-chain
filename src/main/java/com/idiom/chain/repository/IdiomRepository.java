package com.idiom.chain.repository;

import com.idiom.chain.entity.Idiom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface IdiomRepository extends JpaRepository<Idiom, Long> {

    /**
     * Find an idiom by its content
     */
    Optional<Idiom> findByContent(String content);

    /**
     * Find a random idiom that starts with the given character
     */
    @Query(value = "SELECT * FROM idioms WHERE content LIKE :startChar% ORDER BY RANDOM() LIMIT 1", nativeQuery = true)
    Optional<Idiom> findRandomIdiomStartingWith(@Param("startChar") String startChar);

    /**
     * Check if an idiom exists by its content
     */
    boolean existsByContent(String content);
}