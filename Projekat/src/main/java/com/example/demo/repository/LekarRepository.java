package com.example.demo.repository;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.Lekar;
import com.example.demo.model.Pacijent;

public interface LekarRepository extends JpaRepository<Lekar, Long>{

	Page<Lekar> findAll(Pageable pageable);
	
	@Query("select p from Lekar p where p.email = ?1 and p.lozinka = ?2")
	Lekar findByEmailAndLozinka(String email, String lozinka);
	
	Lekar findByEmail(String email);

	
}
