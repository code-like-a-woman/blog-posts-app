package com.example.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO for updating an existing post")
public record UpdatePostDto(
    @Schema(description = "Updated title of the post", example = "Updated post title")
    String title, 
    
    @Schema(description = "Updated content of the post", example = "This is the updated content of the post.")
    String content
    ) 
{}
