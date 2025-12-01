package com.idiom.chain.service.impl;

import com.idiom.chain.entity.Idiom;
import com.idiom.chain.repository.IdiomRepository;
import com.idiom.chain.service.IdiomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class IdiomServiceImpl implements IdiomService {

    private final IdiomRepository idiomRepository;

    @Autowired
    public IdiomServiceImpl(IdiomRepository idiomRepository) {
        this.idiomRepository = idiomRepository;
    }

    @Override
    public boolean isIdiomValid(String idiomContent) {
        if (idiomContent == null || idiomContent.trim().isEmpty()) {
            return false;
        }
        return idiomRepository.existsByContent(idiomContent.trim());
    }

    @Override
    public Optional<Idiom> getNextIdiom(String lastIdiomContent) {
        if (lastIdiomContent == null || lastIdiomContent.trim().isEmpty()) {
            return Optional.empty();
        }

        // Get the last character of the input idiom
        String lastChar = lastIdiomContent.trim().substring(lastIdiomContent.trim().length() - 1);

        // Find a random idiom starting with this character
        return idiomRepository.findRandomIdiomStartingWith(lastChar);
    }

    @Override
    public Optional<Idiom> getRandomStartIdiom() {
        // This is a simplified implementation. In a real application, you might want to
        // have a better way to select a random idiom, perhaps by using a count query first.
        // For now, we'll use a native query approach.
        return idiomRepository.findRandomIdiomStartingWith("%");
    }
}