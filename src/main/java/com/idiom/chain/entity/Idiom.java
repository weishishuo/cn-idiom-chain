package com.idiom.chain.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "idioms")
public class Idiom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content", nullable = false, unique = true, length = 10)
    private String content;

    @Column(name = "pinyin", nullable = false, length = 50)
    private String pinyin;

    @Column(name = "meaning", columnDefinition = "TEXT")
    private String meaning;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }
}