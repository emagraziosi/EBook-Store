package com.emanuele.ebookStore.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.emanuele.ebookStore.model.entity.VerificationToken;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long>{

	VerificationToken findByToken(String token);
}
