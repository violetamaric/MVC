insert into klinicki_centar (naziv, adresa, opis) values ('Klinicki centar Srbije', 'Pasterova 2','Klinicki centar uvek na usluzi svojim pacijentima');

insert into administratorkc (ime, prezime, lozinka, email, klinicki_centar_id) values ('MVC', 'MVC', '$2y$12$YlXYBQHItM7BAtAwRWS5VOe3x0aufhZ3qDz8r5BVdghgZGJMtVY.2', 'mvc@gmail.com', 1);

insert into klinika (naziv, adresa, opis, klinicki_centar_id, ocena) values ('Klinika Beograd', 'Pasterova 2','Klinika uvek na usluzi svojim pacijentima',1, 8);
insert into klinika (naziv, adresa, opis, klinicki_centar_id, ocena) values ('Klinika Nis', 'Bulevar dr Zorana Djindjica 48','Klinika uvek na usluzi svojim pacijentima',1, 9);
insert into klinika (naziv, adresa, opis, klinicki_centar_id, ocena) values ('Klinika Novi Sad', 'Hajduk Veljkova 1','Klinika uvek na usluzi svojim pacijentima',1, 10);

insert into administrator_klinike (ime, prezime, lozinka, email, telefon, klinika_id) values ('Maga', 'Lakic', '$2y$12$XYkK0PZgshWgicsvaRF5IONp/ayRb15YJbSAYYI.Pguv05UI85s7S', 'maga@gmail.com', '066/000-665', 3);
insert into administrator_klinike (ime, prezime, lozinka, email, telefon, klinika_id) values ('Ceca', 'Antesevic', '$2y$12$0u9Lrx8s9VDyMp/MttaHseqJbQ0l0lLRmzHbaWCrtzVPBXtbxVVVO', 'ceca@gmail.com', '061/422-852', 2);
insert into administrator_klinike (ime, prezime, lozinka, email, telefon, klinika_id) values ('Viki', 'Maric', '$2y$12$IYv24FLHC3QXGryKQgcgYubTQxsWYWihRJO19mW8BV93Gk6dIlFM.', 'viki@gmail.com', '064/656-969', 1);

insert into lek (naziv, sifra, klinicki_centar_id) values ('Andol','001', 1);
insert into lek (naziv, sifra, klinicki_centar_id) values ('Cafetin','002',1);
insert into lek (naziv, sifra, klinicki_centar_id) values ('Panadol','003',1);
insert into lek (naziv, sifra, klinicki_centar_id) values ('Nitroglicerin','004',1);
insert into lek (naziv, sifra, klinicki_centar_id) values ('Valsacor','005',1);
insert into lek (naziv, sifra, klinicki_centar_id) values ('Tiastat','006',1);
insert into lek (naziv, sifra, klinicki_centar_id) values ('Febricet','007',1);

insert into dijagnoza(naziv, opis, oznaka, klinicki_centar_id ) values('Trovanje hranom', 'Intoxicato ailmentaria per Clostridium perfringentem', 'A052', 1);
insert into dijagnoza(naziv, opis, oznaka, klinicki_centar_id ) values('Trbušni tifus', 'Typhus abdominalis', 'A010', 1);
insert into dijagnoza(naziv, opis, oznaka, klinicki_centar_id ) values('Veliki kašalj ', 'Pertussis', 'A37', 1);
insert into dijagnoza(naziv, opis, oznaka, klinicki_centar_id ) values('Groznica Zapadnog Nila', 'Febris West Nile', 'A923', 1);
insert into dijagnoza(naziv, opis, oznaka, klinicki_centar_id ) values('Velike boginje', 'Variola vera', 'B03', 1);
insert into dijagnoza(naziv, opis, oznaka, klinicki_centar_id ) values('Male boginje bez komplikacija', 'Morbilli sine complicationibus', 'B059', 1);


insert into lekar (ime, prezime, email, lozinka,klinika_id, ocena, telefon) values ('Milica','Markovic', 'micacica@gmail.com', '$2y$12$TSUwPf/Ji7j5xr2eq6QjaugOzsFjNnKo.TedGrtdRsugH7hhFhtBW', 1, 0, '066/243-665');
insert into lekar (ime, prezime, email, lozinka,klinika_id, ocena,  telefon) values ('Jovan','Jovanovic', 'jole@gmail.com', '$2y$12$7hS4PjDxhrvZJtxaGseXOOlR52ZyNlS3fa4X0Z/dQHLv7W69gVbi.', 3, 0, '063/200-765');
insert into lekar (ime, prezime, email, lozinka,klinika_id, ocena, telefon) values ('Dusan','Dusanovic', 'duskodule@gmail.com', '$2y$12$VNa2wnV9nqBp082R9OyfleQVTTJlV9EcuEApVR8wSUHYZRXfpg.uy', 3, 0, '066/993-785');
insert into lekar (ime, prezime, email, lozinka,klinika_id, ocena, telefon) values ('Marija','Maric', 'marija@gmail.com', '$2y$12$3QwsJvo3YK7ybzuo0fkk3uRDtC3X.wdnuAGFSsMBehrE8gYOMNkx.', 3, 5, '069/143-665');
insert into lekar (ime, prezime, email, lozinka,klinika_id, ocena,  telefon) values ('Jelena','Janic', 'jelena@gmail.com', '$2y$12$jlxdajA.m3/vGCTBxOKHPuxV5vP71kNKvsd53LvnaMhogXaTpjy/6', 3, 0, '063/209-785');
insert into lekar (ime, prezime, email, lozinka,klinika_id, ocena, telefon) values ('Ivan','Ivanovic', 'ivan@gmail.com', '$2y$12$4C0Aj6sJ4aeNgFVLb4kN3e/Cv5AjEI24g9hfr6tIPtDoLwhsIvCd.', 3, 0, '068/953-445');


insert into medicinska_sestra (ime, prezime, email, br_telefona, lozinka,klinika_id) values ('Jelena','Jelenovic', 'jelena@gmail.com', '066/222-665', '$2y$12$ckUemsooeJoZ8mWv0h83G.pENxUrVVIMr2jgclhFFVepHGRPwpAtG', 1);
insert into medicinska_sestra (ime, prezime, email, br_telefona, lozinka,klinika_id) values ('Olgica','Olganovic', 'olgaolgaa@gmail.com','063/222-765', '$2y$12$a.k4FY.bo4SmCenCbJZxEOxrWKtaOaLX2ubDV4/0aAmMX98K6onmu', 2);
insert into medicinska_sestra (ime, prezime, email, br_telefona, lozinka,klinika_id) values ('Gordana','Gordanovic', 'gocagordana@gmail.com', '066/963-785', '$2y$12$RWNCcUWoAu9Ez5EUZCrDheKlhAAdWpKKcs3NoSlenGYmNrzBkCVlK', 3);

insert into zdravstveni_karton (visina, tezina, krvna_grupa) values (180,80, 'AB');
insert into zdravstveni_karton (visina, tezina, krvna_grupa) values (190,90, 'A');
insert into zdravstveni_karton (visina, tezina, krvna_grupa) values (185,85, 'B');
insert into zdravstveni_karton (visina, tezina, krvna_grupa) values (195,95, '0');

insert into pacijent (ime, prezime, lbo,  lozinka, email, adresa, grad, drzava, telefon, odobrena_registracija, klinicki_centar_id, zdravstveni_karton_id) values ('Pera', 'Peric', '001', '$2y$12$quB17Sy.NyfuOK2gV0xAYuMWL9Hq7D.enZa/iVlDh/4qz.5dbr8fy', 'pera@gmail.com', 'Temerinska 55','Novi Sad', 'Srbija','065241359', true, 1, 1);
insert into pacijent (ime, prezime, lbo,  lozinka, email, adresa, grad, drzava, telefon, odobrena_registracija, klinicki_centar_id, zdravstveni_karton_id) values ('Mita', 'Mitic', '002', '$2y$12$dsfxuPfCtOdH/1hNS6nYg.bV9KVvYSV8ieibaUVvnoPbDaQmAK3Y.', 'mita@gmail.com', 'Petra Drapsina 89', 'Novi Sad', 'Srbija','0642255696', false, 1, 2);
insert into pacijent (ime, prezime, lbo,  lozinka, email, adresa, grad, drzava, telefon, odobrena_registracija, klinicki_centar_id, zdravstveni_karton_id) values ('Jovan', 'Jovicic', '003', '$2y$12$aKFrJ1lPai6EBwofijLvOOw67h2GD2sxlBf8TdKPc3ykqKIfo8ayW', 'jovan@gmail.com', 'Petra Kocica 66', 'Novi Sad', 'Srbija','061221478', true, 1, 3);
insert into pacijent (ime, prezime, lbo,  lozinka, email, adresa, grad, drzava, telefon, odobrena_registracija, klinicki_centar_id, zdravstveni_karton_id) values ('Lara', 'Nikolic', '004', '$2y$12$Q71g/k/ItGEpebDRS9727OJ/EutFXhCp1GjpUO4ofljoIGTGs7u0.', 'lara@gmail.com', 'Kralja Petra 11', 'Novi Sad', 'Srbija','0642255696', false, 1, 4);
insert into pacijent (ime, prezime, lbo,  lozinka, email, adresa, grad, drzava, telefon, odobrena_registracija, klinicki_centar_id, zdravstveni_karton_id) values ('Anita', 'Antic', '005', '$2y$12$cttD74SUFF/vnIJ3r6/yYebun0yY1Zji2pNkncrY/b87/e5za29tO', 'anita@gmail.com', 'Mise Dimitrijevica 66', 'Novi Sad', 'Srbija','069221478', true, 1, 3);

insert into tip_pregleda(naziv) values ('dermatoloski pregled');
insert into tip_pregleda(naziv) values ('oftalmoloski pregled');
insert into tip_pregleda(naziv) values ('ginekoloski pregled');
insert into tip_pregleda(naziv) values ('stomatoloski pregled');
insert into tip_pregleda(naziv) values ('psihijatrijski pregled');


insert into lekar_pacijent (pacijent_id, lekar_id) values (1,2);
insert into lekar_pacijent (pacijent_id, lekar_id) values (2,2);
insert into lekar_pacijent (pacijent_id, lekar_id) values (5,2);

insert into tip_pregleda_klinika (tip_pregleda_id, klinika_id) values (1,1);
insert into tip_pregleda_klinika (tip_pregleda_id, klinika_id) values (1,2);
insert into tip_pregleda_klinika (tip_pregleda_id, klinika_id) values (1,3);
--insert into tip_pregleda_klinika (tip_pregleda_id, klinika_id) values (2,1);
insert into tip_pregleda_klinika (tip_pregleda_id, klinika_id) values (2,2);
insert into tip_pregleda_klinika (tip_pregleda_id, klinika_id) values (2,3);
insert into tip_pregleda_klinika (tip_pregleda_id, klinika_id) values (3,1);
insert into tip_pregleda_klinika (tip_pregleda_id, klinika_id) values (3,2);
insert into tip_pregleda_klinika (tip_pregleda_id, klinika_id) values (3,3);
--insert into tip_pregleda_klinika (tip_pregleda_id, klinika_id) values (4,1);
insert into tip_pregleda_klinika (tip_pregleda_id, klinika_id) values (4,2);
insert into tip_pregleda_klinika (tip_pregleda_id, klinika_id) values (4,3);
insert into tip_pregleda_klinika (tip_pregleda_id, klinika_id) values (5,1);
insert into tip_pregleda_klinika (tip_pregleda_id, klinika_id) values (5,2);
insert into tip_pregleda_klinika (tip_pregleda_id, klinika_id) values (5,3);

insert into klinika_pacijent (klinika_id, pacijent_id) values (3,3);
insert into klinika_pacijent (klinika_id, pacijent_id) values (3,5);

insert into medicinska_sestra_pacijent (pacijent_id, medicinska_sestra_id) values (1,2);
insert into medicinska_sestra_pacijent (pacijent_id, medicinska_sestra_id) values (2,2);
insert into medicinska_sestra_pacijent (pacijent_id, medicinska_sestra_id) values (3,1);
insert into medicinska_sestra_pacijent (pacijent_id, medicinska_sestra_id) values (1,1);
insert into medicinska_sestra_pacijent (pacijent_id, medicinska_sestra_id) values (2,1);


insert into pregled(datum, tip_pregleda_id, lekar_id, pacijent_id, klinika_id, cena, status) values ('12-dec-2019 08:00', 1, 2, 1, 3, 3000, true);
insert into pregled(datum, tip_pregleda_id, lekar_id, pacijent_id, klinika_id, cena, status) values ('13-dec-2019 10:00', 2, 1, 2, 3, 5000, true);
insert into pregled(datum, tip_pregleda_id, lekar_id, pacijent_id, klinika_id, cena, status) values ('15-dec-2019 15:00', 3, 2, 3, 3, 1500, false);
insert into pregled(datum, tip_pregleda_id, lekar_id, pacijent_id, klinika_id, cena, status) values ('18-dec-2019 14:00', 4, 1, 4, 1, 2000, true);
insert into pregled(datum, tip_pregleda_id, lekar_id, pacijent_id, klinika_id, cena, status) values ('19-dec-2019 08:00', 2, 2, 2, 2, 3000, false);


insert into slobodni_termin(datum, tip_pregleda_id, lekar_id, klinika_id, cena, status, popust) values ('15-dec-2019', 3, 2, 3, 15, false, 50.0);
insert into slobodni_termin(datum, tip_pregleda_id, lekar_id, klinika_id, cena, status, popust) values ('18-dec-2019', 4, 1, 1, 20, true, 25.0);
insert into slobodni_termin(datum, tip_pregleda_id, lekar_id, klinika_id, cena, status, popust) values ('19-dec-2019', 2, 2, 2, 30, false, 10.0);


insert into sala(naziv, broj, klinika_id) values ('O', 1, 3);
insert into sala(naziv, broj, klinika_id) values ('O', 2, 3);
insert into sala(naziv, broj, klinika_id) values ('P', 1, 3);
insert into sala(naziv, broj, klinika_id) values ('P', 2, 3);
insert into sala(naziv, broj, klinika_id) values ('O', 3, 3);
insert into sala(naziv, broj, klinika_id) values ('O', 1, 2);
insert into sala(naziv, broj, klinika_id) values ('O', 2, 2);
insert into sala(naziv, broj, klinika_id) values ('P', 1, 2);
insert into sala(naziv, broj, klinika_id) values ('P', 2, 2);
insert into sala(naziv, broj, klinika_id) values ('O', 3, 2);

--insert into radni_dan (datum_pocetka, datum_kraja,  medicinska_sestra_id) values ('11-dec-2019 06:00:00+02:00', '11-dec-2019 13:59:00+02:00', 1);
--insert into radni_dan (datum_pocetka, datum_kraja,  medicinska_sestra_id) values ('12-dec-2019 06:00:00+01:00', '12-dec-2019 13:59:00+01:00', 1);
--insert into radni_dan (datum_pocetka, datum_kraja,  medicinska_sestra_id) values ('10-dec-2019 06:00:00+01:00', '10-dec-2019 13:59:00+01:00', 1);
--insert into radni_dan (datum_pocetka, datum_kraja,  medicinska_sestra_id) values ('09-dec-2019 06:00:00+01:00', '09-dec-2019 13:59:00+01:00', 1);
--insert into radni_dan (datum_pocetka, datum_kraja,  medicinska_sestra_id) values ('13-dec-2019 06:00:00+01:00', '13-dec-2019 13:59:00+01:00', 1);
--insert into radni_dan (datum_pocetka, datum_kraja,  medicinska_sestra_id) values ('16-dec-2019 06:00:00+01:00', '16-dec-2019 13:59:00+01:00', 1);

insert into authority (uloga) values ('PACIJENT');
insert into authority (uloga) values ('LEKAR');
insert into authority (uloga) values ('MED_SESTRA');
insert into authority (uloga) values ('ADMIN_KC');
insert into authority (uloga) values ('ADMIN_KLINIKE');

insert into pacijent_authority (pacijent_id, authority_id) values (1,1);
insert into pacijent_authority (pacijent_id, authority_id) values (2,1);
insert into pacijent_authority (pacijent_id, authority_id) values (3,1);
insert into pacijent_authority (pacijent_id, authority_id) values (4,1);
insert into pacijent_authority (pacijent_id, authority_id) values (5,1);

insert into lekar_authority (lekar_id, authority_id) values (1,2);
insert into lekar_authority (lekar_id, authority_id) values (2,2);
insert into lekar_authority (lekar_id, authority_id) values (3,2);
insert into lekar_authority (lekar_id, authority_id) values (4,2);
insert into lekar_authority (lekar_id, authority_id) values (5,2);
insert into lekar_authority (lekar_id, authority_id) values (6,2);

insert into medicinska_sestra_authority (medicinska_sestra_id, authority_id) values (1,3);
insert into medicinska_sestra_authority (medicinska_sestra_id, authority_id) values (2,3);
insert into medicinska_sestra_authority (medicinska_sestra_id, authority_id) values (3,3);

insert into administrator_kc_authority (administrator_kc_id, authority_id) values (1,4);

insert into administrator_klinike_authority (administrator_klinike_id, authority_id) values (1,5);
insert into administrator_klinike_authority (administrator_klinike_id, authority_id) values (2,5);
insert into administrator_klinike_authority (administrator_klinike_id, authority_id) values (3,5);

insert into odmor_odsustvo_medicinska_sestra (medicinska_sestra_id, datum_od, datum_do, opis, tip, status) values (2, '2019-11-21', '2019-12-01', 'odmor ili odsustvo', 1, FALSE);
insert into odmor_odsustvo_medicinska_sestra (medicinska_sestra_id, datum_od, datum_do, opis, tip, status) values (2, '2020-01-01', '2020-01-20', 'odmor ili odsustvo', 0, TRUE);

insert into odmor_odsustvo_lekar (lekar_id, datum_od, datum_do, opis, tip, status) values (2, '2019-11-21', '2019-12-01', 'odmor ili odsustvo', 0, FALSE);

