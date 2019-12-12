
insert into klinicki_centar (naziv, adresa, opis) values ('Klinicki centar Srbije', 'Pasterova 2','Klinicki centar uvek na usluzi svojim pacijentima');

insert into administratorkc (ime, prezime, lozinka, email, klinicki_centar_id) values ('MVC', 'MVC', 'mvc', 'mvc@gmail.com', 1);

insert into klinika (naziv, adresa, opis, klinicki_centar_id, ocena) values ('Klinika Beograd', 'Pasterova 2','Klinika uvek na usluzi svojim pacijentima',1, 8);
insert into klinika (naziv, adresa, opis, klinicki_centar_id, ocena) values ('Klinika Nis', 'Bulevar dr Zorana Djindjica 48','Klinika uvek na usluzi svojim pacijentima',1, 9);
insert into klinika (naziv, adresa, opis, klinicki_centar_id, ocena) values ('Klinika Novi Sad', 'Hajduk Veljkova 1','Klinika uvek na usluzi svojim pacijentima',1, 10);

insert into administrator_klinike (ime, prezime, lozinka, email, telefon, klinika_id) values ('Maga', 'Lakic', 'maga', 'maga@gmail.com', '066/000-665', 3);
insert into administrator_klinike (ime, prezime, lozinka, email, telefon, klinika_id) values ('Ceca', 'Antesevic', 'ceca', 'ceca@gmail.com', '061/422-852', 2);
insert into administrator_klinike (ime, prezime, lozinka, email, telefon, klinika_id) values ('Viki', 'Maric', 'viki', 'viki@gmail.com', '064/656-969', 1);

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

insert into lekar (ime, prezime, email, lozinka,klinika_id, ocena, telefon) values ('Milica','Markovic', 'micacica@gmail.com', 'mica', 1, 0, '066/243-665');
insert into lekar (ime, prezime, email, lozinka,klinika_id, ocena,  telefon) values ('Jovan','Jovanovic', 'jole@gmail.com', 'jole', 3, 0, '063/200-765');
insert into lekar (ime, prezime, email, lozinka,klinika_id, ocena, telefon) values ('Dusan','Dusanovic', 'duskodule@gmail.com', 'dule', 3, 0, '066/993-785');
insert into lekar (ime, prezime, email, lozinka,klinika_id, ocena, telefon) values ('Marija','Maric', 'marija@gmail.com', 'marija', 3, 5, '069/143-665');
insert into lekar (ime, prezime, email, lozinka,klinika_id, ocena,  telefon) values ('Jelena','Janic', 'jelena@gmail.com', 'jelena', 3, 0, '063/209-785');
insert into lekar (ime, prezime, email, lozinka,klinika_id, ocena, telefon) values ('Ivan','Ivanovic', 'ivan@gmail.com', 'ivan', 3, 0, '068/953-445');

insert into medicinska_sestra (ime, prezime, email, br_telefona, lozinka,klinika_id) values ('Jelena','Jelenovic', 'jelena@gmail.com', '066/222-665', 'jeca', 1);
insert into medicinska_sestra (ime, prezime, email, br_telefona, lozinka,klinika_id) values ('Olgica','Olganovic', 'olgaolgaa@gmail.com','063/222-765', 'olga', 2);
insert into medicinska_sestra (ime, prezime, email, br_telefona, lozinka,klinika_id) values ('Gordana','Gordanovic', 'gocagordana@gmail.com', '066/963-785', 'goca', 3);

insert into sala (broj, klinika_id) values ('001C', 1);
insert into sala (broj, klinika_id) values ('002B', 2);
insert into sala (broj, klinika_id) values ('003A', 3);

insert into zdravstveni_karton (visina, tezina, krvna_grupa) values (180,80, 'AB');
insert into zdravstveni_karton (visina, tezina, krvna_grupa) values (190,90, 'A');
insert into zdravstveni_karton (visina, tezina, krvna_grupa) values (185,85, 'B');
insert into zdravstveni_karton (visina, tezina, krvna_grupa) values (195,95, '0');

insert into pacijent (ime, prezime, lbo,  lozinka, email, adresa, grad, drzava, telefon, odobrena_registracija, klinicki_centar_id, zdravstveni_karton_id) values ('Pera', 'Peric', '001', 'pera', 'pera@gmail.com', 'Temerinska 55','Novi Sad', 'Srbija','065241359', true, 1, 1);
insert into pacijent (ime, prezime, lbo,  lozinka, email, adresa, grad, drzava, telefon, odobrena_registracija, klinicki_centar_id, zdravstveni_karton_id) values ('Mita', 'Mitic', '002', 'mita', 'mita@gmail.com', 'Petra Drapsina 89', 'Novi Sad', 'Srbija','0642255696', false, 1, 2);
insert into pacijent (ime, prezime, lbo,  lozinka, email, adresa, grad, drzava, telefon, odobrena_registracija, klinicki_centar_id, zdravstveni_karton_id) values ('Jovan', 'Jovicic', '003', 'jovan', 'jovan@gmail.com', 'Petra Kocica 66', 'Novi Sad', 'Srbija','061221478', true, 1, 3);
insert into pacijent (ime, prezime, lbo,  lozinka, email, adresa, grad, drzava, telefon, odobrena_registracija, klinicki_centar_id, zdravstveni_karton_id) values ('Lara', 'Nikolic', '004', 'lara', 'lara@gmail.com', 'Kralja Petra 11', 'Novi Sad', 'Srbija','0642255696', false, 1, 4);

insert into lekar_pacijent (pacijent_id, lekar_id) values (1,2);
insert into lekar_pacijent (pacijent_id, lekar_id) values (2,2);

insert into medicinska_sestra_pacijent (pacijent_id, medicinska_sestra_id) values (1,2);
insert into medicinska_sestra_pacijent (pacijent_id, medicinska_sestra_id) values (2,2);



