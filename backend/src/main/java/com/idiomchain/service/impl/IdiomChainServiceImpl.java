package com.idiomchain.service.impl;

import com.idiomchain.entity.Idiom;
import com.idiomchain.repository.IdiomRepository;
import com.idiomchain.service.IdiomChainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class IdiomChainServiceImpl implements IdiomChainService {

    private final IdiomRepository idiomRepository;

    @Override
    public boolean validateIdiom(String idiomContent) {
        if (!StringUtils.hasText(idiomContent)) {
            return false;
        }
        // 验证成语是否为4个字
        if (idiomContent.length() != 4) {
            return false;
        }
        // 验证成语是否存在于数据库中
        return idiomRepository.existsByContent(idiomContent);
    }

    @Override
    public Optional<Idiom> findNextIdiom(String lastChar) {
        if (!StringUtils.hasText(lastChar) || lastChar.length() != 1) {
            return Optional.empty();
        }
        // 根据首字查找成语，随机返回一个
        List<Idiom> idioms = idiomRepository.findRandomIdiomsByFirstChar(lastChar);
        return idioms.stream().findFirst();
    }

    @Override
    public Optional<Idiom> getNextIdiom(String currentIdiomContent) {
        // 验证当前成语是否有效
        if (!validateIdiom(currentIdiomContent)) {
            return Optional.empty();
        }
        // 获取当前成语的最后一个字
        String last_char = currentIdiomContent.substring(currentIdiomContent.length() - 1);
        // 查找下一个成语
        return findNextIdiom(last_char);
    }
}