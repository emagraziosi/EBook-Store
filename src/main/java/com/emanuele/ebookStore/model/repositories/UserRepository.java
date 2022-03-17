package com.emanuele.ebookStore.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.emanuele.ebookStore.model.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{

	User findByEmail(String email);
}
