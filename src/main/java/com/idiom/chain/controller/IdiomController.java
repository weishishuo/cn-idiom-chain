package com.idiom.chain.controller;

import com.idiom.chain.entity.Idiom;
import com.idiom.chain.service.IdiomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/idiom")
@CrossOrigin(origins = "http://localhost:3000")
public class IdiomController {

    private final IdiomService idiomService;

    @Autowired
    public IdiomController(IdiomService idiomService) {
        this.idiomService = idiomService;
    }

    /**
     * Check if an idiom is valid (exists in the database)
     */
    @GetMapping("/validate/{content}")
    public ResponseEntity<Map<String, Boolean>> validateIdiom(@PathVariable String content) {
        boolean isValid = idiomService.isIdiomValid(content);
        Map<String, Boolean> response = new HashMap<>();
        response.put("valid", isValid);
        return ResponseEntity.ok(response);
    }

    /**
     * Get the next idiom in the chain based on the last idiom
     */
    @GetMapping("/next/{lastIdiom}")
    public ResponseEntity<?> getNextIdiom(@PathVariable String lastIdiom) {
        Optional<Idiom> nextIdiom = idiomService.getNextIdiom(lastIdiom);
        if (nextIdiom.isPresent()) {
            return ResponseEntity.ok(nextIdiom.get());
        } else {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "找不到下一个成语");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    /**
     * Get a random idiom to start the game
     */
    @GetMapping("/random/start")
    public ResponseEntity<?> getRandomStartIdiom() {
        Optional<Idiom> randomIdiom = idiomService.getRandomStartIdiom();
        if (randomIdiom.isPresent()) {
            return ResponseEntity.ok(randomIdiom.get());
        } else {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "无法获取随机成语");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}