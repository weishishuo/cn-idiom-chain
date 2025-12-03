package com.example.idiomchain.service;

import com.example.idiomchain.entity.Idiom;

import java.util.Optional;

public interface IdiomService {
    Optional<Idiom> findByContent(String content);
    Optional<Idiom> getNextIdiom(String lastIdiomContent);
    boolean isValidIdiom(String content);
    boolean canChain(String previousIdiom, String currentIdiom);
}