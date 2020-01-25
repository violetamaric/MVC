package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Termin;

public interface TerminRepository  extends JpaRepository<Termin, Long> {

}
