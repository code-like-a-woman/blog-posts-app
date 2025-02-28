package com.example.demo.dto;

public class UpdatePostDto {
	private final String title;
    private final String content;
    
    
	public UpdatePostDto(String title, String content) {
		super();
		this.title = title;
		this.content = content;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getContent() {
		return content;
	}
}
