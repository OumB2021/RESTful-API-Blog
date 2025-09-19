package com.in28minutes.webservices.rest_api.controllers;

import com.in28minutes.webservices.rest_api.dao.UserDaoService;
import com.in28minutes.webservices.rest_api.exceptions.UserNotFoundException;
import com.in28minutes.webservices.rest_api.model.User;
import com.in28minutes.webservices.rest_api.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class UserController {

    private UserDaoService userDaoService;
    private UserRepository userRepository;

    public UserController(UserDaoService userDaoService, UserRepository userRepository) {
        this.userRepository = userRepository;
        this.userDaoService = userDaoService;
    }


    //GET /users
    @GetMapping(value="/users")
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }


    // GET /users/{id}
    @GetMapping(value = "/users/{id}")
    public EntityModel<User> getUser(@PathVariable int id){
        Optional<User> user = userRepository.findById(id);

        if (user.isEmpty()){
            throw new UserNotFoundException("id: " +  id);
        }
        EntityModel<User> entityModel = EntityModel.of(user.get());

        WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).getAllUsers());

        entityModel.add(link.withRel("all-users"));

        return entityModel;
    }


    // POST /users/{id}
    @PostMapping(value = "/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user){
        User savedUser = userRepository.save(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }


    // DELETE /users/{id}
    @DeleteMapping(value="/users/{id}")
    public void deleteUser(@PathVariable int id){
        userRepository.deleteById(id);
    }
}
