package com.example.cnidiomchain.controller;

import com.example.cnidiomchain.dto.IdiomDTO;
import com.example.cnidiomchain.service.IdiomService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/idioms")
public class IdiomController {

    private final IdiomService idiomService;

    public IdiomController(IdiomService idiomService) {
        this.idiomService = idiomService;
    }

    // 获取所有成语
    @GetMapping
    public ResponseEntity<List<IdiomDTO>> getAllIdioms() {
        List<IdiomDTO> idiomDTOs = idiomService.getAllIdiomDTOs();
        return ResponseEntity.ok(idiomDTOs);
    }

    // 根据名称获取成语
    @GetMapping("/{name}")
    public ResponseEntity<IdiomDTO> getIdiomByName(@PathVariable String name) {
        Optional<IdiomDTO> idiomDTO = idiomService.getIdiomDTOByName(name);
        return idiomDTO.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 成语接龙：根据输入成语获取下一个成语
    @GetMapping("/chain/{currentIdiom}")
    public ResponseEntity<IdiomDTO> getNextIdiom(@PathVariable String currentIdiom) {
        Optional<IdiomDTO> nextIdiomDTO = idiomService.getNextIdiomDTO(currentIdiom);
        return nextIdiomDTO.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 根据最后一个字获取成语列表
    @GetMapping("/last-char/{lastChar}")
    public ResponseEntity<List<IdiomDTO>> getIdiomsByLastChar(@PathVariable char lastChar) {
        List<IdiomDTO> idiomDTOs = idiomService.getIdiomDTOsByLastChar(lastChar);
        return ResponseEntity.ok(idiomDTOs);
    }

    // 根据第一个字获取成语列表
    @GetMapping("/first-char/{firstChar}")
    public ResponseEntity<List<IdiomDTO>> getIdiomsByFirstChar(@PathVariable char firstChar) {
        List<IdiomDTO> idiomDTOs = idiomService.getIdiomDTOsByFirstChar(firstChar);
        return ResponseEntity.ok(idiomDTOs);
    }

    // 批量添加成语
    @PostMapping
    public ResponseEntity<List<IdiomDTO>> addIdioms(@RequestBody List<IdiomDTO> idiomDTOs) {
        List<IdiomDTO> newIdiomDTOs = idiomService.addIdiomDTOs(idiomDTOs);
        return ResponseEntity.ok(newIdiomDTOs);
    }
}
