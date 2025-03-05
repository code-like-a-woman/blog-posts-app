package com.example.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO for creating a new post")
public record NewPostDto (
    @Schema(description = "Title of the post", example = "My first post")
    String title, 
    
    @Schema(description = "Content of the post", example = "This is a sample post")
    String content) {
}
