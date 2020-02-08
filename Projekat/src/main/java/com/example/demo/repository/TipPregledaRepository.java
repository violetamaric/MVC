package com.example.demo.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.Klinika;
import com.example.demo.model.TipPregleda;

public interface TipPregledaRepository extends JpaRepository<TipPregleda,Long>{ 
	Page<TipPregleda> findAll(Pageable pageable);
	
	@Query("select tp from TipPregleda tp inner join tp.listaKlinika lk where lk.id = ?1")
	List<TipPregleda> findByIdKlinike(Long id);
	TipPregleda findByNaziv(String naziv);
	
}
