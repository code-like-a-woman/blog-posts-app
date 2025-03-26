package com.example.demo.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("api/posts")
public class PostController {
  private final PostService service;

  public PostController(PostService service) {
    this.service = service;
  }
  
  @GetMapping("/thread-name")
  public String getThreadName() {
    return Thread.currentThread().toString();
  }

  @GetMapping
  @Operation(
      summary = "Get all posts", 
      description = "Retrieve a list of all posts",
      responses = {
          @ApiResponse(
              responseCode = "200", 
              description = "succesffully retrieved list",
              content = @Content(mediaType = "application/json",
              schema = @Schema(implementation = PostDto.class)
              )
          )
          }
      )
  public List<PostDto> getAllPosts() {
    return service.getAllPosts();
  }

  @GetMapping("/{id}")
  @Operation(
      summary = "Get a post by ID",
      description = "Retrieve a single post by its ID",
      responses = {
          @ApiResponse(
              responseCode = "200", 
              description = "Post Found",
              content = @Content(mediaType = "application/json",
                  schema = @Schema(implementation = PostDto.class))
          ),
          @ApiResponse(
              responseCode = "404", 
              description = "Post not found",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ProblemDetail.class),
                  examples = @ExampleObject(name = "Not Found Response", 
                    value = """
                      "timestamp": "2025-03-05T15:47:19.841+00:00",
                       "status": 404,
                      "error": "Not Found",
                      "path": "/api/posts/10"
                      """)
              )
          )

      })
  public ResponseEntity<PostDto> getPostById(
      @Parameter(description = "ID of the post to be retrieve", example = "1")
      @PathVariable long id) {
    return ResponseEntity.ok(service.getPostById(id));
  }

  @PostMapping
  @Operation(
      summary = "Create a new post",
      description = "Add a new post to the system",
      responses = {
          @ApiResponse(
              responseCode = "201",
              description = "post created successfully",
              content = @Content(
                    mediaType = "application/json",
                    schema = @Schema (implementation = NewPostDto.class)
                  )
              )
      }
      )
  public ResponseEntity<PostDto> createPost(@RequestBody NewPostDto post) {
    PostDto savedPostDto = service.createPost(post);
    return ResponseEntity.status(HttpStatus.CREATED).body(savedPostDto);
  }

  
  @DeleteMapping("/{id}")
  @Operation(
      summary = "Delete a post",
      description = "Remove a posts by its ID",
      responses = {
          @ApiResponse(
                responseCode = "204",
                description = "Post deleted successfully"
              ),
          @ApiResponse(
                responseCode = "404",
                description = "Post not found",
                content = @Content(
                      mediaType = "application/json",
                      schema = @Schema(implementation = ProblemDetail.class)
                    )
              )
          
      }
   )
  public ResponseEntity<Void> deletePost(
      @Parameter(description = "ID of the post to be deleted", example = "1")
      @PathVariable long id) {
    service.delete(id);
    return ResponseEntity.noContent().build();

  }

  @PutMapping("/{id}")
  @Operation(
      summary = "Update a post",
      description = "Modifies the title and content of an existing post",
      responses = {
          @ApiResponse(
              responseCode = "200",
              description = "Post updated successfully",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = PostDto.class)
              )
          ),
          @ApiResponse(
              responseCode = "404",
              description = "Post not found",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ProblemDetail.class),
                  examples = @ExampleObject(name = "Not Found Response", 
                  value = """
                    "timestamp": "2025-03-05T15:47:19.841+00:00",
                     "status": 404,
                    "error": "Not Found",
                    "path": "/api/posts/10"
                    """)
              )
          )
      }
  )
  public ResponseEntity<PostDto> updatePost(@PathVariable long id,
      @RequestBody UpdatePostDto post) {
    PostDto updatedPostDto = service.updatePost(id, post);
    return ResponseEntity.ok(updatedPostDto);
  }
}
