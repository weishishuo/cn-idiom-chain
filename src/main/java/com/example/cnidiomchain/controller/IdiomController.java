package com.example.cnidiomchain.controller;

import com.example.cnidiomchain.entity.Idiom;
import com.example.cnidiomchain.service.IdiomService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/idiom")
@CrossOrigin(origins = "http://localhost:5173")
public class IdiomController {
    
    private final IdiomService idiomService;
    
    public IdiomController(IdiomService idiomService) {
        this.idiomService = idiomService;
    }
    
    @PostMapping("/next")
    public ResponseEntity<?> getNextIdiom(@RequestBody Map<String, String> request) {
        String lastIdiom = request.get("lastIdiom");
        
        if (lastIdiom == null || lastIdiom.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "成语不能为空"));
        }
        
        // 验证用户输入的成语是否存在
        if (!idiomService.validateIdiom(lastIdiom)) {
            return ResponseEntity.badRequest().body(Map.of("error", "输入的成语不存在于成语库中"));
        }
        
        // 获取最后一个汉字
        String lastChar = lastIdiom.substring(lastIdiom.length() - 1);
        
        return idiomService.getNextIdiom(lastChar)
                .map(idiom -> ResponseEntity.ok(Map.of(
                        "idiom", idiom.getIdiom(),
                        "pinyin", idiom.getPinyin()
                )))
                .orElseGet(() -> ResponseEntity.ok(Map.of(
                        "error", "找不到可以接龙的下一个成语"
                )));
    }
    
    @PostMapping("/validate")
    public ResponseEntity<Map<String, Boolean>> validateIdiom(@RequestBody Map<String, String> request) {
        String idiom = request.get("idiom");
        boolean exists = idiomService.validateIdiom(idiom);
        return ResponseEntity.ok(Map.of("valid", exists));
    }
}