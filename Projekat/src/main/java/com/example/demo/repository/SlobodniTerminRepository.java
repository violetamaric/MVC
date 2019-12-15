package com.example.demo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.SlobodniTermin;

public interface SlobodniTerminRepository extends JpaRepository<SlobodniTermin, Long> {

	Page<SlobodniTermin> findAll(Pageable pageable);
}
