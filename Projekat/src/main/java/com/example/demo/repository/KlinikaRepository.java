package com.example.demo.repository;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.Klinika;

public interface KlinikaRepository extends JpaRepository<Klinika, Long>{

	Klinika findByNaziv(String naziv);
	Klinika findByAdresa(String adresa);
	Page<Klinika> findAll(Pageable pageable);
	@Query("select sum(p.cena) from Pregled p where p.status in (3,4,5,6) and p.klinika.id =?1 and p.datum between ?2 and ?3 ")
	Float nedeljniPrihod(Long id, Date pocDatum, Date krajDatum);
	@Query("select count(*) from Pregled p where p.status in (3,4,5,6) and p.klinika.id =?1 and p.datum between ?2 and ?3")
	Integer mesecniNivo(Long id, Date pocDatum, Date krajDatum);
	@Query("select count(*) from Pregled p where p.status in (3,4,5,6) and p.klinika.id =?1 and p.datum between ?2 and ?3 ")
	Integer nedeljniNivo(Long id, Date pocDatum, Date krajDatum);
	@Query("select count(*) from Pregled p where p.status in (3,4,5,6) and p.klinika.id =?1 and p.termin = ?2 and p.datum between ?3 and ?4")
	Integer dnevniNivo(Long id, Integer termin, Date pocDatum, Date krajDatum);
	

}
