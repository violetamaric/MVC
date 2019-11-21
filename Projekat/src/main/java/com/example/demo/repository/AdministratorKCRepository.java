package com.example.demo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.AdministratorKC;

public interface AdministratorKCRepository extends JpaRepository<AdministratorKC, Long> {
	
	Page<AdministratorKC> findAll(Pageable pageable);
	
	AdministratorKC findByEmail(String email);
	
	@Query("select p from AdministratorKC p where p.email = ?1 and p.lozinka = ?2")
	AdministratorKC findByEmailAndLozinka(String email, String lozinka);
	

	
	
	
}
