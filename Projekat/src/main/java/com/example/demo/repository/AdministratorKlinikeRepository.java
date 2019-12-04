package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.AdministratorKlinike;



public interface AdministratorKlinikeRepository extends JpaRepository<AdministratorKlinike, Long> {
	
	@Query("select p from AdministratorKlinike p where p.email = ?1 and p.lozinka = ?2")
	AdministratorKlinike findByEmailAndLozinka(String email, String lozinka);

	
	AdministratorKlinike findByEmail(String email);
	
	

}
