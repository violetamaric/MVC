package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.Pregled;

public interface PregledRepository extends JpaRepository<Pregled, Long>{

	@Modifying
	@Query("delete from Pregled p where p.id = ?1")
	void deleteById(Long id);
}
