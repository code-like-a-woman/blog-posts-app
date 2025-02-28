package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Post;
import com.example.demo.repository.PostRepository;

@Service
public class PostService {

	private final PostRepository repository;

	// As long as the class has one constructor Spring injects the dependency
	// without the need of Autowired.
	public PostService(PostRepository repository) {
		this.repository = repository;
	}

	public List<Post> getAllPosts() {
		return repository.findAll();
	}

	public Post getPostById(long id) {
		return repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Post not found with the id " + id));
	}

	public Post createPost(Post post) {
		Post savedPost = repository.save(post);
		System.out.println("savedPost :  " + savedPost);
		return savedPost;
	}

	public Post updatePost(Long id, Post updatedPost) {
		return repository.findById(id).map(post -> {
			post.setTitle(updatedPost.getTitle());
			post.setContent(updatedPost.getContent());
			return repository.save(post);
		}).orElseThrow(() -> new RuntimeException("Post not found with the id " + id));
	}

	public void delete(Long id) {
		repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + id));
		repository.deleteById(id);
	}
}
