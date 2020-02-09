package com.example.demo.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.AdministratorKlinikeDTO;
import com.example.demo.dto.LekarDTO;
import com.example.demo.dto.OperacijaDTO;
import com.example.demo.dto.PacijentDTO;
import com.example.demo.dto.PregledDTO;
import com.example.demo.dto.SalaDTO;
import com.example.demo.dto.TerminDTO;
import com.example.demo.model.AdministratorKlinike;
import com.example.demo.model.Klinika;
import com.example.demo.model.Lekar;
import com.example.demo.model.OdmorOdsustvoLekar;
import com.example.demo.model.Operacija;
import com.example.demo.model.Pacijent;
import com.example.demo.model.Sala;
import com.example.demo.model.Termin;
import com.example.demo.service.EmailService;
import com.example.demo.service.KlinikaService;
import com.example.demo.service.LekarService;
import com.example.demo.service.OperacijaService;
import com.example.demo.service.PacijentService;
import com.example.demo.service.SalaService;
import com.example.demo.service.TerminService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(value = "/api/operacije", produces = MediaType.APPLICATION_JSON_VALUE)
public class OperacijaController {
	@Autowired
	private OperacijaService operacijaService;
	@Autowired
	private LekarService lekarService;
	@Autowired
	private PacijentService pacijentService;
	@Autowired
	private KlinikaService klinikaService;
	@Autowired
	private SalaService salaService;
	@Autowired
	private EmailService emailService;
	@Autowired
	private TerminService terminService;

	private Logger logger = LoggerFactory.getLogger(UserController.class);

	@GetMapping(value = "/{id}")
	@PreAuthorize("hasAuthority('ADMIN_KLINIKE')")
	public ResponseEntity<OperacijaDTO> getOperacija(@PathVariable Long id) {

		Operacija operacija = operacijaService.findOne(id);
		System.out.println(operacija);

		// studen must exist
		if (operacija == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(new OperacijaDTO(operacija), HttpStatus.OK);
	}

	@GetMapping(value = "/all")
	public ResponseEntity<List<OperacijaDTO>> getAll() {

		List<Operacija> operacije = operacijaService.findAll();

		// convert students to DTOs
		List<OperacijaDTO> operacijeDTO = new ArrayList<>();
		for (Operacija o : operacije) {
			operacijeDTO.add(new OperacijaDTO(o));
		}

		return new ResponseEntity<>(operacijeDTO, HttpStatus.OK);
	}

	@GetMapping(value = "/operacijePacijenta")
	public ResponseEntity<List<OperacijaDTO>> preuzimanjeOperacijaPacijenta(Principal pr) {

		List<Operacija> operacije = operacijaService.findAll();

		// convert students to DTOs
		List<OperacijaDTO> operacijeDTO = new ArrayList<>();
		for (Operacija o : operacije) {
			System.out.println("======");
			System.out.println(o.getListaLekara().size());
			if (o.getPacijent().getEmail().equals(pr.getName()) && o.getStatus() == 1) {
//				if(o.getStatus() == 3) ako su zavrsene
				operacijeDTO.add(new OperacijaDTO(o));
			}

		}

		return new ResponseEntity<>(operacijeDTO, HttpStatus.OK);
	}

	// vracanje liste svih operacija lekara
	@GetMapping(value = "/operacijeLekara")
	@CrossOrigin(origins = "http://localhost:3000")
	@PreAuthorize("hasAuthority('LEKAR')")
	public ResponseEntity<List<OperacijaDTO>> preuzimanjeOperacijaLekara(Principal pr) {

		Lekar lekar = lekarService.findByEmail(pr.getName());

		Set<Operacija> operacije = lekar.getListaOperacija();
		List<OperacijaDTO> operacijeDTO = new ArrayList<>();

		for (Operacija o : operacije) {

			System.out.println(o.getStatus());
			if (o.getStatus() == 1) {
				System.out.println("dodata");
				operacijeDTO.add(new OperacijaDTO(o));
			}

		}

		return new ResponseEntity<>(operacijeDTO, HttpStatus.OK);
	}

	// zakazivanje operacije od strane lekara
	@PostMapping(path = "/novaOperacija", consumes = "application/json")
	@CrossOrigin(origins = "http://localhost:3000")
	@PreAuthorize("hasAuthority('LEKAR')")
	public ResponseEntity<?> novaOperacija(@RequestBody OperacijaDTO operacijaDTO, Principal pr) {

		Lekar lekar = lekarService.findByEmail(pr.getName());
		Klinika klinika = lekar.getKlinika();
		Pacijent pacijent = pacijentService.findByEmail(operacijaDTO.getPacijentEmail());

		System.out.println("dodavanje nove operacije");
		System.out.println(operacijaDTO);
		Operacija operacija = new Operacija();
		operacija.setDatum(operacijaDTO.getDatum());
		operacija.setTermin(operacijaDTO.getTermin());
		operacija.setStatus(0);
		operacija.setTipOperacije(operacijaDTO.getTipOperacije());
		operacija.setPacijent(pacijent);
		operacija.setKlinika(klinika);

		operacija = operacijaService.save(operacija);

		klinika.getListaOperacija().add(operacija);
		klinika = klinikaService.save(klinika);

//		pacijent.getListaOperacija().add(operacija);
//		pacijent = pacijentService.save(pacijent);

		Set<AdministratorKlinike> ak = klinika.getListaAdminKlinike();

		for (AdministratorKlinike AK : ak) {
			AdministratorKlinikeDTO akDTO = new AdministratorKlinikeDTO(AK);
			String subject = "Zahtev za operaciju";
			String text = "Postovani " + AK.getIme() + " " + AK.getPrezime() + ",\n\n imate novi zahtev za operaciju.";

			System.out.println(text);

			// slanje emaila
			try {
				emailService.poslatiOdgovorAdminuK(akDTO, subject, text);
			} catch (Exception e) {
				logger.info("Greska prilikom slanja emaila: " + e.getMessage());
				return new ResponseEntity<>("Mail nije poslat", HttpStatus.BAD_REQUEST);
			}
		}

		return new ResponseEntity<>(new OperacijaDTO(operacija), HttpStatus.OK);
	}

	@GetMapping(value = "preuzmiZahteveOperacijeKlinike/{id}")
	@PreAuthorize("hasAuthority('ADMIN_KLINIKE')")
	public ResponseEntity<List<OperacijaDTO>> getZahteviOperacijeKlinike(@PathVariable Long id) {

		Klinika klinika = klinikaService.findOne(id);
		List<Operacija> operacije = operacijaService.findAll();
		List<OperacijaDTO> lista = new ArrayList<OperacijaDTO>();
		for (Operacija o : operacije) {
			if (o.getKlinika().getId() == klinika.getId() && o.getStatus() == 0) {
				OperacijaDTO pregledDTO = new OperacijaDTO(o);
				lista.add(pregledDTO);
			}
		}

		System.out.println("Lista  zahtjeva operacija u klinici:" + klinika.getNaziv() + " ID: " + id);
		for (OperacijaDTO ss : lista) {
			System.out.println(ss);
		}

		return new ResponseEntity<>(lista, HttpStatus.OK);
	}

	// pronalazak sala slobodnih za taj teremin i datum za PREGLED
	@GetMapping(value = "/pronadjiSaleZaTajTermin/{idP}")
	@CrossOrigin(origins = "http://localhost:3000")
	@PreAuthorize("hasAuthority('ADMIN_KLINIKE')")
	public ResponseEntity<List<SalaDTO>> getSaleTermin(@PathVariable Long idP) {

		Operacija operacija = operacijaService.findById(idP);
		System.out.println(operacija.getDatum() + " " + operacija.getTermin());

		List<Sala> sale = salaService.findAll();
		List<Sala> listaSalaKlinike = new ArrayList<Sala>();
		for (Sala s : sale) {
			if (s.getKlinika().getId() == operacija.getKlinika().getId()) {
				if (s.getTipSale() == 0) {
					listaSalaKlinike.add(s);
				}
			}
		}

		List<Sala> listaSalaTest = new ArrayList<Sala>();
		boolean flag = false;
		List<SalaDTO> listaSalaSlob = new ArrayList<SalaDTO>();
		List<Sala> listaZauzete = new ArrayList<Sala>();
		for (Sala sD : listaSalaKlinike) {
			System.out.println();
			System.out.println(sD.getId());
			flag = false;
			SalaDTO sdt = new SalaDTO();
			for (Termin t : terminService.findAll()) {

				System.out.println(t);
				if (t.getSala().getId() == sD.getId()) {
					System.out.println(t.getSala().getId() + " " + t.getTermin() + " " + t.getDatumPocetka());
					if (t.getDatumPocetka().equals(operacija.getDatum())
							&& t.getTermin().equals(operacija.getTermin())) {
						System.out.println("Istiiii termin i njega preskoci");
						sD.getZauzetiTermini().add(t);
						listaZauzete.add(sD);

						flag = true;
						break;

					} else if (t.getDatumPocetka().equals(operacija.getDatum())) {
						System.out.println("||||||||||||||||||||||||||||||||||||||||||||");
						if (!t.getTermin().equals(operacija.getTermin())) {
							sD.getZauzetiTermini().add(t);
							System.out.println();
							salaService.save(sD);
							listaSalaTest.add(sD);
							System.out.println(
									"********************aaaa " + sD.getId() + sD.getBroj() + " " + t.getTermin());

							TerminDTO terminDTO = new TerminDTO(t);

							sdt.getZauzetiTermini().add(terminDTO);
						}
					}

				}

			}

			if (flag) {
				continue;
			}

			sdt.setNaziv(sD.getNaziv());
			sdt.setBroj(sD.getBroj());
			sdt.setId(sD.getId());
			sdt.setKlinikaID(sD.getKlinika().getId());

			System.out.println();
			System.out.println("DUZINAAAAAAAAAAAAAAAAAAAA : " + listaSalaTest.size());
			listaSalaSlob.add(sdt);
//			

		}

		if (listaZauzete.size() == listaSalaKlinike.size()) {
			listaZauzete = new ArrayList<Sala>();
			// znaci da su sve sale zauzete za taj termin, tada mi vrati sve sale sa
			// disabled tim terminom, jer ima mogucnost da izabere novi termin i ako ima
			// potrebe da mijenja lekara
			for (Sala sala : listaSalaKlinike) {
				if (sala.getTipSale() == 0) {
					SalaDTO sdt = new SalaDTO();
					for (Termin termin : terminService.findAll()) {
						if (sala.getId() == termin.getSala().getId()) {
							if (termin.getDatumPocetka().equals(operacija.getDatum())
									&& termin.getTermin().equals(operacija.getTermin())) {
								sala.getZauzetiTermini().add(termin);
								TerminDTO terDTO = new TerminDTO(termin);

								sdt.getZauzetiTermini().add(terDTO);
								sdt.setNaziv(sala.getNaziv());
								sdt.setBroj(sala.getBroj());
								sdt.setId(sala.getId());
								sdt.setKlinikaID(sala.getKlinika().getId());

//								System.out.println();
//								System.out.println("********************bbbbb " + sala.getId() +" " + sala.getBroj() + " " + termin.getTermin());

							} else if (termin.getDatumPocetka().equals(operacija.getDatum())) {
								sala.getZauzetiTermini().add(termin);
								TerminDTO terDTO = new TerminDTO(termin);

								sdt.getZauzetiTermini().add(terDTO);
								sdt.setNaziv(sala.getNaziv());
								sdt.setBroj(sala.getBroj());
								sdt.setId(sala.getId());
								sdt.setKlinikaID(sala.getKlinika().getId());
								//
//								System.out.println();
//								System.out.println("********************bbbbb " + sala.getId() + sala.getBroj() + " " + termin.getTermin());
							}
						}

					}
//					System.out.println();
					salaService.save(sala);

					listaSalaSlob.add(sdt);
				}

			}

		}
		return new ResponseEntity<>(listaSalaSlob, HttpStatus.OK);
	}

	// metoda koja vraca listu slobodnih lekara za taj datum i termin
	@PostMapping(path = "/pronadjiLekaraZaOperaciju", consumes = "application/json")
	@CrossOrigin(origins = "http://localhost:3000")
	@PreAuthorize("hasAuthority('ADMIN_KLINIKE')")
	public ResponseEntity<List<LekarDTO>> slobodanLekar(@RequestBody PregledDTO pDTO) {

		List<LekarDTO> listaSlobodnihLekara = new ArrayList<LekarDTO>();
		Klinika klinika = klinikaService.findById(pDTO.getKlinikaID());

		System.out.println(pDTO);
		List<Lekar> listaSvihLekaraKlinike = new ArrayList<Lekar>();
		List<Lekar> listaSvihLekara = lekarService.findAll();
		for (Lekar l : listaSvihLekara) {
			if (l.getKlinika().equals(klinika)) {
				LekarDTO ll = new LekarDTO(l);
				listaSvihLekaraKlinike.add(l);
				// listaSlobodnihLekara.add(ll);
			}

		}

		System.out.println();
		System.out.println(" SVI LEKARI KLINIKE *************** " + listaSvihLekaraKlinike.size());
		System.out.println(listaSvihLekaraKlinike.size());

		// Lekar lekar = lekarService.findByEmail(ooDTO.getEmailL());
		for (Lekar lekar : listaSvihLekaraKlinike) {
			System.out.println("-----> " + lekar.getId() + " " + lekar.getIme() + " " + lekar.getPrezime());
			boolean flag = false;
//					Set<Termin> pregledi = lekar.getListaZauzetihTermina();
			for (Operacija t : operacijaService.findAll()) {
				if (t.getStatus() == 1) {
					for (Lekar lo : t.getListaLekara()) {
						if (lo.getId() == lekar.getId()) {
							if (t.getTermin() == pDTO.getTermin()) {
								System.out.println("TERMIN ISTI");
								if (t.getDatum().compareTo(pDTO.getDatum()) == 0) {
									System.out.println("Zauzet lekar :");
									System.out.println(lekar.getId() + " " + lekar.getIme() + " " + lekar.getPrezime());
									System.out.println();
									flag = true;
									break;

								}

							}
						}

					}

				}

			}

			Set<OdmorOdsustvoLekar> listaool = lekar.getListaOdmorOdsustvo();

			for (OdmorOdsustvoLekar ool : listaool) {
				if (ool.getStatus() == 1) {
					if (ool.getDatumOd().compareTo(pDTO.getDatum())
							* pDTO.getDatum().compareTo(ool.getDatumDo()) >= 0) {
						System.out.println();
						System.out.println("Lekar na odmoru");
						System.out.println(lekar.getId() + " " + lekar.getIme() + " " + lekar.getPrezime());
						System.out.println();
						flag = true;
						break;
					}

				}
			}

			if (!flag) {
				LekarDTO l = new LekarDTO(lekar);
				listaSlobodnihLekara.add(l);
			}

		}

		for (LekarDTO l : listaSlobodnihLekara) {
			System.out.println(l);
		}

		System.out.println("*************** " + listaSlobodnihLekara.size());

		return new ResponseEntity<>(listaSlobodnihLekara, HttpStatus.OK);
	}

	// rezervisanje sale i slanje mejla pacijentu i lekaru
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	@PostMapping(path = "/rezervisanjeSale", consumes = "application/json")
	@PreAuthorize("hasAuthority('ADMIN_KLINIKE')")
	public ResponseEntity<String> rezervisanjeSaleOp(@RequestBody OperacijaDTO oDTO) {
		System.out.println();
		System.out.println("......... Rezervisanje sale ..... ");
		System.out.println(oDTO);
		System.out.println();

		List<Operacija> listaOperacija = operacijaService.findAll();
		Pacijent pacijent = null;
		List<Lekar> listaLekaraOper = new ArrayList<Lekar>();

		for (Operacija p : listaOperacija) {
			if (p.getId().equals(oDTO.getId())) {
//				p.setStatus(1);
				Sala s = salaService.findById(oDTO.getSalaID());
				p.setSala(s);
				pacijent = pacijentService.findByID(oDTO.getPacijentID());
				p.setPacijent(pacijent);

				for (Long i : oDTO.getListaLekara()) {
					Lekar lekarO = lekarService.findById(i);
					listaLekaraOper.add(lekarO);
					// dodajem tu operaciju kod lekara:

				}

				p.setTermin(oDTO.getTermin());
				p.setDatum(oDTO.getDatum());

				operacijaService.save(p);

				for (Lekar le : listaLekaraOper) {
					Termin t = new Termin();
					t.setTermin(oDTO.getTermin());
					int idT = terminService.findAll().size() + 1;
					long idTermina = (long) idT;
					t.setId(idTermina);
					t.setDatumPocetka(oDTO.getDatum());
					t.setSala(s);
					t.setLekar(le);
					t = terminService.save(t);

					le.getListaZauzetihTermina().add(t);
					lekarService.save(le);

					s.getZauzetiTermini().add(t);
					salaService.save(s);

				}

			}

		}

		System.out.println("REZERVISANOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO");

		String subject = "Rezervisana sala za operaciju";
//		String dat = new SimpleDateFormat("yyyy-MM-dd").format(oDTO.getDatum().getDate());
		String text = "Postovani " + pacijent.getIme() + " " + pacijent.getPrezime()
				+ ",\n\nVasa operacija je zakazana za " + oDTO.getDatum().getDate() + " u " + oDTO.getTermin() + ":00h";

		System.out.println(text);

		PacijentDTO pd = new PacijentDTO(pacijent);

		// slanje emaila
		try {
			emailService.poslatiOdgovorPacijentu(pd, subject, text);
		} catch (Exception e) {
			logger.info("Greska prilikom slanja emaila: " + e.getMessage());
			return new ResponseEntity<>("Mail nije poslat", HttpStatus.BAD_REQUEST);
		}

		for (Lekar lekarr : listaLekaraOper) {
			LekarDTO ld = new LekarDTO(lekarr);
			String subject2 = "Rezervisana sala za operaciju";
			String text2 = "Postovani " + lekarr.getIme() + " " + lekarr.getPrezime()
					+ ",\n\nRezervisana je sala za operaciju! ";
			try {
				emailService.poslatiOdgovorLekaru(ld, subject2, text2);
			} catch (Exception e) {
				logger.info("Greska prilikom slanja emaila: " + e.getMessage());
				return new ResponseEntity<>("Mail nije poslat2", HttpStatus.BAD_REQUEST);
			}
		}

		return new ResponseEntity<>("uspesno rezervisana sala1", HttpStatus.OK);
	}

	// zakazivanje operacije
	@PostMapping(value = "/zakazivanjeOperacijeLekar")
	@CrossOrigin(origins = "http://localhost:3000")
	@PreAuthorize("hasAuthority('LEKAR')")
	public ResponseEntity<?> zakazivanjeOperacijeLekar(@RequestBody OperacijaDTO operacijaDTO, Principal pr) {
		System.out.println("*************");

		Lekar lekar = lekarService.findByEmail(pr.getName());

		System.out.println("dodavanje nove operacije");

		Operacija operacija = new Operacija();

		operacija.setDatum(operacijaDTO.getDatum());

		Klinika klinika = lekar.getKlinika();
		operacija.setKlinika(klinika);

		operacija.setTermin(operacijaDTO.getTermin());

		Pacijent pacijent = pacijentService.findByEmail(operacijaDTO.getPacijentEmail());
		operacija.setPacijent(pacijent);
		operacija.setStatus(0);

		operacija.setTipOperacije(operacijaDTO.getTipOperacije());

		operacija.setCena(3000);

		operacija = operacijaService.save(operacija);

		klinika.getListaOperacija().add(operacija);
		klinika = klinikaService.save(klinika);

		Set<AdministratorKlinike> ak = klinika.getListaAdminKlinike();

		for (AdministratorKlinike AK : ak) {
			AdministratorKlinikeDTO akDTO = new AdministratorKlinikeDTO(AK);
			String subject = "Zahtev za operaciju";
			String text = "Postovani " + AK.getIme() + " " + AK.getPrezime() + ",\n\n imate novi zahtev za operaciju.";

			System.out.println(text);

			// slanje emaila
			try {
				emailService.poslatiOdgovorAdminuK(akDTO, subject, text);
			} catch (Exception e) {
				logger.info("Greska prilikom slanja emaila: " + e.getMessage());
				return new ResponseEntity<>("Mail nije poslat", HttpStatus.BAD_REQUEST);
			}
		}

		return new ResponseEntity<>(new OperacijaDTO(operacija), HttpStatus.OK);

	}

	@GetMapping(value = "lekariNaOperaciji/{id}")
//	@PreAuthorize("hasAuthority('ADMIN_KLINIKE')")
	public ResponseEntity<?> getlekariNaOperaciji(@PathVariable Long id) {

		List<Lekar> lekari = operacijaService.findLekare(id);
		List<LekarDTO> lekariDTO = new ArrayList<LekarDTO>();

		if (!lekari.isEmpty()) {
			for (Lekar l : lekari) {
				lekariDTO.add(new LekarDTO(l));

			}
			return new ResponseEntity<>(lekariDTO, HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Nema lekara", HttpStatus.OK);
		}

	}

}
