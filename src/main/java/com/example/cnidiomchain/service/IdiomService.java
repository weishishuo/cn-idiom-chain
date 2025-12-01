package com.example.cnidiomchain.service;

import com.example.cnidiomchain.entity.Idiom;
import com.example.cnidiomchain.repository.IdiomRepository;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class IdiomService {
    
    private final IdiomRepository idiomRepository;
    
    public IdiomService(IdiomRepository idiomRepository) {
        this.idiomRepository = idiomRepository;
    }
    
    public Optional<Idiom> getNextIdiom(String lastChar) {
        return idiomRepository.findRandomIdiomByFirstChar(lastChar);
    }
    
    public boolean validateIdiom(String idiom) {
        return idiomRepository.existsByIdiom(idiom);
    }
}