package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Lek;



public interface LekRepository extends JpaRepository<Lek, Long> {

}
