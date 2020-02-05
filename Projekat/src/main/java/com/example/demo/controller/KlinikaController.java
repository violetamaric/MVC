package com.example.demo.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.KlinikaDTO;
import com.example.demo.dto.LekarDTO;
import com.example.demo.dto.PacijentDTO;
import com.example.demo.model.Klinika;
import com.example.demo.model.Lekar;
import com.example.demo.model.OdmorOdsustvoLekar;
import com.example.demo.model.Pacijent;
import com.example.demo.model.Pregled;
import com.example.demo.model.SlobodniTermin;
import com.example.demo.model.Termin;
import com.example.demo.service.KlinikaService;
import com.example.demo.service.LekarService;
import com.example.demo.service.PregledService;
import com.example.demo.service.SlobodniTerminService;

@RestController
@RequestMapping(value = "/api/klinike", produces = MediaType.APPLICATION_JSON_VALUE)
public class KlinikaController {
	@Autowired
	private KlinikaService klinikaService;
	@Autowired
	private LekarService lekarService;
	@Autowired
	private PregledService pregledService;
	@Autowired
	private SlobodniTerminService STService;

	@GetMapping(value = "/{id}")
	@CrossOrigin(origins = "http://localhost:3000")
	@PreAuthorize("hasAuthority('ADMIN_KLINIKE') or hasAuthority('LEKAR') or hasAuthority('ADMIN_KC')")
	public ResponseEntity<?> getKlinikaById(@PathVariable Long id) {
		System.out.println("Metoda find by id klinika: ");
		System.out.println(id);

		Klinika k = klinikaService.findOne(id);
		System.out.println("Pretraga klinike po ID");
		// studen must exist
		if (k == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		System.out.println(k.getNaziv() + " " + k.getId());
		return ResponseEntity.ok(new KlinikaDTO(k));
	}

//	@GetMapping(value = "/{id}")
//	@CrossOrigin(origins = "http://localhost:3000")
//	@PreAuthorize("hasAuthority('ADMIN_KLINIKE')")
//	public ResponseEntity<?> getKlinikaById(@PathVariable Long id) {
//		System.out.println("Metoda find by id klinika: ");
//		System.out.println(id);
//		
//		Klinika k = klinikaService.findOne(id);
//		System.out.println("Pretraga klinike po ID");
//		// studen must exist
//		if (k == null) {
//			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//		}
//		System.out.println(k.getNaziv() + " " + k.getId());
//		return ResponseEntity.ok(new KlinikaDTO(k));
//	}

	@GetMapping(value = "/all")
	@PreAuthorize("hasAuthority('PACIJENT') or hasAuthority('ADMIN_KLINIKE')")
	public ResponseEntity<List<KlinikaDTO>> getAll() {

		List<Klinika> klinike = klinikaService.findAll();

		// convert students to DTOs
		List<KlinikaDTO> klinikaDTO = new ArrayList<>();
		for (Klinika k : klinike) {
			klinikaDTO.add(new KlinikaDTO(k));
		}

		return new ResponseEntity<>(klinikaDTO, HttpStatus.OK);
	}

	@GetMapping(value = "/findKlinikaByNaziv/{naziv}")
	@CrossOrigin(origins = "http://localhost:3000")
	@PreAuthorize("hasAuthority('ADMIN_KLINIKE')")
	public ResponseEntity<KlinikaDTO> getKlinikaByNaziv(@PathVariable String naziv) {
		System.out.println("find klinika by naziv");
		Klinika klinika = klinikaService.findByNaziv(naziv);
		if (klinika == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		System.out.println(klinika.getNaziv());
		return new ResponseEntity<>(new KlinikaDTO(klinika), HttpStatus.OK);
	}

	@GetMapping(value = "/findKlinikaByAdresa/{adresa}")
	public ResponseEntity<KlinikaDTO> getKlinikaByAdresa(@PathVariable String adresa) {
		System.out.println("find klinika by adresa");
		if (adresa.contains("%20"))
			adresa.replace("%20", " ");

		Klinika klinika = klinikaService.findByAdresa(adresa);

		if (klinika == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		System.out.println(klinika.getNaziv());
		return new ResponseEntity<>(new KlinikaDTO(klinika), HttpStatus.OK);
	}

	@PutMapping(path = "/update", consumes = "application/json")
	@CrossOrigin(origins = "http://localhost:3000")
	@PreAuthorize("hasAuthority( 'ADMIN_KLINIKE') or hasAuthority('ADMIN_KC')")
	public ResponseEntity<KlinikaDTO> updateKliniku(@RequestBody KlinikaDTO klinikaDTO) {

		// a student must exist
		System.out.println(" KLINIKa UPDRATE");
		Klinika klinika = klinikaService.findById(klinikaDTO.getId());

//		System.out.println("Lekar update: " + lekar.getEmail());
//		if (lekar == null) {
//			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//		}
		klinika.builder()
		.naziv(klinikaDTO.getNaziv())
		.adresa(klinikaDTO.getAdresa())
		.opis(klinikaDTO.getOpis())
		.ocena(klinikaDTO.getOcena())
		.build();
//		klinika.setNaziv(klinikaDTO.getNaziv());
//		klinika.setAdresa(klinikaDTO.getAdresa());
//		klinika.setOpis(klinikaDTO.getOpis());
//		klinika.setOcena(klinikaDTO.getOcena());

		klinika = klinikaService.save(klinika);
		System.out.println("Izmjenjena k: " + klinika);
		return new ResponseEntity<>(new KlinikaDTO(klinika), HttpStatus.OK);
	}

	@GetMapping(value = "/listaLekaraKlinika/{id}")
	@CrossOrigin(origins = "http://localhost:3000")
	@PreAuthorize("hasAuthority('ADMIN_KLINIKE') or hasAuthority('PACIJENT')")
	public ResponseEntity<List<LekarDTO>> getKlinikaLekari(@PathVariable Long id) {
		System.out.println("//////////////////// KLINIKA LISTA LEKARA /////////////////////////		");
		Klinika klinika = klinikaService.findById(id);
//		List<Lekar> listaSvihLekara =  lekarService.findAll();

		List<LekarDTO> lista = new ArrayList<>();

		for (Lekar l : klinika.getListaLekara()) {

//			if(klinika.getId() == l.getKlinika().getId()) {
			LekarDTO lDTO = new LekarDTO(l);
			lista.add(lDTO);
//			}
		}
//		for(Lekar ll : listaSvihLekara) {
//			if(!lista.contains(ll.getEmail())) {
//				System.out.println("Prazna listaaaaaaaaaaaaaaaaaaaaaaaa!!!!! ");
//				lista = null;
//			}
//		}

		return new ResponseEntity<>(lista, HttpStatus.OK);
	}

	// brisanje lekara
	@PostMapping(path = "/brisanjeLekara", consumes = "application/json")
	@CrossOrigin(origins = "http://localhost:3000")
	@PreAuthorize("hasAuthority('ADMIN_KLINIKE')")
	public ResponseEntity<String> brisanjeLekara(@RequestBody LekarDTO lekarDTO) {
		System.out.println("------------------------------------------------------");
		System.out.println("pocinje");
		// lekar koji se brise
		Lekar lekar = lekarService.findByEmail(lekarDTO.getEmail());
		System.out.println(lekar.getEmail());
		// List<Klinika> listaKlinika = klinikaService.findAll();
		System.out.println("Id LEKAR KLINIKA: " + lekar.getKlinika().getId());

		Long idLong = lekar.getKlinika().getId();

		Klinika klinika = klinikaService.findById(idLong);
		System.out.println("Klinika id ------------- : " + klinika.getId());

		if (klinika.getListaLekara().contains(lekar)) {
			// brisanje njegove liste slobodnih termina
			List<SlobodniTermin> listaST = STService.findAll();
			List<SlobodniTermin> listaSTkopija = listaST;

			for (SlobodniTermin s : listaSTkopija) {

				System.out.println("Slobodni termin L: " + s.getLekar().getIme());
				if (s.getLekar().equals(lekar)) {
					listaST.remove(s);
					STService.delete(s);

				}

			}

			List<Pregled> listaP = pregledService.findAll();
			List<Pregled> listaPkopija = new ArrayList<Pregled>(listaP);
			System.out.println(pregledService.findAll().size());
			for (Pregled p : listaPkopija) {
				System.out.println("Preled: " + p.getLekar().getIme());
				if (p.getLekar().equals(lekar)) {
					System.out.println(listaP.size());

					Pregled pp = pregledService.findById(p.getId());
					listaP.remove(pp);
					System.out.println(listaP.size());

//						pregledService.delete(pp);
					pregledService.deleteById(pp.getId());

					// lekar = lekarService.save(lekar);

					System.out.println("aaaaaaaaaaaaaaaaaaaaa");
				}
			}

			// pregledService.deleteAll();
//			System.out.println(pregledService.findAll().size());
//			for(Pregled preg : listaP) {
//				pregledService.save(preg);
//			}

			System.out.println(pregledService.findAll().size());
			System.out.println("dsadasdasdsadasads");
		}

		System.out.println("--------------*-*-*-*-*-*-*-*-*");

		Set<Lekar> lista = klinika.getListaLekara();
		System.out.println("------> LISTA LEKARA KLINIKE:  -----");
		for (Lekar l : lista) {
			System.out.println(l.getEmail());
		}
		System.out.println("---------------------------------------");
		System.out.println("LEKAR kojeg brisem =============== " + lekar.getEmail());
//			lista.remove(lekar);
//			System.out.println("------> LISTA LEKARA KLINIKE NAKON BRISANJA:  -----" );
//			for(Lekar l: lista) {
//				System.out.println(l.getEmail());
//			}
//			System.out.println("---------------------------------------");
//		//	klinika.getListaLekara().clear();
		klinika.getListaLekara().remove(lekar);
		System.out.println("------> LISTA LEKARA KLINIKE NAKON BRISANJA :  -----");
		for (Lekar l : klinika.getListaLekara()) {
			System.out.println(l.getEmail());
		}
		System.out.println("---------------------------------------");
		// klinika.setListaLekara(lista);
//			System.out.println("------> LISTA LEKARA KLINIKE NAKON SETOVANJA:  -----" );
//			for(Lekar l: klinika.getListaLekara()) {
//				System.out.println(l.getEmail());
//			}
//			System.out.println("---------------------------------------");
		System.out.println(lekar.getEmail());

		Lekar ll = lekarService.findByEmail(lekarDTO.getEmail());
//			System.out.println(ll.getEmail());
//			System.out.println(lekar.getEmail());
		lekarService.delete(ll);

		System.out.println("/*****************   BAZA  *****************/");

		for (Lekar l : lekarService.findAll()) {
			System.out.println(l.getEmail());
		}
		System.out.println("/**********************************/");

		// klinikaService.save(klinika);
		// System.out.println("obrisano" + lekarDTO.getEmail());

		System.out.println("------------------------------------------------------");
		return new ResponseEntity<>("uspesno obrisan lekar !!!", HttpStatus.OK);
	}

	@GetMapping(value = "/pacijentiKlinike/{id}")
	@CrossOrigin(origins = "http://localhost:3000")
	@PreAuthorize("hasAuthority('LEKAR')")
	public ResponseEntity<List<PacijentDTO>> getPacijentiKlinike(@PathVariable Long id) {
		System.out.println("//////////////////// Klinika i lista pacijenata /////////////////////////		");
//		Klinika klinika = klinikaService.findById(id);

		List<Pacijent> listaPacijenataKlinike = klinikaService.findByIdKlinike(id);
		System.out.println("***********");

		for (Pacijent kp : listaPacijenataKlinike) {
			System.out.println(kp);

		}
		List<PacijentDTO> lista = new ArrayList<PacijentDTO>();
		for (Pacijent pp : listaPacijenataKlinike) {
			PacijentDTO pD = new PacijentDTO(pp);
			lista.add(pD);
		}
		System.out.println("*************");

		return new ResponseEntity<>(lista, HttpStatus.OK);
	}

	@PutMapping(path = "/oceni/{id}/{ocena}/{pregled_id}", consumes = "application/json")
	@CrossOrigin(origins = "http://localhost:3000")
	public ResponseEntity<KlinikaDTO> oceniKliniku(@PathVariable Long id, @PathVariable int ocena,
			@PathVariable Long pregled_id) {

		Klinika klinika = klinikaService.findById(id);
		int temp = klinika.getOcena();
		klinika.builder()
		.ocena((temp + ocena) / 2)
		.build();
//		klinika.setOcena((temp + ocena) / 2);
		klinikaService.save(klinika);
		Pregled pregled = pregledService.findById(pregled_id);
		if (pregled.getStatus() == 3) {
			pregled.setStatus(4);
			pregledService.save(pregled);
		} else if (pregled.getStatus() == 5) {
			pregled.setStatus(6);
			pregledService.save(pregled);
		}

		return new ResponseEntity<>(new KlinikaDTO(klinika), HttpStatus.OK);
	}

	@SuppressWarnings("deprecation")
	@GetMapping(value = "/slobodneKlinike/{datum}")
	public ResponseEntity<List<KlinikaDTO>> getSlobodneKlinike(@PathVariable Date datum) {

		System.out.println(datum);
		List<Klinika> klinike = klinikaService.findAll();

		List<KlinikaDTO> klinikaDTO = new ArrayList<>();
		for (Klinika k : klinike) {

			System.out.println(k.getNaziv());
			petlja: {
				Set<Lekar> lekari = k.getListaLekara();
				for (Lekar l : lekari) {
					Set<Termin> terminiLekara = l.getListaZauzetihTermina();

					if (l.sadrziSlobodanTermin(terminiLekara, datum) == false) {
						System.out.println("svi termini su zauzeti");
						System.out.println("FALSE");
					} else {
						boolean flag = false;
						petlja2:{
							Set<OdmorOdsustvoLekar> listaool = l.getListaOdmorOdsustvo();
							for(OdmorOdsustvoLekar ool:listaool ) {
								if(ool.getStatus() == 1) {
									if(ool.getDatumOd().compareTo(datum)*datum.compareTo(ool.getDatumDo())>=0) {
										System.out.println("Lekar na odmoru.");
										flag = true;
										break petlja2;
									}
								}
							}
						}

						if(flag == false) {
							System.out.println("TRUE");
							klinikaDTO.add(new KlinikaDTO(k));
							break petlja;
						}

					}

//					for (Termin tl : terminiLekara) {
//
//						if (tl.getDatumPocetka().getDate() == datum.getDate()
//								&& tl.getDatumPocetka().getMonth() == datum.getMonth()
//								&& tl.getDatumPocetka().getYear() == datum.getYear()) {
//
//							System.out.println(k.getNaziv());
//
//							klinikaDTO.add(new KlinikaDTO(k));
//
//							break petlja;
//
//						}
//					}
				}

			}

		}

		return new ResponseEntity<>(klinikaDTO, HttpStatus.OK);
	}

	@GetMapping(value = "/nedeljniPrihodi/{id}")
	@CrossOrigin(origins = "http://localhost:3000")
	@PreAuthorize("hasAuthority( 'ADMIN_KLINIKE')")
	public float nedeljniPrihodiKlinike(@PathVariable Long id) {

		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String format = formatter.format(date);
		System.out.println(format);

//		Date date = new Date();
//		String krajDatum = new SimpleDateFormat("yyyy-MM-dd").format(date);

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -7);
		Date todate1 = cal.getTime();
		String pocDatum = new SimpleDateFormat("yyyy-MM-dd").format(todate1);
		float ukupno = 0;
		try {
			ukupno = klinikaService.nedeljniPrihod(id, todate1, date);
		} catch (Exception e) {
			System.out.println("NEMA PRIHODA");
		}

		System.out.println("UKUPNO " + ukupno);
		System.out.println("DATUM " + date.toString());
		System.out.println(new SimpleDateFormat("yyyy-MM-dd").format(date));
		return ukupno;

	}

	@GetMapping(value = "/mesecniPrihodi/{id}")
	@CrossOrigin(origins = "http://localhost:3000")
	@PreAuthorize("hasAuthority( 'ADMIN_KLINIKE')")
	public float mesecniPrihodiKlinike(@PathVariable Long id) {

		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String format = formatter.format(date);
		System.out.println(format);

//		Date date = new Date();
//		String krajDatum = new SimpleDateFormat("yyyy-MM-dd").format(date);

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -30);
		Date todate1 = cal.getTime();
		String pocDatum = new SimpleDateFormat("yyyy-MM-dd").format(todate1);
		float ukupno = 0;
		try {
			ukupno = klinikaService.nedeljniPrihod(id, todate1, date);
		} catch (Exception e) {
			System.out.println("NEMA PRIHODA");
		}

		System.out.println("UKUPNO " + ukupno);
		System.out.println("DATUM " + date.toString());
		System.out.println(new SimpleDateFormat("yyyy-MM-dd").format(date));
		return ukupno;

	}

	@GetMapping(value = "/godisnjiPrihodi/{id}")
	@CrossOrigin(origins = "http://localhost:3000")
	@PreAuthorize("hasAuthority( 'ADMIN_KLINIKE')")
	public float godisnjiPrihodiKlinike(@PathVariable Long id) {

		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String format = formatter.format(date);
		System.out.println(format);

//		Date date = new Date();
//		String krajDatum = new SimpleDateFormat("yyyy-MM-dd").format(date);

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -365);
		Date todate1 = cal.getTime();
		String pocDatum = new SimpleDateFormat("yyyy-MM-dd").format(todate1);
		float ukupno = 0;
		try {
			ukupno = klinikaService.nedeljniPrihod(id, todate1, date);
		} catch (Exception e) {
			System.out.println("NEMA PRIHODA");
		}

		System.out.println("UKUPNO " + ukupno);
		System.out.println("DATUM " + date.toString());
		System.out.println(new SimpleDateFormat("yyyy-MM-dd").format(date));
		return ukupno;

	}

	@GetMapping(value = "/dnevniNivo/{id}")
	public ResponseEntity<?> dnevniNivo(@PathVariable Long id) {

		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String format = formatter.format(date);
		System.out.println(format);
		date.setHours(0);
		date.setMinutes(0);
		date.setSeconds(0);
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		Date date2 = cal.getTime();
		date2.setHours(0);
		date2.setMinutes(0);
		date2.setSeconds(0);
		HashMap<Integer, Integer> pregledi = new HashMap<Integer, Integer>();
		try {
			int u9 = 0;
			u9 = klinikaService.dnevniNivo(id, 9, date2, date);
			System.out.println(u9);
			pregledi.put(9, u9);
			int u11 = 0;
			u11 = klinikaService.dnevniNivo(id, 11, date2, date);
			System.out.println(u11);
			pregledi.put(11, u11);
			int u13 = 0;
			u13 = klinikaService.dnevniNivo(id, 13, date2, date);
			System.out.println(u13);
			pregledi.put(13, u13);
			int u15 = 0;
			u15 = klinikaService.dnevniNivo(id, 15, date2, date);
			System.out.println(u15);
			pregledi.put(15, u15);

			System.out.println(pregledi.get(9));
			System.out.println(pregledi.get(11));
			System.out.println(pregledi.get(13));
			System.out.println(pregledi.get(15));

		} catch (Exception e) {
			System.out.println("NEMA TERMINA");
		}

		return new ResponseEntity<>(pregledi, HttpStatus.OK);
	}

	@SuppressWarnings("deprecation")
	@GetMapping(value = "/nedeljniNivo/{id}")
	public ResponseEntity<?> nedeljniNivo(@PathVariable Long id) {

		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String format = formatter.format(date);
		System.out.println(format);

		HashMap<Date, Integer> pregledi = new HashMap<Date, Integer>();

		Date pomocni = date;
		for (int i = 0; i < 6; i++) {
			System.out.println("===================================");

			date = pomocni;
			date.setHours(0);
			date.setMinutes(0);
			date.setSeconds(0);
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, - i-1);


			SimpleDateFormat dateOnly = new SimpleDateFormat("yyyy-MM-dd");
			System.out.println(dateOnly.format(cal.getTime()));
			Date todate1 = cal.getTime();
			todate1.setHours(0);
			todate1.setMinutes(0);
			todate1.setSeconds(0);
			pomocni = todate1;

			System.out.println(date.toString());
			String pocDatum = new SimpleDateFormat("yyyy-MM-dd").format(todate1);
			int nedeljno = 0;

			try {
				System.out.println("TRY month " + date.getMonth());
				nedeljno = klinikaService.nedeljniNivo(id, todate1, date);
				System.out.println("NEDELJNO: " + nedeljno);
				pregledi.put(date, nedeljno);

			} catch (Exception e) {
				System.out.println("NEMA TERMINA");
			}
			System.out.println("=======================");

		}

		return new ResponseEntity<>(pregledi, HttpStatus.OK);
	}

	@GetMapping(value = "/mesecniNivo/{id}")
	public ResponseEntity<?> mesecniNivo(@PathVariable Long id) {

		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String format = formatter.format(date);
		System.out.println(format);

		HashMap<Date, Integer> pregledi = new HashMap<Date, Integer>();

		Date pomocni = date;
		for (int i = 0; i < 3; i++) {

			date = pomocni;
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.MONTH, -1 - i);

			Date todate1 = cal.getTime();

			pomocni = todate1;
			System.out.println(todate1.toString());
			System.out.println(date.toString());
			String pocDatum = new SimpleDateFormat("yyyy-MM-dd").format(todate1);
			int mesecno = 0;

			try {
				System.out.println("TRY month " + date.getMonth());
				mesecno = klinikaService.mesecniNivo(id, todate1, date);
				System.out.println("GODISNJE: " + mesecno);
				pregledi.put(date, mesecno);

			} catch (Exception e) {
				System.out.println("NEMA TERMINA");
			}
			System.out.println("=======================");
		}

		return new ResponseEntity<>(pregledi, HttpStatus.OK);
	}
}
