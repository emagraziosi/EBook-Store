package com.emanuele.ebookStore.model.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.emanuele.ebookStore.model.entity.BooksInStore;

public interface BooksInStoreRepository extends JpaRepository<BooksInStore, Long>{

	BooksInStore findByTitolo(String titolo);
	
	List<BooksInStore> findByTitoloContaining(String titolo);
	
	BooksInStore findByAutore(String autore);
	
	List<BooksInStore> findByAutoreContaining(String autore);
	
	List<BooksInStore> findByGenere(String genere);
	
	List<BooksInStore> findTop5ByOrderByNumeroAcquistiDesc();
}
