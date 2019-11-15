package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Recept;

public interface ReceptRepository extends JpaRepository<Recept, Long> {

}
