package com.example.demo.dto;

import java.time.LocalDateTime;

import com.example.demo.model.Post;

public class PostDto {
    private final Long id;
    private final String title;
    private final String content;
    private final LocalDateTime createdAt;

    public PostDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.createdAt = post.getCreatedAt();
    }

	public Long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getContent() {
		return content;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
}