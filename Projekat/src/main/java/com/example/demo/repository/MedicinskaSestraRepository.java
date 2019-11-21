package com.example.demo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.MedicinskaSestra;

public interface MedicinskaSestraRepository extends JpaRepository<MedicinskaSestra, Long>{
	
	Page<MedicinskaSestra> findAll(Pageable pageable);
	
	MedicinskaSestra findByEmail(String email);
	
	@Query("select p from MedicinskaSestra p where p.email = ?1 and p.lozinka = ?2")
	MedicinskaSestra findByEmailAndLozinka(String email, String lozinka);

}
