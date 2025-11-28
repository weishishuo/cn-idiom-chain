package com.example.cnidiomchain.repository;

import com.example.cnidiomchain.entity.Idiom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IdiomRepository extends JpaRepository<Idiom, Long> {

    // 根据成语名称查询成语
    Optional<Idiom> findByName(String name);

    // 查询所有成语
    List<Idiom> findAll();

    // 根据最后一个字查询成语（用于成语接龙）
    @Query("SELECT i FROM Idiom i WHERE SUBSTRING(i.name, LENGTH(i.name), 1) = :lastChar")
    List<Idiom> findByLastChar(@Param("lastChar") char lastChar);

    // 根据第一个字查询成语
    @Query("SELECT i FROM Idiom i WHERE SUBSTRING(i.name, 1, 1) = :firstChar")
    List<Idiom> findByFirstChar(@Param("firstChar") char firstChar);

    // 检查成语是否存在
    boolean existsByName(String name);
}
