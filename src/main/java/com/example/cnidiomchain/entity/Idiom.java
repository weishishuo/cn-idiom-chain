package com.example.cnidiomchain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "idioms")
@EntityListeners(AuditingEntityListener.class)
public class Idiom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "成语不能为空")
    @Column(nullable = false, unique = true, length = 10)
    private String name;

    @Column(length = 255)
    private String pinyin;

    @Column(length = 500)
    private String meaning;

    @Column(length = 1000)
    private String example;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // 获取成语的第一个字
    public char getFirstChar() {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("成语名称不能为空");
        }
        return name.charAt(0);
    }

    // 获取成语的最后一个字
    public char getLastChar() {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("成语名称不能为空");
        }
        return name.charAt(name.length() - 1);
    }
}
