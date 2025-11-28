package com.example.cnidiomchain.dto;

import lombok.Data;

@Data
public class IdiomDTO {
    private Long id;
    private String name;
    private String pinyin;
    private String meaning;
    private String example;
}
