package com.example.idiomchain.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "idioms")
@Data
public class Idiom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content", nullable = false, unique = true, length = 4)
    private String content;

    @Column(name = "pinyin", nullable = false)
    private String pinyin;

    @Column(name = "meaning", nullable = false, columnDefinition = "TEXT")
    private String meaning;
}