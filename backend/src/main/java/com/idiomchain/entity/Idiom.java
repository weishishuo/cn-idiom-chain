package com.idiomchain.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
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

    @Column(name = "meaning", columnDefinition = "TEXT")
    private String meaning;

    @Column(name = "first_char", nullable = true, length = 1)
    private String firstChar;

    @Column(name = "last_char", nullable = true, length = 1)
    private String lastChar;
}