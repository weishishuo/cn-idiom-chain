package com.example.idiomchain.service.impl;

import com.example.idiomchain.entity.Idiom;
import com.example.idiomchain.repository.IdiomRepository;
import com.example.idiomchain.service.IdiomService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class IdiomServiceImpl implements IdiomService {
    private final IdiomRepository idiomRepository;
    private final Random random = new Random();

    public IdiomServiceImpl(IdiomRepository idiomRepository) {
        this.idiomRepository = idiomRepository;
    }

    @Override
    public Optional<Idiom> findByContent(String content) {
        return idiomRepository.findByContent(content);
    }

    @Override
    public Optional<Idiom> getNextIdiom(String lastIdiomContent) {
        if (lastIdiomContent == null || lastIdiomContent.length() != 4) {
            return Optional.empty();
        }

        String lastCharacter = lastIdiomContent.substring(3, 4);
        List<Idiom> candidates = idiomRepository.findByFirstCharacter(lastCharacter);

        if (candidates.isEmpty()) {
            return Optional.empty();
        }

        // 随机选择一个候选成语
        int randomIndex = random.nextInt(candidates.size());
        return Optional.of(candidates.get(randomIndex));
    }

    @Override
    public boolean isValidIdiom(String content) {
        if (content == null || content.length() != 4) {
            return false;
        }
        return idiomRepository.findByContent(content).isPresent();
    }

    @Override
    public boolean canChain(String previousIdiom, String currentIdiom) {
        if (previousIdiom == null || currentIdiom == null || 
            previousIdiom.length() != 4 || currentIdiom.length() != 4) {
            return false;
        }

        String lastCharOfPrevious = previousIdiom.substring(3, 4);
        String firstCharOfCurrent = currentIdiom.substring(0, 1);

        return lastCharOfPrevious.equals(firstCharOfCurrent);
    }
}