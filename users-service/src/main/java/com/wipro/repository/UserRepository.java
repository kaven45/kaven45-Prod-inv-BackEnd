package com.wipro.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wipro.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
