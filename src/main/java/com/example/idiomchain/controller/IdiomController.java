package com.example.idiomchain.controller;

import com.example.idiomchain.entity.Idiom;
import com.example.idiomchain.service.IdiomService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/idiom")
@CrossOrigin(origins = "http://localhost:3000")
public class IdiomController {
    private final IdiomService idiomService;

    public IdiomController(IdiomService idiomService) {
        this.idiomService = idiomService;
    }

    @PostMapping("/next")
    public ResponseEntity<Map<String, Object>> getNextIdiom(
            @RequestBody Map<String, String> request) {
        String userInput = request.get("userInput");
        String previousIdiom = request.get("previousIdiom");

        Map<String, Object> response = new HashMap<>();

        // 校验用户输入是否为四字成语
        if (!idiomService.isValidIdiom(userInput)) {
            response.put("success", false);
            response.put("message", "成语不在库中，请重新输入");
            return ResponseEntity.ok(response);
        }

        // 如果有上一个成语，校验是否能接龙
        if (previousIdiom != null && !previousIdiom.isEmpty()) {
            if (!idiomService.canChain(previousIdiom, userInput)) {
                response.put("success", false);
                response.put("message", "输入成语不是接龙，请重新输入");
                return ResponseEntity.ok(response);
            }
        }

        // 查找下一个接龙成语
        Optional<Idiom> nextIdiom = idiomService.getNextIdiom(userInput);
        if (nextIdiom.isPresent()) {
            response.put("success", true);
            response.put("idiom", nextIdiom.get().getContent());
            response.put("meaning", nextIdiom.get().getMeaning());
        } else {
            response.put("success", false);
            response.put("message", "成语没有下一个接龙");
        }

        return ResponseEntity.ok(response);
    }
}