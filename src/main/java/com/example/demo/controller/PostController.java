package com.example.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.NewPostDto;
import com.example.demo.dto.PostDto;
import com.example.demo.dto.UpdatePostDto;
import com.example.demo.service.PostService;

@RestController
@RequestMapping("api/posts")
public class PostController {
	private final PostService service;

	public PostController(PostService service) {
		this.service = service;
	}

	@GetMapping
	public List<PostDto> getAllPosts() {
		return service.getAllPosts();
	}

	@GetMapping("/{id}")
	public ResponseEntity<PostDto> getPostById(@PathVariable long id) {
		return ResponseEntity.ok(service.getPostById(id));
	}

	@PostMapping
	public ResponseEntity<PostDto> createPost(@RequestBody NewPostDto post) {
		PostDto savedPostDto = service.createPost(post);
		return ResponseEntity.status(HttpStatus.CREATED).body(savedPostDto);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletePost(@PathVariable long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();

	}

	@PutMapping("/{id}")
	public ResponseEntity<PostDto> updatePost(@PathVariable long id, @RequestBody UpdatePostDto post) {
		PostDto updatedPostDto = service.updatePost(id, post);
		return ResponseEntity.ok(updatedPostDto);
	}
}
