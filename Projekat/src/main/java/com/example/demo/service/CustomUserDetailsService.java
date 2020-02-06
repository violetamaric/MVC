package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.model.AdministratorKC;
import com.example.demo.model.AdministratorKlinike;
import com.example.demo.model.Lekar;
import com.example.demo.model.MedicinskaSestra;
import com.example.demo.model.Pacijent;
import com.example.demo.repository.AdministratorKCRepository;
import com.example.demo.repository.AdministratorKlinikeRepository;
import com.example.demo.repository.LekarRepository;
import com.example.demo.repository.MedicinskaSestraRepository;
import com.example.demo.repository.PacijentRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
    private PacijentRepository pacijentRepository;

    @Autowired
    private LekarRepository lekarRepository;

    @Autowired
    private MedicinskaSestraRepository medicinskaSestraRepository;

    @Autowired
    private AdministratorKCRepository adminKCRepository;

    @Autowired
    private AdministratorKlinikeRepository adminKlinikeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;


    @Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    	  
    	  Pacijent pacijent = pacijentRepository.findByEmail(email);
          Lekar lekar = lekarRepository.findByEmail(email);
          MedicinskaSestra medicinskaSestra = medicinskaSestraRepository.findByEmail(email);
          AdministratorKC administratorKlinickogCentra = adminKCRepository.findByEmail(email);
          AdministratorKlinike administratorKlinike = adminKlinikeRepository.findByEmail(email);
          
          if (pacijent != null) {
             return pacijent;
          } else if (lekar != null) {
        	  return lekar;
          } else if (medicinskaSestra != null) {
              return medicinskaSestra;
          } else if (administratorKlinickogCentra != null) {
              return administratorKlinickogCentra;
          } else if (administratorKlinike != null) {
              return administratorKlinike;
          } else {
             throw new UsernameNotFoundException(String.format("Korisnik sa email adresom: '%s' nije pronadjen.", email));
          }
	}
    
}
