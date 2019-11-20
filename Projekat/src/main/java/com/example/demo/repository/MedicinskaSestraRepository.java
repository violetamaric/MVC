package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.MedicinskaSestra;
import com.example.demo.model.Pacijent;

public interface MedicinskaSestraRepository extends JpaRepository<MedicinskaSestra, Long>{
	
	@Query("select p from MedicinskaSestra p where p.email = ?1 and p.lozinka = ?2")
	MedicinskaSestra findByEmailAndLozinka(String email, String lozinka);

}
