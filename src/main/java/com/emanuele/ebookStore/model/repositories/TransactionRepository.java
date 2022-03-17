package com.emanuele.ebookStore.model.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.emanuele.ebookStore.model.entity.Transaction;
import com.emanuele.ebookStore.model.entity.User;

public interface TransactionRepository extends JpaRepository<Transaction, Long>{

	List<Transaction> findByUserT(User user);
}
