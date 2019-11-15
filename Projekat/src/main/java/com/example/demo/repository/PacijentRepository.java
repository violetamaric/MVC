package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Pacijent;

public interface PacijentRepository extends JpaRepository<Pacijent, Long>{

}
