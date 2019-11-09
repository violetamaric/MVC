package service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repository.PacijentRepository;

@Service
public class PacijentService {

	@Autowired
	private PacijentRepository pacijentRepository;
}
