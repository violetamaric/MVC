package com.example.demo.service;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.Klinika;
import com.example.demo.model.Pacijent;
import com.example.demo.model.Pregled;
import com.example.demo.repository.KlinikaRepository;
import com.example.demo.repository.PacijentRepository;
import com.example.demo.repository.PregledRepository;

@Service
@Transactional(readOnly = true)
public class KlinikaService {
	@Autowired
	private KlinikaRepository klinikaRepository;
	
	@Autowired
	private PacijentRepository pacijentRepository;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private PregledRepository pregledRepository;
	
	public Klinika findOne(Long id) {
		return klinikaRepository.findById(id).orElseGet(null);
	}
	
	public Klinika findByNaziv(String naziv) {
		return klinikaRepository.findByNaziv(naziv);
	}
	
	public Klinika findByAdresa(String adresa) {
		return klinikaRepository.findByAdresa(adresa);
	}
	
	public List<Klinika> findAll() {
		return klinikaRepository.findAll();
	}
	@Transactional(readOnly = false)
	public Klinika save(Klinika klinika) {
		logger.info("> create");
		Klinika k = klinikaRepository.save(klinika);
		logger.info("< create");
		return k;
	}
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void delete(Klinika klinika) {
		klinikaRepository.delete(klinika);
	}
	public Klinika findById(Long id) {
		return klinikaRepository.findById(id).orElseGet(null);
	}
	
	public List<Pacijent>findByIdKlinike(Long id){
		return pacijentRepository.findByIdKlinike(id);
	}

	public float nedeljniPrihod(Long id, Date pocDatum, Date krajDatum) {
		return klinikaRepository.nedeljniPrihod(id, pocDatum, krajDatum);
	}
	public Integer dnevniNivo(Long id, Integer termin, Date pocDatum, Date krajDatum ) {
		return klinikaRepository.dnevniNivo(id, termin, pocDatum, krajDatum);
	}
	public Integer nedeljniNivo(Long id, Date pocDatum, Date krajDatum ) {
		return klinikaRepository.nedeljniNivo(id, pocDatum, krajDatum);
	}
	public Integer mesecniNivo(Long id, Date pocDatum, Date krajDatum ) {
		return klinikaRepository.mesecniNivo(id, pocDatum, krajDatum);
	}

	public List<Pregled> listaPregledaKlinike(Long id){
		return pregledRepository.findByIdKlinike(id);
	}
}


