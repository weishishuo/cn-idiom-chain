package com.example.idiomchain.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "idioms")
public class Idiom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content", nullable = false, unique = true, length = 20)
    private String content;

    @Column(name = "pinyin", nullable = false, length = 100)
    private String pinyin;

    @Column(name = "first_char", nullable = false, length = 10)
    private String firstChar;

    @Column(name = "last_char", nullable = false, length = 10)
    private String lastChar;
}