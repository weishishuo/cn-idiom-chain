package com.idiom.chain.service;

import com.idiom.chain.entity.Idiom;

import java.util.Optional;

public interface IdiomService {

    /**
     * Check if an idiom exists
     */
    boolean isIdiomValid(String idiomContent);

    /**
     * Get the next idiom in the chain based on the last character of the given idiom
     */
    Optional<Idiom> getNextIdiom(String lastIdiomContent);

    /**
     * Get a random idiom to start the game
     */
    Optional<Idiom> getRandomStartIdiom();
}