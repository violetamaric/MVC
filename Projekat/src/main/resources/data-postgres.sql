insert into klinicki_centar (naziv, adresa, opis) values ('Klinicki centar Srbije', 'Pasterova 2','Klinicki centar uvek na usluzi svojim pacijentima');

insert into authority (uloga) values ('PACIJENT');
insert into authority (uloga) values ('LEKAR');
insert into authority (uloga) values ('MED_SESTRA');
insert into authority (uloga) values ('ADMIN_KC');
insert into authority (uloga) values ('ADMIN_KLINIKE');

insert into administratorkc (ime, prezime, lozinka, email, status, klinicki_centar_id) values ('MVC', 'MVC', '$2y$12$YlXYBQHItM7BAtAwRWS5VOe3x0aufhZ3qDz8r5BVdghgZGJMtVY.2', 'mvc@gmail.com', 1, 1);
insert into administratorkc (ime, prezime, lozinka, email, status, klinicki_centar_id) values ('SOKO', 'ORAO', '$2a$10$I58svTrFjZcbyqbooOB.J.izCnJGqHvbDLPuR6F8aj2lrV2as6BYm', 'orao@gmail.com', 0, 1); 

insert into administrator_kc_authority (administrator_kc_id, authority_id) values (1,4);
insert into administrator_kc_authority (administrator_kc_id, authority_id) values (2,4);

insert into klinika (naziv, adresa, opis, klinicki_centar_id, ocena, version, status) values ('Klinika Beograd', 'Pasterova 2','Klinika uvek na usluzi svojim pacijentima',1, 8, 0, 0);
insert into klinika (naziv, adresa, opis, klinicki_centar_id, ocena, version, status) values ('Klinika Nis', 'Bulevar dr Zorana Djindjica 48','Klinika uvek na usluzi svojim pacijentima',1, 9, 0, 0);
insert into klinika (naziv, adresa, opis, klinicki_centar_id, ocena, version, status) values ('Klinika Novi Sad', 'Hajduk Veljkova 1','Klinika uvek na usluzi svojim pacijentima',1, 10, 0, 0);

insert into administrator_klinike (ime, prezime, lozinka, email, telefon, status, klinika_id) values ('Magdalena', 'Lakic', '$2y$12$XYkK0PZgshWgicsvaRF5IONp/ayRb15YJbSAYYI.Pguv05UI85s7S', 'magdalena@gmail.com', '066/000-665',1, 3);
insert into administrator_klinike (ime, prezime, lozinka, email, telefon, status, klinika_id) values ('Ceca', 'Antesevic', '$2y$12$0u9Lrx8s9VDyMp/MttaHseqJbQ0l0lLRmzHbaWCrtzVPBXtbxVVVO', 'ceca@gmail.com', '061/422-852', 0, 2);
insert into administrator_klinike (ime, prezime, lozinka, email, telefon, status, klinika_id) values ('Viki', 'Maric', '$2y$12$IYv24FLHC3QXGryKQgcgYubTQxsWYWihRJO19mW8BV93Gk6dIlFM.', 'viki@gmail.com', '064/656-969', 1, 1);

insert into administrator_klinike_authority (administrator_klinike_id, authority_id) values (1,5);
insert into administrator_klinike_authority (administrator_klinike_id, authority_id) values (2,5);
insert into administrator_klinike_authority (administrator_klinike_id, authority_id) values (3,5);

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

insert into lekar (ime, prezime, email, lozinka,klinika_id, ocena, telefon, status) values ('Milica','Markovic', 'micacica@gmail.com', '$2y$12$TSUwPf/Ji7j5xr2eq6QjaugOzsFjNnKo.TedGrtdRsugH7hhFhtBW', 3, 7, '066/243-665', 0);
insert into lekar (ime, prezime, email, lozinka,klinika_id, ocena, telefon, status) values ('Jovan','Jovanovic', 'jole@gmail.com', '$2y$12$7hS4PjDxhrvZJtxaGseXOOlR52ZyNlS3fa4X0Z/dQHLv7W69gVbi.', 3, 7, '063/200-765', 1);
insert into lekar (ime, prezime, email, lozinka,klinika_id, ocena, telefon, status) values ('Dusan','Dusanovic', 'duskodule@gmail.com', '$2y$12$VNa2wnV9nqBp082R9OyfleQVTTJlV9EcuEApVR8wSUHYZRXfpg.uy', 3, 6, '066/993-785', 1);
insert into lekar (ime, prezime, email, lozinka,klinika_id, ocena, telefon, status) values ('Marija','Maric', 'marija@gmail.com', '$2y$12$3QwsJvo3YK7ybzuo0fkk3uRDtC3X.wdnuAGFSsMBehrE8gYOMNkx.', 3, 8, '069/143-665', 0);
insert into lekar (ime, prezime, email, lozinka,klinika_id, ocena, telefon, status) values ('Jelena','Janic', 'jelena@gmail.com', '$2y$12$jlxdajA.m3/vGCTBxOKHPuxV5vP71kNKvsd53LvnaMhogXaTpjy/6', 3, 10, '063/209-785', 1);
insert into lekar (ime, prezime, email, lozinka,klinika_id, ocena, telefon, status) values ('Ivan','Ivanovic', 'ivan@gmail.com', '$2y$12$4C0Aj6sJ4aeNgFVLb4kN3e/Cv5AjEI24g9hfr6tIPtDoLwhsIvCd.', 3, 9, '068/953-445', 1);
insert into lekar (ime, prezime, email, lozinka,klinika_id, ocena, telefon, status) values ('Mirko','Mirkovic', 'mirko@gmail.com', '$2y$12$sv7FSvNrcxRVZVZT41utauowcOHB9HVHvjqtCkcxL8.5nC/XaxWcC', 2, 7, '065/955-445', 0);
insert into lekar (ime, prezime, email, lozinka,klinika_id, ocena, telefon, status) values ('Jovana','Ilic', 'jovana@gmail.com', '$2y$12$8tBONOj41DnGxrA4Su2BWOtk60BfFAVdo9lCMK861AGZ6/vxNQKda', 3, 8, '068/555-445', 1);
insert into lekar (ime, prezime, email, lozinka,klinika_id, ocena, telefon, status) values ('Mitar','Djordjevic', 'mitar@gmail.com', '$2y$12$ehhRFMSy6vRnnZmN/uEXKOLl2LjBfi18DyEhl27L4tFji22GyGFeG', 2, 7, '068/953-333', 0);
insert into lekar (ime, prezime, email, lozinka,klinika_id, ocena, telefon, status) values ('Helena','Lakic', 'hela@gmail.com', '$2a$10$s9lGJ1n4jE.U2ZjjOhEBSO8g5RPe5wg4fcViVjVxaFe6Ouz97pLhm', 1, 8, '068/953-333', 1);

insert into lekar_authority (lekar_id, authority_id) values (1,2);
insert into lekar_authority (lekar_id, authority_id) values (2,2);
insert into lekar_authority (lekar_id, authority_id) values (3,2);
insert into lekar_authority (lekar_id, authority_id) values (4,2);
insert into lekar_authority (lekar_id, authority_id) values (5,2);
insert into lekar_authority (lekar_id, authority_id) values (6,2);
insert into lekar_authority (lekar_id, authority_id) values (7,2);
insert into lekar_authority (lekar_id, authority_id) values (8,2);
insert into lekar_authority (lekar_id, authority_id) values (9,2);
insert into lekar_authority (lekar_id, authority_id) values (10,2);

insert into medicinska_sestra (ime, prezime, email, br_telefona, lozinka, klinika_id, status) values ('Jeca','Jelenovic', 'jeca@gmail.com', '066/222-665', '$2y$12$ckUemsooeJoZ8mWv0h83G.pENxUrVVIMr2jgclhFFVepHGRPwpAtG', 3, 0);
insert into medicinska_sestra (ime, prezime, email, br_telefona, lozinka, klinika_id, status) values ('Olgica','Olganovic', 'olgaolgaa@gmail.com','063/222-765', '$2y$12$a.k4FY.bo4SmCenCbJZxEOxrWKtaOaLX2ubDV4/0aAmMX98K6onmu', 3, 0);
insert into medicinska_sestra (ime, prezime, email, br_telefona, lozinka, klinika_id, status) values ('Gordana','Gordanovic', 'gocagordana@gmail.com', '066/963-785', '$2y$12$RWNCcUWoAu9Ez5EUZCrDheKlhAAdWpKKcs3NoSlenGYmNrzBkCVlK', 3, 1);

insert into medicinska_sestra_authority (medicinska_sestra_id, authority_id) values (1,3);
insert into medicinska_sestra_authority (medicinska_sestra_id, authority_id) values (2,3);
insert into medicinska_sestra_authority (medicinska_sestra_id, authority_id) values (3,3);

insert into zdravstveni_karton (visina, tezina, krvna_grupa) values (180, 80, 'AB');
insert into zdravstveni_karton (visina, tezina, krvna_grupa) values (190, 90, 'A');
insert into zdravstveni_karton (visina, tezina, krvna_grupa) values (185, 85, 'B');
insert into zdravstveni_karton (visina, tezina, krvna_grupa) values (195, 95, '0');
insert into zdravstveni_karton (visina, tezina, krvna_grupa) values (170, 65, '0');

insert into pacijent (ime, prezime, lbo, jmbg, lozinka, email, adresa, grad, drzava, telefon, odobrena_registracija, klinicki_centar_id, zdravstveni_karton_id) values ('Pera', 'Peric', '00136547865', '0612967800002', '$2y$12$quB17Sy.NyfuOK2gV0xAYuMWL9Hq7D.enZa/iVlDh/4qz.5dbr8fy', 'pera@gmail.com', 'Temerinska 55','Novi Sad', 'Srbija','065241359', 2, 1, 1);
insert into pacijent (ime, prezime, lbo, jmbg, lozinka, email, adresa, grad, drzava, telefon, odobrena_registracija, klinicki_centar_id, zdravstveni_karton_id) values ('Mita', 'Mitic', '00234212256', '0504988800520', '$2y$12$dsfxuPfCtOdH/1hNS6nYg.bV9KVvYSV8ieibaUVvnoPbDaQmAK3Y.', 'mita@gmail.com', 'Petra Drapsina 89', 'Novi Sad', 'Srbija','0642255696', 0, 1, 2);
insert into pacijent (ime, prezime, lbo, jmbg, lozinka, email, adresa, grad, drzava, telefon, odobrena_registracija, klinicki_centar_id, zdravstveni_karton_id) values ('Jovan', 'Jovicic', '00378975346', '1511965840036', '$2y$12$aKFrJ1lPai6EBwofijLvOOw67h2GD2sxlBf8TdKPc3ykqKIfo8ayW', 'jovan@gmail.com', 'Petra Kocica 66', 'Novi Sad', 'Srbija','061221478', 2, 1, 3);
insert into pacijent (ime, prezime, lbo, jmbg, lozinka, email, adresa, grad, drzava, telefon, odobrena_registracija, klinicki_centar_id, zdravstveni_karton_id) values ('Lara', 'Nikolic', '00487885437', '2306999804556', '$2y$12$Q71g/k/ItGEpebDRS9727OJ/EutFXhCp1GjpUO4ofljoIGTGs7u0.', 'lara@gmail.com', 'Kralja Petra 11', 'Novi Sad', 'Srbija','0642255696', 0, 1, 4);
insert into pacijent (ime, prezime, lbo, jmbg, lozinka, email, adresa, grad, drzava, telefon, odobrena_registracija, klinicki_centar_id, zdravstveni_karton_id) values ('Anita', 'Antic', '00500987234', '0105949806635', '$2y$12$cttD74SUFF/vnIJ3r6/yYebun0yY1Zji2pNkncrY/b87/e5za29tO', 'anita@gmail.com', 'Mise Dimitrijevica 66', 'Novi Sad', 'Srbija','069221478', 2, 1, 5);

insert into pacijent_authority (pacijent_id, authority_id) values (1,1);
insert into pacijent_authority (pacijent_id, authority_id) values (2,1);
insert into pacijent_authority (pacijent_id, authority_id) values (3,1);
insert into pacijent_authority (pacijent_id, authority_id) values (4,1);
insert into pacijent_authority (pacijent_id, authority_id) values (5,1);

insert into klinika_pacijent (klinika_id, pacijent_id) values (3, 3);
insert into klinika_pacijent (klinika_id, pacijent_id) values (3, 5);

insert into tip_pregleda(naziv, cena) values ('dermatoloski pregled', 3000);
insert into tip_pregleda(naziv, cena) values ('oftalmoloski pregled', 5500);
insert into tip_pregleda(naziv, cena) values ('ginekoloski pregled', 2500);
insert into tip_pregleda(naziv, cena) values ('stomatoloski pregled', 1500);
insert into tip_pregleda(naziv, cena) values ('psihijatrijski pregled', 2000);
insert into tip_pregleda(naziv, cena) values ('nefroloski pregled', 2350);
insert into tip_pregleda(naziv, cena) values ('imunoloski pregled', 3050);

insert into lekar_pacijent (pacijent_id, lekar_id) values (1, 2);
insert into lekar_pacijent (pacijent_id, lekar_id) values (2, 2);
insert into lekar_pacijent (pacijent_id, lekar_id) values (5, 2);
insert into lekar_pacijent (pacijent_id, lekar_id) values (1, 1);
insert into lekar_pacijent (pacijent_id, lekar_id) values (2, 1);
insert into lekar_pacijent (pacijent_id, lekar_id) values (3, 1);
insert into lekar_pacijent (pacijent_id, lekar_id) values (4, 3);
insert into lekar_pacijent (pacijent_id, lekar_id) values (1, 4);
insert into lekar_pacijent (pacijent_id, lekar_id) values (2, 5);
insert into lekar_pacijent (pacijent_id, lekar_id) values (3, 6);
insert into lekar_pacijent (pacijent_id, lekar_id) values (4, 7);
insert into lekar_pacijent (pacijent_id, lekar_id) values (3, 8);
insert into lekar_pacijent (pacijent_id, lekar_id) values (2, 9);
insert into lekar_pacijent (pacijent_id, lekar_id) values (2, 10);

insert into tip_pregleda_klinika (tip_pregleda_id, klinika_id) values (1,1);
insert into tip_pregleda_klinika (tip_pregleda_id, klinika_id) values (1,3);
insert into tip_pregleda_klinika (tip_pregleda_id, klinika_id) values (2,2);
insert into tip_pregleda_klinika (tip_pregleda_id, klinika_id) values (2,3);
insert into tip_pregleda_klinika (tip_pregleda_id, klinika_id) values (3,1);
insert into tip_pregleda_klinika (tip_pregleda_id, klinika_id) values (3,2);
insert into tip_pregleda_klinika (tip_pregleda_id, klinika_id) values (3,3);
insert into tip_pregleda_klinika (tip_pregleda_id, klinika_id) values (4,2);
insert into tip_pregleda_klinika (tip_pregleda_id, klinika_id) values (4,3);
insert into tip_pregleda_klinika (tip_pregleda_id, klinika_id) values (5,1);
insert into tip_pregleda_klinika (tip_pregleda_id, klinika_id) values (5,2);
insert into tip_pregleda_klinika (tip_pregleda_id, klinika_id) values (5,3);
insert into tip_pregleda_klinika (tip_pregleda_id, klinika_id) values (6,3);
insert into tip_pregleda_klinika (tip_pregleda_id, klinika_id) values (7,3);


insert into medicinska_sestra_pacijent (pacijent_id, medicinska_sestra_id) values (2, 1);
insert into medicinska_sestra_pacijent (pacijent_id, medicinska_sestra_id) values (2, 2);
insert into medicinska_sestra_pacijent (pacijent_id, medicinska_sestra_id) values (3, 1);
insert into medicinska_sestra_pacijent (pacijent_id, medicinska_sestra_id) values (3, 2);
insert into medicinska_sestra_pacijent (pacijent_id, medicinska_sestra_id) values (3, 3);
insert into medicinska_sestra_pacijent (pacijent_id, medicinska_sestra_id) values (4, 2);
insert into medicinska_sestra_pacijent (pacijent_id, medicinska_sestra_id) values (5, 3);


insert into sala(naziv, broj, klinika_id, tip_sale) values ('Sala za pregled', 111, 3, 1);
insert into sala(naziv, broj, klinika_id, tip_sale) values ('Sala za pregled', 96, 3, 1);
insert into sala(naziv, broj, klinika_id, tip_sale) values ('Sala za pregled', 1, 3, 1);
insert into sala(naziv, broj, klinika_id, tip_sale) values ('Sala za pregled', 2, 3, 1);
insert into sala(naziv, broj, klinika_id, tip_sale) values ('Sala za pregled', 333, 3, 1);
insert into sala(naziv, broj, klinika_id, tip_sale) values ('Sala za pregled', 1, 2, 1);
insert into sala(naziv, broj, klinika_id, tip_sale) values ('Sala za pregled', 23, 1, 1);

insert into sala(naziv, broj, klinika_id, tip_sale) values ('Operaciona sala', 1, 1, 0);
insert into sala(naziv, broj, klinika_id, tip_sale) values ('Operaciona sala', 2, 2, 0);
insert into sala(naziv, broj, klinika_id, tip_sale) values ('Operaciona sala', 19, 3, 0);
insert into sala(naziv, broj, klinika_id, tip_sale) values ('Operaciona sala', 221, 3, 0);
insert into sala(naziv, broj, klinika_id, tip_sale) values ('Operaciona sala', 123, 3, 0);
insert into sala(naziv, broj, klinika_id, tip_sale) values ('Operaciona sala', 22, 3, 0);
insert into sala(naziv, broj, klinika_id, tip_sale) values ('Operaciona sala', 225, 3, 0);

insert into operacija (status, datum, termin, tip_operacije, pacijent_id, cena, klinika_id) values (0, '17-mar-2020', 9, 'mala hiruska intervencija', 1, 10000, 1);
insert into operacija (status, datum, termin, tip_operacije, sala_id, pacijent_id, cena, klinika_id) values (1, '13-mar-2020', 11, 'velika hiruska intervencija', 10, 2, 15000, 3);
insert into operacija (status, datum, termin, tip_operacije,  pacijent_id, cena, klinika_id) values (0, '14-mar-2020', 11, 'mala hiruska intervencija',  3, 10000, 3);
insert into operacija (status, datum, termin, tip_operacije,  pacijent_id, cena, klinika_id) values (0, '15-mar-2020', 15, 'velika hiruska intervencija', 1, 20000, 3);
insert into operacija (status, datum, termin, tip_operacije, sala_id, pacijent_id, cena, klinika_id) values (1, '15-mar-2020', 9, 'velika hiruska intervencija', 8, 1, 20000, 1);
insert into operacija (status, datum, termin, tip_operacije,  pacijent_id, cena, klinika_id) values (0, '1-mar-2020', 11, 'velika hiruska intervencija',  3, 32000, 3	);
insert into operacija (status, datum, termin, tip_operacije,  pacijent_id, cena, klinika_id) values (0, '25-feb-2020', 9, 'velika hiruska intervencija',  1, 25555, 2	);

insert into lekar_operacija (operacija_id, lekar_id) values (2, 2);
insert into lekar_operacija (operacija_id, lekar_id) values (2, 3);
insert into lekar_operacija (operacija_id, lekar_id) values (5, 10);


insert into odmor_odsustvo_medicinska_sestra (klinika_id,medicinska_sestra_id, datum_od, datum_do, opis, tip, status) values (3,2, '2020-03-15', '2020-03-17', 'Smrtan slucaj u porodici.', 1, 0);
insert into odmor_odsustvo_medicinska_sestra (klinika_id,medicinska_sestra_id, datum_od, datum_do, opis, tip, status) values (3,2, '2020-05-15', '2020-05-17', 'Smrtan slucaj u porodici.', 1, 1);
insert into odmor_odsustvo_medicinska_sestra (klinika_id,medicinska_sestra_id, datum_od, datum_do, opis, tip, status) values (3,1, '2020-01-01', '2020-01-05', 'Idem za Kanadu.', 0, 0);
insert into odmor_odsustvo_medicinska_sestra (klinika_id,medicinska_sestra_id, datum_od, datum_do, opis, tip, status) values (3,3, '2020-02-02', '2020-02-05', 'Seminar.', 1, 1);

insert into odmor_odsustvo_lekar (klinika_id,lekar_id, datum_od, datum_do, opis, tip, status) values (3,2, '2020-01-21', '2020-01-23', 'Slobodni dani.', 0, 1);
insert into odmor_odsustvo_lekar (klinika_id,lekar_id, datum_od, datum_do, opis, tip, status) values (3,2, '2020-02-20', '2020-02-22', 'Slobodni dani.', 0, 1);
insert into odmor_odsustvo_lekar (klinika_id,lekar_id, datum_od, datum_do, opis, tip, status) values (3,2, '2020-02-17', '2020-02-18', 'Put u Ljubljanu.', 1, 0);
insert into odmor_odsustvo_lekar (klinika_id,lekar_id, datum_od, datum_do, opis, tip, status) values (3,1, '2020-03-05', '2020-03-06', 'Seminar.', 0, 0);
insert into odmor_odsustvo_lekar (klinika_id,lekar_id, datum_od, datum_do, opis, tip, status) values (3,4, '2020-03-15', '2020-03-19', 'Smrtan slucaj u porodici', 0, 1);
insert into odmor_odsustvo_lekar (klinika_id,lekar_id, datum_od, datum_do, opis, tip, status) values (3,3, '2020-03-07', '2020-03-10', 'Putujem u Nemacku.', 0, 1);

insert into termin (datum_pocetka, termin, sala_id, lekar_id ) values ('1-mar-2020', 11, 1, 3);
insert into termin (datum_pocetka, termin, sala_id, lekar_id ) values ('1-mar-2020', 11, 6, 9);
insert into termin (datum_pocetka, termin, sala_id, lekar_id ) values ('1-mar-2020', 9, 1, 1);
insert into termin (datum_pocetka, termin, sala_id, lekar_id ) values ('1-mar-2020', 11, 2, 1);
insert into termin (datum_pocetka, termin, sala_id, lekar_id ) values ('1-mar-2020', 13, 2, 1);
insert into termin (datum_pocetka, termin, sala_id, lekar_id ) values ('1-mar-2020', 15, 1, 2);


insert into termin(datum_pocetka, termin, lekar_id, sala_id) values ('12-mar-2020', 9, 2, 1);
insert into termin(datum_pocetka, termin, lekar_id, sala_id) values ('12-mar-2020', 11, 2, 1);

insert into termin(datum_pocetka, termin, lekar_id, sala_id) values ('13-mar-2020', 9, 1, 2);
insert into termin(datum_pocetka, termin, lekar_id, sala_id) values ('13-mar-2020', 11, 1, 1);
insert into termin(datum_pocetka, termin, lekar_id, sala_id) values ('13-mar-2020', 11, 2, 10);
insert into termin(datum_pocetka, termin, lekar_id, sala_id) values ('13-mar-2020', 11, 3, 10);

insert into termin(datum_pocetka, termin, lekar_id, sala_id) values ('15-mar-2020', 9, 10, 8);
insert into termin(datum_pocetka, termin, lekar_id, sala_id) values ('15-mar-2020', 13, 2, 3);
insert into termin(datum_pocetka, termin, lekar_id, sala_id) values ('15-mar-2020', 9, 2, 5);

insert into termin(datum_pocetka, termin, lekar_id, sala_id) values ('18-mar-2020', 11, 1, 2);

insert into termin(datum_pocetka, termin, lekar_id, sala_id) values ('19-mar-2020', 13, 2, 5);

insert into termin (datum_pocetka, termin, sala_id, lekar_id ) values ('20-mar-2020', 11, 2, 1);
insert into termin (datum_pocetka, termin, sala_id, lekar_id ) values ('20-mar-2020', 13, 1, 1);
insert into termin (datum_pocetka, termin, sala_id, lekar_id ) values ('20-mar-2020', 15, 5, 1);
insert into termin (datum_pocetka, termin, sala_id, lekar_id ) values ('20-mar-2020', 9, 3, 2);
insert into termin (datum_pocetka, termin, sala_id, lekar_id ) values ('20-mar-2020', 9, 4, 1);
insert into termin (datum_pocetka, termin, sala_id, lekar_id ) values ('20-mar-2020', 9, 5, 3);
insert into termin (datum_pocetka, termin, sala_id, lekar_id ) values ('20-mar-2020', 9, 6, 4);
insert into termin (datum_pocetka, termin, sala_id, lekar_id ) values ('20-mar-2020', 9, 7, 5);
insert into termin (datum_pocetka, termin, sala_id, lekar_id ) values ('20-mar-2020', 9, 1, 6);
insert into termin (datum_pocetka, termin, sala_id, lekar_id ) values ('20-mar-2020', 9, 2, 8);
insert into termin (datum_pocetka, termin, sala_id, lekar_id ) values ('20-mar-2020', 11, 5, 2);
insert into termin (datum_pocetka, termin, sala_id, lekar_id ) values ('20-mar-2020', 13, 3, 2);
insert into termin (datum_pocetka, termin, sala_id, lekar_id ) values ('20-mar-2020', 11, 6, 9);
insert into termin (datum_pocetka, termin, sala_id, lekar_id ) values ('20-mar-2020', 13, 2, 3);

insert into termin (datum_pocetka, termin, sala_id, lekar_id ) values ('21-mar-2020', 9, 1, 1);
insert into termin (datum_pocetka, termin, sala_id, lekar_id ) values ('21-mar-2020', 11, 2, 1);
insert into termin (datum_pocetka, termin, sala_id, lekar_id ) values ('21-mar-2020', 13, 3, 1);
insert into termin (datum_pocetka, termin, sala_id, lekar_id ) values ('21-mar-2020', 9, 4, 2);
insert into termin (datum_pocetka, termin, sala_id, lekar_id ) values ('21-mar-2020', 13, 5, 2);
insert into termin (datum_pocetka, termin, sala_id, lekar_id ) values ('21-mar-2020', 13, 6, 9);


insert into termin (datum_pocetka, termin, sala_id, lekar_id ) values ('25-feb-2020', 9, 1, 1);
insert into termin (datum_pocetka, termin, sala_id, lekar_id ) values ('25-feb-2020', 13, 2, 1);

insert into termin (datum_pocetka, termin, sala_id, lekar_id ) values ('26-mar-2020', 9, 3, 1);
insert into termin (datum_pocetka, termin, sala_id, lekar_id ) values ('26-mar-2020', 11, 3, 1);
insert into termin (datum_pocetka, termin, sala_id, lekar_id ) values ('26-mar-2020', 13, 3, 1);
insert into termin (datum_pocetka, termin, sala_id, lekar_id ) values ('26-mar-2020', 15, 4, 2);

insert into termin (datum_pocetka, termin, sala_id, lekar_id ) values ('27-mar-2020', 15, 1, 1);

--zahtevi za pregled
insert into pregled(datum, tip_pregleda_id, lekar_id, pacijent_id, klinika_id, cena, status, termin) values ('26-mar-2020', 1, 2, 1, 3, 3000, 0, 15);
insert into pregled(datum, tip_pregleda_id, lekar_id, pacijent_id, klinika_id, cena, status, termin) values ('26-mar-2020', 2, 1, 2, 3, 5000, 0, 11);
insert into pregled(datum, tip_pregleda_id, lekar_id, pacijent_id, klinika_id, cena, status, termin) values ('20-mar-2020', 3, 2, 3, 3, 1500, 0, 9);
insert into pregled(datum, tip_pregleda_id, lekar_id, pacijent_id, klinika_id, cena, status, termin) values ('20-mar-2020', 4, 1, 4, 3, 2000, 0, 13);
insert into pregled(datum, tip_pregleda_id, lekar_id, pacijent_id, klinika_id, cena, status, termin) values ('13-mar-2020', 2, 2, 2, 3, 3000, 0, 9);

--zavrseni pregledi
insert into pregled(datum, tip_pregleda_id, lekar_id, pacijent_id, klinika_id, cena, status, sala_id, termin) values('26-jan-2020', 1, 1, 1, 3, 1500, 3, 2, 9);
insert into pregled(datum, tip_pregleda_id, lekar_id, pacijent_id, klinika_id, cena, status, sala_id, termin) values('26-jan-2020', 1, 1, 1, 3, 1500, 3, 3, 11);

--pregledi koji cekaju cekaju odobrenje pacijenta
insert into pregled(datum, tip_pregleda_id, lekar_id, pacijent_id, klinika_id, cena, status, sala_id, termin) values('26-mar-2020', 3, 1, 2, 3, 3000, 0, 3, 13);
insert into pregled(datum, tip_pregleda_id, lekar_id, pacijent_id, klinika_id, cena, status, sala_id, termin) values('26-mar-2020', 5, 2, 3, 3, 2000, 0, 4, 15);

--pregledi koje je pacijent potvrdio
insert into pregled(datum, tip_pregleda_id, lekar_id, pacijent_id, klinika_id, cena, status, sala_id, termin) values ('12-mar-2020', 1, 2, 1, 3, 3000, 1, 1, 11);
insert into pregled(datum, tip_pregleda_id, lekar_id, pacijent_id, klinika_id, cena, status, sala_id, termin) values ('13-mar-2020', 2, 1, 2, 3, 5000, 1, 2, 11);
insert into pregled(datum, tip_pregleda_id, lekar_id, pacijent_id, klinika_id, cena, status, sala_id, termin) values ('15-mar-2020', 3, 2, 3, 3, 1500, 1, 3, 13);
insert into pregled(datum, tip_pregleda_id, lekar_id, pacijent_id, klinika_id, cena, status, sala_id, termin) values ('27-mar-2020', 5, 1, 4, 3, 1500, 1, 1, 15);
insert into pregled(datum, tip_pregleda_id, lekar_id, pacijent_id, klinika_id, cena, status, sala_id, termin) values ('20-mar-2020', 1, 3, 1, 3, 2500, 1, 2, 11);
insert into pregled(datum, tip_pregleda_id, lekar_id, pacijent_id, klinika_id, cena, status, sala_id, termin) values ('26-mar-2020', 2, 1, 2, 3, 5000, 1, 3, 11);
insert into pregled(datum, tip_pregleda_id, lekar_id, pacijent_id, klinika_id, cena, status, sala_id, termin) values ('20-mar-2020', 3, 2, 3, 3, 1500, 1, 3, 9);
--insert into pregled(datum, tip_pregleda_id, lekar_id, pacijent_id, klinika_id, cena, status, sala_id, termin) values ('20-mar-2020', 3, 1, 1, 3, 1500, 1, 4, 9);
insert into pregled(datum, tip_pregleda_id, lekar_id, pacijent_id, klinika_id, cena, status, sala_id, termin) values ('20-mar-2020', 3, 3, 2, 3, 1500, 1, 5, 9);
insert into pregled(datum, tip_pregleda_id, lekar_id, pacijent_id, klinika_id, cena, status, sala_id, termin) values ('20-mar-2020', 3, 4, 3, 3, 1500, 1, 6, 9);
insert into pregled(datum, tip_pregleda_id, lekar_id, pacijent_id, klinika_id, cena, status, sala_id, termin) values ('20-mar-2020', 3, 5, 4, 3, 1500, 1, 7, 9);
insert into pregled(datum, tip_pregleda_id, lekar_id, pacijent_id, klinika_id, cena, status, sala_id, termin) values ('20-mar-2020', 3, 6, 5, 3, 1500, 1, 1, 9);
--insert into pregled(datum, tip_pregleda_id, lekar_id, pacijent_id, klinika_id, cena, status, sala_id, termin) values ('20-mar-2020', 3, 8, 1, 3, 1500, 1, 2, 9);


insert into izvestajopregledu (sadrzaj, dijagnoza_id, pregled_id, zdravstveni_karton_id) values ('Pacijent se zalio na tegobe u delu grudnog kosa. Potrebno odraditi dodatne analize radi odredjivanja preciznije dijagnoze', 2, 6, 1);
insert into izvestajopregledu (sadrzaj, dijagnoza_id, pregled_id, zdravstveni_karton_id) values ('Pacijent je dobio velike boginje', 5, 7, 1);

insert into recept (izvestajopregledu_id, lek_id, medicinska_sestra_id, medicinska_sestra_overa) values (1, 1, 1, true);
insert into recept (izvestajopregledu_id, lek_id, medicinska_sestra_id, medicinska_sestra_overa) values (1, 2, 1, true);
insert into recept (izvestajopregledu_id, lek_id, medicinska_sestra_id, medicinska_sestra_overa) values (1, 3, 1, false);
insert into recept (izvestajopregledu_id, lek_id, medicinska_sestra_id, medicinska_sestra_overa) values (2, 4, 2, true);
insert into recept (izvestajopregledu_id, lek_id, medicinska_sestra_id, medicinska_sestra_overa) values (2, 5, 2, true);
insert into recept (izvestajopregledu_id, lek_id, medicinska_sestra_id, medicinska_sestra_overa) values (2, 6, 2, true);
insert into recept (izvestajopregledu_id, lek_id, medicinska_sestra_id, medicinska_sestra_overa) values (2, 7, 2, true);

insert into slobodni_termin(datum, tip_pregleda_id, lekar_id, klinika_id, cena, status, popust, sala_id, termin) values ('15-mar-2020', 3, 2, 3, 1500, false, 50.0, 1, 9);
insert into slobodni_termin(datum, tip_pregleda_id, lekar_id, klinika_id, cena, status, popust, sala_id, termin) values ('18-mar-2020', 4, 1, 3, 2000, false, 25.0, 2, 11);
insert into slobodni_termin(datum, tip_pregleda_id, lekar_id, klinika_id, cena, status, popust, sala_id, termin) values ('19-mar-2020', 2, 2, 3, 3000, false, 10.0, 5, 13);


