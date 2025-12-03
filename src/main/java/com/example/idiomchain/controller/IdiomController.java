package com.example.idiomchain.controller;

import com.example.idiomchain.entity.Idiom;
import com.example.idiomchain.service.IdiomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/idiom")
@CrossOrigin(origins = "http://localhost:3000")
public class IdiomController {
    @Autowired
    private IdiomService idiomService;

    @PostMapping("/next")
    public ResponseEntity<Map<String, Object>> getNextIdiom(@RequestBody Map<String, String> request) {
        Map<String, Object> response = new HashMap<>();
        
        String userInput = request.get("userInput");
        String previousIdiom = request.get("previousIdiom");
        
        // 校验用户输入是否为四字成语
        if (userInput == null || userInput.length() != 4) {
            response.put("success", false);
            response.put("message", "请输入四字成语");
            return ResponseEntity.ok(response);
        }
        
        // 校验用户输入的成语是否在库中
        if (!idiomService.isValidIdiom(userInput)) {
            response.put("success", false);
            response.put("message", "成语不在库中，请重新输入");
            return ResponseEntity.ok(response);
        }
        
        // 校验是否可以接龙（如果有上一个成语）
        if (previousIdiom != null && !previousIdiom.isEmpty()) {
            if (!idiomService.canChain(previousIdiom, userInput)) {
                response.put("success", false);
                response.put("message", "输入成语不是接龙，请重新输入");
                return ResponseEntity.ok(response);
            }
        }
        
        // 获取下一个接龙成语
        Optional<Idiom> nextIdiomOpt = idiomService.getNextIdiom(userInput);
        
        if (nextIdiomOpt.isPresent()) {
            response.put("success", true);
            response.put("idiom", nextIdiomOpt.get().getContent());
            response.put("message", "接龙成功");
        } else {
            response.put("success", false);
            response.put("message", "成语没有下一个接龙");
        }
        
        return ResponseEntity.ok(response);
    }
}