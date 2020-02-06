package com.example.demo.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.Pacijent;
import com.example.demo.model.Pregled;

public interface PregledRepository extends JpaRepository<Pregled, Long>{

	@Modifying
	@Query("delete from Pregled p where p.id = ?1")
	void deleteById(Long id);
	
	Page<Pregled> findAll(Pageable pageable);
	
	@Query("select p from Pregled p inner join p.lekar.klinika lp where lp.id = ?1")
	List<Pregled> findByIdKlinike(Long id);
}
