package com.idiomchain.controller;

import com.idiomchain.common.ApiResponse;
import com.idiomchain.entity.Idiom;
import com.idiomchain.service.IdiomChainService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/idiom-chain")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class IdiomChainController {

    private final IdiomChainService idiomChainService;

    /**
     * 获取下一个成语
     * @param currentIdiom 当前成语
     * @return 下一个成语
     */
    @GetMapping("/next")
    public ApiResponse<Idiom> getNextIdiom(@RequestParam String currentIdiom) {
        if (currentIdiom == null || currentIdiom.trim().isEmpty()) {
            return ApiResponse.error("当前成语不能为空");
        }

        Optional<Idiom> nextIdiom = idiomChainService.getNextIdiom(currentIdiom.trim());
        if (nextIdiom.isPresent()) {
            return ApiResponse.success("成功获取下一个成语", nextIdiom.get());
        } else {
            return ApiResponse.error(404, "找不到下一个成语");
        }
    }

    /**
     * 验证成语是否有效
     * @param idiom 成语内容
     * @return 验证结果
     */
    @GetMapping("/validate")
    public ApiResponse<Boolean> validateIdiom(@RequestParam String idiom) {
        if (idiom == null || idiom.trim().isEmpty()) {
            return ApiResponse.success(false);
        }

        boolean isValid = idiomChainService.validateIdiom(idiom.trim());
        return ApiResponse.success(isValid);
    }
}