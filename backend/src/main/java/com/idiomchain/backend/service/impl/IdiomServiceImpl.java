package com.idiomchain.backend.service.impl;

import com.idiomchain.backend.entity.Idiom;
import com.idiomchain.backend.repository.IdiomRepository;
import com.idiomchain.backend.service.IdiomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class IdiomServiceImpl implements IdiomService {

    @Autowired
    private IdiomRepository idiomRepository;

    @Override
    public boolean isIdiomExist(String idiomName) {
        if (idiomName == null || idiomName.length() != 4) {
            return false;
        }
        return idiomRepository.findByName(idiomName).isPresent();
    }

    @Override
    public boolean canChain(String previousIdiom, String currentIdiom) {
        if (previousIdiom == null || currentIdiom == null) {
            return false;
        }
        if (previousIdiom.length() != 4 || currentIdiom.length() != 4) {
            return false;
        }
        // 检查上一个成语的最后一个字是否等于当前成语的第一个字
        return previousIdiom.charAt(3) == currentIdiom.charAt(0);
    }

    @Override
    public Optional<String> findNextIdiom(String userInputIdiom) {
        if (userInputIdiom == null || userInputIdiom.length() != 4) {
            return Optional.empty();
        }
        // 获取用户输入成语的最后一个字
        String lastChar = String.valueOf(userInputIdiom.charAt(3));
        // 查找以该字开头的随机成语
        Optional<Idiom> nextIdiom = idiomRepository.findRandomByIdiomStartingWith(lastChar);
        return nextIdiom.map(Idiom::getName);
    }
}
