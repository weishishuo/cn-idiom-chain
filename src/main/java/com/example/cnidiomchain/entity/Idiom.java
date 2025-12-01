package com.example.cnidiomchain.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "idioms")
public class Idiom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String idiom;
    
    @Column(name = "first_char", nullable = false)
    private String firstChar;
    
    @Column(name = "last_char", nullable = false)
    private String lastChar;
    
    private String pinyin;
}