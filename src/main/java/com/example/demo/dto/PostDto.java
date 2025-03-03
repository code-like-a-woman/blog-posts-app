package com.example.demo.dto;

import java.time.LocalDateTime;

import com.example.demo.model.Post;

public record PostDto(Long id, String title, String content, LocalDateTime createAt) {
	public static PostDto fromEntity(Post postEntity) {
		return new PostDto(postEntity.getId(), postEntity.getContent(), postEntity.getContent(),
				postEntity.getCreatedAt());
	}
}