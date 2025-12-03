package com.example.idiomchain.service;

import com.example.idiomchain.entity.Idiom;
import com.example.idiomchain.repository.IdiomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class IdiomService {
    @Autowired
    private IdiomRepository idiomRepository;

    private final Random random = new Random();

    /**
     * 检查用户输入的成语是否有效
     */
    public boolean isValidIdiom(String content) {
        return idiomRepository.findByContent(content).isPresent();
    }

    /**
     * 检查两个成语是否可以接龙
     */
    public boolean canChain(String previousIdiom, String currentIdiom) {
        if (previousIdiom == null || previousIdiom.isEmpty()) {
            return true;
        }
        Optional<Idiom> prev = idiomRepository.findByContent(previousIdiom);
        Optional<Idiom> curr = idiomRepository.findByContent(currentIdiom);
        
        if (prev.isPresent() && curr.isPresent()) {
            return prev.get().getLastChar().equals(curr.get().getFirstChar());
        }
        return false;
    }

    /**
     * 获取下一个接龙的成语
     */
    public Optional<Idiom> getNextIdiom(String lastIdiom) {
        Optional<Idiom> idiomOpt = idiomRepository.findByContent(lastIdiom);
        
        if (idiomOpt.isPresent()) {
            String lastChar = idiomOpt.get().getLastChar();
            List<Idiom> candidates = idiomRepository.findByFirstCharAndContentNot(lastChar, lastIdiom);
            
            if (!candidates.isEmpty()) {
                int randomIndex = random.nextInt(candidates.size());
                return Optional.of(candidates.get(randomIndex));
            }
        }
        return Optional.empty();
    }
}