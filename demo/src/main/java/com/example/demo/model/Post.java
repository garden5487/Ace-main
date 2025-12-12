package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // DB에서 자동 증가
    private Long id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    public Post() {
    }

    // id 없이 쓰는 생성자만 있으면 됨 (필수는 아니지만 편함)
    public Post(String title, String content) {
        this.title = title;
        this.content = content;
    }

    // getter / setter
    public Long getId() {
        return id;
    }

    // id는 JPA가 채워주므로, setter 안 써도 되지만 일단 둬도 무방
    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
