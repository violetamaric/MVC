package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.ZdravstveniKarton;
import com.example.demo.repository.ZdravstveniKartonRepository;

@Service
public class ZdravstveniKartonService {
	
	@Autowired
	private ZdravstveniKartonRepository zkRepository;
	
	public ZdravstveniKarton save(ZdravstveniKarton zk) {
		return zkRepository.save(zk);
	}
	public void delete(ZdravstveniKarton zk) {
		zkRepository.delete(zk);
	}
}
