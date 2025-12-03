package com.idiomchain.backend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "idiom")
public class Idiom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 4, nullable = false, unique = true)
    private String name;

    @Column(name = "pinyin", nullable = false)
    private String pinyin;

    @Column(name = "meaning")
    private String meaning;

    // 构造方法
    public Idiom() {
    }

    public Idiom(String name, String pinyin, String meaning) {
        this.name = name;
        this.pinyin = pinyin;
        this.meaning = meaning;
    }

    // Getter和Setter方法
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
