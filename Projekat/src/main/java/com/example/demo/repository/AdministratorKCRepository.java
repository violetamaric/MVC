package com.example.demo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.AdministratorKC;

public interface AdministratorKCRepository extends JpaRepository<AdministratorKC, Long> {
	
	Page<AdministratorKC> findAll(Pageable pageable);
	
	AdministratorKC findByEmail(String email);
	
	
	
//	@Query("select a from AdministratorKC a where a.email =?1 and a.lozinka?2")
//	AdministratorKC fidAdminKCByEmailAndLozinka(String email, String lozinka);
	

	
	
	
}
