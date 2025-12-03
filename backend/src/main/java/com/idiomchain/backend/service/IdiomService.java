package com.idiomchain.backend.service;

import java.util.Optional;

public interface IdiomService {

    /**
     * 检查用户输入的成语是否在成语库中
     * @param idiomName 成语名称
     * @return 成语是否存在
     */
    boolean isIdiomExist(String idiomName);

    /**
     * 检查两个成语是否能接龙
     * @param previousIdiom 上一个成语
     * @param currentIdiom 当前成语
     * @return 是否能接龙
     */
    boolean canChain(String previousIdiom, String currentIdiom);

    /**
     * 根据用户输入的成语找到下一个接龙的成语
     * @param userInputIdiom 用户输入的成语
     * @return 下一个接龙的成语
     */
    Optional<String> findNextIdiom(String userInputIdiom);
}
