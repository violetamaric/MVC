package com.example.demo.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.Klinika;
import com.example.demo.model.Pacijent;

public interface KlinikaRepository extends JpaRepository<Klinika, Long>{

	Klinika findByNaziv(String naziv);
	Klinika findByAdresa(String adresa);
	Page<Klinika> findAll(Pageable pageable);
	@Query("select sum(p.cena) from Pregled p where p.status in (3,4,5,6) and p.klinika.id =?1")
	float nedeljniPrihod(Long id);
	

}
