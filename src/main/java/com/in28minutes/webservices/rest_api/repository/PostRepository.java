package com.in28minutes.webservices.rest_api.repository;

import com.in28minutes.webservices.rest_api.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Integer> {
}
