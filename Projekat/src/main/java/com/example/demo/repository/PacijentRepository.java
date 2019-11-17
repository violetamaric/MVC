package com.example.demo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Pacijent;

public interface PacijentRepository extends JpaRepository<Pacijent, Long>{
	
	Pacijent findOneByLbo(String lbo);
	Page<Pacijent> findAll(Pageable pageable);
	

}
