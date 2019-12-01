package com.example.demo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.Pacijent;

public interface PacijentRepository extends JpaRepository<Pacijent, Long>{
	
	Pacijent findOneByLbo(String lbo);
	Pacijent findByEmail(String email);
	Page<Pacijent> findAll(Pageable pageable);
	@Query("select p from Pacijent p where p.email = ?1 and p.lozinka = ?2")
	Pacijent findByEmailAndLozinka(String email, String lozinka);
	

}
