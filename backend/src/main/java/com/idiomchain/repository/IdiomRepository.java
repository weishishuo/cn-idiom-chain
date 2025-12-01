package com.idiomchain.repository;

import com.idiomchain.entity.Idiom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IdiomRepository extends JpaRepository<Idiom, Long> {

    /**
     * 根据成语内容查找成语
     * @param content 成语内容
     * @return 成语对象
     */
    Optional<Idiom> findByContent(String content);

    /**
     * 根据首字查找成语
     * @param firstChar 首字
     * @return 成语列表
     */
    List<Idiom> findByFirstChar(String firstChar);

    /**
     * 根据首字查找成语（使用JPQL）
     * @param firstChar 首字
     * @return 成语列表
     */
    @Query("SELECT i FROM Idiom i WHERE i.firstChar = :firstChar ORDER BY FUNCTION('RANDOM')")
    List<Idiom> findRandomIdiomsByFirstChar(@Param("firstChar") String firstChar);

    /**
     * 检查成语是否存在
     * @param content 成语内容
     * @return 是否存在
     */
    boolean existsByContent(String content);
}