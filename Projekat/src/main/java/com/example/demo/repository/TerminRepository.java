package com.example.demo.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.Termin;

public interface TerminRepository  extends JpaRepository<Termin, Long> {

	
	@Query("select t from Termin t where t.lekar.id =?1 and t.datumPocetka between ?2 and ?3")
	List<Termin> zauzetiTerminiLekara(Long id, Date pocDatum, Date krajDatum);
	
}
