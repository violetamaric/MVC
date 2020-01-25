package com.example.demo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.OdmorOdsustvoMedicinskaSestra;

public interface OdmorOdsustvoMedicinskeSestreRepository extends JpaRepository<OdmorOdsustvoMedicinskaSestra, Long> {

	Page<OdmorOdsustvoMedicinskaSestra> findAll(Pageable pageable);
	
}
