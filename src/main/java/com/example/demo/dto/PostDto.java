package com.example.demo.dto;

import java.time.LocalDateTime;

import com.example.demo.model.Post;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema	(description = "Data transfer object for a Post" )
public record PostDto(
		@Schema(description = "Unique identifier for the post", example = "1")
		Long id, 
		
		@Schema(description = "Title of the post", example = "My first post")
		String title, 
		
		@Schema(description = "The content of the post", example = "This is a sample post")
		String content, 
		
		@Schema(description = "Timestamp when the post was created", example = "2024-03-05T14:30:00")
		LocalDateTime createAt
	) {
	public static PostDto fromEntity(Post postEntity) {
		return new PostDto(postEntity.getId(), postEntity.getTitle(), postEntity.getContent(),
				postEntity.getCreatedAt());
	}
}