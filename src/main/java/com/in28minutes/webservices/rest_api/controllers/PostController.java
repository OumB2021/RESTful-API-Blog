package com.in28minutes.webservices.rest_api.controllers;

import com.in28minutes.webservices.rest_api.exceptions.UserNotFoundException;
import com.in28minutes.webservices.rest_api.model.Post;
import com.in28minutes.webservices.rest_api.model.User;
import com.in28minutes.webservices.rest_api.repository.PostRepository;
import com.in28minutes.webservices.rest_api.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class PostController {
    protected UserRepository userRepository;
    protected PostRepository postRepository;
    public PostController(UserRepository userRepository, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    @GetMapping("/users/{id}/posts")
    public List<Post> getAllPosts(@PathVariable int id){

        Optional<User> user = userRepository.findById(id);

        if (user.isEmpty()){
            throw new UserNotFoundException("id: " + id);
        }

        return user.get().getPosts();
    }

    @PostMapping("/users/{id}/posts")
    public ResponseEntity<Post> createNewPost(@PathVariable int id, @Valid @RequestBody Post post){
        Optional<User> user = userRepository.findById(id);

        if (user.isEmpty()){
            throw new UserNotFoundException("Id: " +  id);
        }

        post.setUser(user.get());

        System.out.println(post);

        Post savedPost = postRepository.save(post);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedPost.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping("/users/{id}/posts/{post_id}")
    public Optional<Post> getPostById(@PathVariable int id, @PathVariable int post_id){

        Optional<User> user = userRepository.findById(id);

        if (user.isEmpty()){
            throw new UserNotFoundException("Id: " +  id);
        }

        return postRepository.findById(post_id);

        
    }
}
