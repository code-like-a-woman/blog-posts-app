package com.example.demo.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.bean.override.mockito.MockitoBeans;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import com.example.demo.dto.NewPostDto;
import com.example.demo.dto.PostDto;
import com.example.demo.dto.UpdatePostDto;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.service.PostService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(PostController.class)
@MockitoBeans({@MockitoBean(types = PostService.class)})
class PostControllerTest {
  private static final LocalDateTime fixedCreatedAt = LocalDateTime.of(2024, 5, 21, 4, 30);
  private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
  
  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private PostService postService;

  @InjectMocks
  private PostController controller;

  private final ObjectMapper objectMapper = new ObjectMapper();

  @Test
  void testGetAllPosts() throws Exception {
    List<PostDto> posts =
        List.of(new PostDto(1L, "Title 1", "Content example 1", fixedCreatedAt),
            new PostDto(2L, "Title 2", "Content example 2", fixedCreatedAt),
            new PostDto(3L, "Title 3", "Content example 3", fixedCreatedAt));

    when(postService.getAllPosts()).thenReturn(posts);

    MvcResult mvcResult = mockMvc
        .perform(MockMvcRequestBuilders.get("/api/posts").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk()).andExpect(jsonPath("$.size()").value(posts.size())).andReturn();

    ObjectMapper objectMapper = new ObjectMapper();
    JsonNode jsonResponse = objectMapper.readTree(mvcResult.getResponse().getContentAsString());

    IntStream.range(0, posts.size()).forEach(i -> {
      try {
        PostDto expectedPost = posts.get(i);
        JsonNode actualPost = jsonResponse.get(i);

        assertEquals(expectedPost.id(), actualPost.get("id").asLong());
        assertEquals(expectedPost.title(), actualPost.get("title").asText());
        assertEquals(expectedPost.content(), actualPost.get("content").asText());
        assertEquals(expectedPost.createdAt().format(formatter), actualPost.get("createdAt").asText());
        
      } catch (Exception e) {
        Assertions.fail("Unexpected exception: " + e.getMessage());
      }
    });
  }

  @Test
  void testGetPostById_found() throws Exception {
    PostDto post =
        new PostDto(1L, "test post", "test content", fixedCreatedAt);
    when(postService.getPostById(1L)).thenReturn(post);

    ResultActions results = mockMvc.perform(
        MockMvcRequestBuilders.get("/api/posts/1").contentType(MediaType.APPLICATION_JSON));
    results.andExpect(status().isOk());
    results.andExpect(jsonPath("$.id").value(post.id()));
    results.andExpect(jsonPath("$.title").value(post.title()));
    results.andExpect(jsonPath("$.content").value(post.content()));
    results.andExpect(jsonPath("$.createdAt").value(post.createdAt().format(formatter)));
  }

  @Test
  void testGetPostById_notFound() throws Exception {
    when(postService.getPostById(99L))
        .thenThrow(new ResourceNotFoundException("Post with ID 99 not found"));

    ResultActions results = mockMvc.perform(
        MockMvcRequestBuilders.get("/api/posts/99").contentType(MediaType.APPLICATION_JSON));

    results.andExpect(status().isNotFound()).andExpect(
        result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
        .andExpect(result -> assertEquals("Post with ID 99 not found",
            result.getResolvedException().getMessage()));
  }

  @Test
  void testCreatePost_successful() throws Exception {
    NewPostDto newPost = new NewPostDto("My post", "My random content");
    PostDto savedPost =
        new PostDto(1L, newPost.title(), newPost.content(), fixedCreatedAt);
    when(postService.createPost(newPost)).thenReturn(savedPost);

    ResultActions results = mockMvc.perform(post("/api/posts")
        .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(newPost)));

    results.andExpect(status().isCreated()).andExpect(jsonPath("$.id").value(savedPost.id()));
    results.andExpect(jsonPath("$.title").value(savedPost.title()));
    results.andExpect(jsonPath("$.content").value(savedPost.content()));
    results.andExpect(jsonPath("$.createdAt").value(savedPost.createdAt().format(formatter)));
  }

  @Test
  void testDeletePost_successful() throws Exception {
    Long postId = 1L;

    ResultActions result =
        mockMvc.perform(delete("/api/posts/{id}", postId).contentType(MediaType.APPLICATION_JSON));

    result.andExpect(status().isNoContent());
    verify(postService, times(1)).delete(postId);
  }

  @Test
  void testDeletePost_notFound() throws Exception {
    Long postId = 99L;
    doThrow(new ResourceNotFoundException("Post with ID 99 not found")).when(postService)
        .delete(postId);

    ResultActions result =
        mockMvc.perform(delete("/api/posts/{id}", postId).contentType(MediaType.APPLICATION_JSON));

    result.andExpect(status().isNotFound())
        .andExpect(r -> assertTrue(r.getResolvedException() instanceof ResourceNotFoundException))
        .andExpect(
            r -> assertEquals("Post with ID 99 not found", r.getResolvedException().getMessage()));
  }

  @Test
  void testUpdatePost_success() throws Exception {
    long postId = 1L;
    UpdatePostDto updatePostDto = new UpdatePostDto("Updated Title", "Updated Content");
    PostDto updatedPost = new PostDto(postId, "Updated Title", "Updated Content",
        fixedCreatedAt);

    when(postService.updatePost(eq(postId), any(UpdatePostDto.class))).thenReturn(updatedPost);

    mockMvc
        .perform(MockMvcRequestBuilders.put("/api/posts/{id}", postId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(updatePostDto)))
        .andExpect(status().isOk()).andExpect(jsonPath("$.id").value(postId))
        .andExpect(jsonPath("$.title").value("Updated Title"))
        .andExpect(jsonPath("$.content").value("Updated Content"))
        .andExpect(jsonPath("$.createdAt").value(updatedPost.createdAt().format(formatter)));
  }

  @Test
  void testUpdatePost_notFound() throws Exception {
    long postId = 99L;
    UpdatePostDto updatePostDto = new UpdatePostDto("Updated Title", "Updated Content");

    doThrow(new ResourceNotFoundException("Post with ID 99 not found")).when(postService)
    .updatePost(eq(postId), any(UpdatePostDto.class));

    mockMvc
        .perform(MockMvcRequestBuilders.put("/api/posts/{id}", postId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(updatePostDto)))
        .andExpect(status().isNotFound());
  }
}