package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.IzvestajOKlinici;
import com.example.demo.model.KlinickiCentar;

public interface IzvestajOKliniciRepository extends JpaRepository<IzvestajOKlinici, Long>  {

}
