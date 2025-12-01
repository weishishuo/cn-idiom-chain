package com.idiomchain.service;

import com.idiomchain.entity.Idiom;

import java.util.Optional;

public interface IdiomChainService {

    /**
     * 验证用户输入的成语是否有效
     * @param idiomContent 成语内容
     * @return 是否有效
     */
    boolean validateIdiom(String idiomContent);

    /**
     * 根据上一个成语的最后一个字，查找下一个成语
     * @param lastCharacter 上一个成语的最后一个字
     * @return 下一个成语，如果找不到则返回Optional.empty()
     */
    Optional<Idiom> findNextIdiom(String lastCharacter);

    /**
     * 获取成语接龙的下一个成语
     * @param currentIdiomContent 当前成语内容
     * @return 下一个成语，如果找不到则返回Optional.empty()
     */
    Optional<Idiom> getNextIdiom(String currentIdiomContent);
}