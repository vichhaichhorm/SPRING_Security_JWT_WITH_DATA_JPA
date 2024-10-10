package com.example.GROUP1_ASSIGMENT_PROJECT.repository;

import com.example.GROUP1_ASSIGMENT_PROJECT.modal.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppUserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
}