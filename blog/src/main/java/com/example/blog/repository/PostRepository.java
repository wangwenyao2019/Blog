package com.example.blog.repository;

import com.example.blog.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PostRepository extends JpaRepository<Post, Long> {
    @Query(""" 
                SELECT p FROM Post p
                WHERE LOWER(p.title) LIKE LOWER(CONCAT('%', :keyword, '%'))
                OR EXISTS (
                SELECT t FROM p.tags t
                        WHERE LOWER(t.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
                )
            """)
    Page<Post> searchPosts(String keyword, Pageable pageable);
}
