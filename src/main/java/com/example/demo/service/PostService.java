package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.dto.NewPostDto;
import com.example.demo.dto.PostDto;
import com.example.demo.dto.UpdatePostDto;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Post;
import com.example.demo.repository.PostRepository;

@Service
public class PostService {
	private final PostRepository repository;

	public PostService(PostRepository repository) {
		this.repository = repository;
	}

	public List<PostDto> getAllPosts() {
		return repository.findAll().stream().map(PostDto::new).toList();
	}

	public PostDto getPostById(long id) {
		return repository.findById(id).map(PostDto::new)
				.orElseThrow(() -> new ResourceNotFoundException("Post not found with the id " + id));
	}

	public PostDto createPost(NewPostDto post) {
		Post savedPost = repository.save(new Post(post.getTitle(), post.getContent()));
		return new PostDto(savedPost);
	}

	public PostDto updatePost(Long id, UpdatePostDto postDto) {
		return repository.findById(id).map(postToUpdate -> {
			postToUpdate.setTitle(postDto.getTitle());
			postToUpdate.setContent(postDto.getContent());
			return repository.save(postToUpdate);
		}).map(PostDto::new).orElseThrow(() -> new RuntimeException("Post not found with the id " + id));
	}

	public void delete(Long id) {
		repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + id));
		repository.deleteById(id);
	}
}
