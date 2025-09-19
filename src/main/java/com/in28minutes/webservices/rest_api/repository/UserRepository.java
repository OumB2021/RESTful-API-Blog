package com.in28minutes.webservices.rest_api.repository;

import com.in28minutes.webservices.rest_api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
