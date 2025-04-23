package com.example.blog.controller;

import com.example.blog.model.Post;
import com.example.blog.model.User;
import com.example.blog.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Set;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @GetMapping
    public ResponseEntity<Page<Post>> getAllPosts(Pageable pageable) {
        return ResponseEntity.ok(postService.getAllPosts(pageable));
    }

    @GetMapping
    public ResponseEntity<Post> createPost(@RequestBody Post post, @RequestParam Set<String> tages, @RequestParam User author) {
        return ResponseEntity.ok(postService.createPost(post, tages, author));
    }

    public record PostUpdateRequest(String title, String content, Set<String> tags) {}

    @PutMapping("/{id}")
    public ResponseEntity<Void> updatePost(
            @PathVariable Long id,
            @RequestBody PostUpdateRequest request) {
        postService.updatePost(id, request.title(), request.content(), request.tags());
        return ResponseEntity.noContent().build();
    }
}
