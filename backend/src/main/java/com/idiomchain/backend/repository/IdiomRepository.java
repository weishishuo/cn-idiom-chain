package com.idiomchain.backend.repository;

import com.idiomchain.backend.entity.Idiom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IdiomRepository extends JpaRepository<Idiom, Long> {

    /**
     * 根据成语名称查询成语
     * @param name 成语名称
     * @return 成语对象
     */
    Optional<Idiom> findByName(String name);

    /**
     * 根据成语的第一个字查询成语
     * @param firstChar 成语的第一个字
     * @return 成语对象
     */
    @Query(value = "SELECT * FROM idiom WHERE SUBSTRING(name, 1, 1) = :firstChar ORDER BY RANDOM() LIMIT 1", nativeQuery = true)
    Optional<Idiom> findRandomByIdiomStartingWith(@Param("firstChar") String firstChar);
}
