package com.idiomchain.backend.controller;

import com.idiomchain.backend.service.IdiomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/idiom")
@CrossOrigin(origins = "http://localhost:3000") // 处理跨域问题
public class IdiomController {

    @Autowired
    private IdiomService idiomService;

    /**
     * 处理用户的成语接龙请求
     * @param requestBody 请求体，包含用户输入的成语和上一个成语
     * @return 响应结果
     */
    @PostMapping("/chain")
    public ResponseEntity<Map<String, Object>> chainIdiom(@RequestBody Map<String, String> requestBody) {
        String userInput = requestBody.get("userInput");
        String previousIdiom = requestBody.get("previousIdiom");

        // 检查用户输入是否为空
        if (userInput == null || userInput.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of(
                    "status", "error",
                    "message", "请输入成语"
            ));
        }

        // 检查用户输入是否为四字成语
        if (userInput.trim().length() != 4) {
            return ResponseEntity.badRequest().body(Map.of(
                    "status", "error",
                    "message", "请输入四字成语"
            ));
        }

        String inputIdiom = userInput.trim();

        // 检查用户输入的成语是否在成语库中
        if (!idiomService.isIdiomExist(inputIdiom)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "status", "error",
                    "message", "成语不在库中，请重新输入"
            ));
        }

        // 如果有上一个成语，检查是否能接龙
        if (previousIdiom != null && !previousIdiom.trim().isEmpty()) {
            if (!idiomService.canChain(previousIdiom.trim(), inputIdiom)) {
                return ResponseEntity.badRequest().body(Map.of(
                        "status", "error",
                        "message", "输入成语不是接龙，请重新输入"
                ));
            }
        }

        // 查找下一个接龙的成语
        Optional<String> nextIdiom = idiomService.findNextIdiom(inputIdiom);
        if (nextIdiom.isPresent()) {
            return ResponseEntity.ok(Map.of(
                    "status", "success",
                    "nextIdiom", nextIdiom.get()
            ));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "status", "error",
                    "message", "成语没有下一个接龙"
            ));
        }
    }
}
