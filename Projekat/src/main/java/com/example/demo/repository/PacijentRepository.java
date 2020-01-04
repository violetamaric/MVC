package com.example.demo.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Pacijent;
@Repository
public interface PacijentRepository extends JpaRepository<Pacijent, Long>{
	
	Pacijent findOneByLbo(String lbo);
	Pacijent findByEmail(String email);
	Page<Pacijent> findAll(Pageable pageable);
	@Query("select p from Pacijent p where p.email = ?1 and p.lozinka = ?2")
	Pacijent findByEmailAndLozinka(String email, String lozinka);
	@Query("select k from Pacijent k inner join k.listaKlinika lp where lp.id = ?1")
	List<Pacijent> findByIdKlinike(Long id);

	

}
