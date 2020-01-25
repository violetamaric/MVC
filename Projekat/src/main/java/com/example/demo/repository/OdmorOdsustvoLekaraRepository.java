package com.example.demo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.OdmorOdsustvoLekar;

public interface OdmorOdsustvoLekaraRepository extends JpaRepository<OdmorOdsustvoLekar, Long>{

	Page<OdmorOdsustvoLekar> findAll(Pageable pageable);
	
}
