package com.example.blog.service;

import com.example.blog.model.Post;
import com.example.blog.model.User;
import com.example.blog.repository.PostRepository;
import com.example.blog.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final TagService     tagService;

    @Transactional
    public Post createPost(Post post, Set<String> tagNames, User author) {
        post.setAuthor(author);
        post.setTags(tagService.ensureTagExists(tagNames));

        return postRepository.save(post);
    }

    @Transactional(readOnly = true)
    public Post updatePost(long postId, String title, String content, Set<String> tagNames) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("Post not found"));

        post.setTitle(title);
        post.setContent(content);
        post.setTags(tagService.ensureTagExists(tagNames));

        return postRepository.save(post);
    }

    @Transactional(readOnly = true)
    public Page<Post> getAllPosts(Pageable pageable){
        return postRepository.findAll(pageable);
    }
}
