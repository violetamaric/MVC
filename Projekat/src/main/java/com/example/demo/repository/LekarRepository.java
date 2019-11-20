package com.example.demo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Lekar;
import com.example.demo.model.Pacijent;

public interface LekarRepository extends JpaRepository<Lekar, Long>{

	Page<Lekar> findAll(Pageable pageable);
}
