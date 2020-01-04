package com.example.demo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.KlinickiCentar;
@Repository
public interface KlinickiCentarRepository extends JpaRepository<KlinickiCentar, Long> {
	
	Page<KlinickiCentar> findAll(Pageable pageable);
}
