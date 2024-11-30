package com.example.warsztat_samochodowy.service;

import com.example.warsztat_samochodowy.dto.PojazdKlientDto;
import com.example.warsztat_samochodowy.dto.NaprawaDto;
import com.example.warsztat_samochodowy.exception.*;
import com.example.warsztat_samochodowy.model.Klient;
import com.example.warsztat_samochodowy.model.Mechanik;
import com.example.warsztat_samochodowy.model.Naprawa;
import com.example.warsztat_samochodowy.model.Pojazd;
import com.example.warsztat_samochodowy.repository.KlientRepository;
import com.example.warsztat_samochodowy.repository.MechanikRepository;
import com.example.warsztat_samochodowy.repository.NaprawaRepository;
import com.example.warsztat_samochodowy.repository.PojazdRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service

public class Warsztat_serwis {

    private KlientRepository klientRepository;
    private MechanikRepository mechanikRepository;
    private NaprawaRepository naprawaRepository;
    private PojazdRepository pojazdRepository;

    public Warsztat_serwis(KlientRepository klientRepository, MechanikRepository mechanikRepository, NaprawaRepository naprawaRepository, PojazdRepository pojazdRepository) {
        this.klientRepository = klientRepository;
        this.mechanikRepository = mechanikRepository;
        this.naprawaRepository = naprawaRepository;
        this.pojazdRepository = pojazdRepository;
    }

    public List<Klient>Podglad_klientow(){
        List<Klient> listaKlientow = new ArrayList<>();
        listaKlientow = klientRepository.findAll();
        return listaKlientow;
    }
    public Klient Dodawanie_klienta(Klient klient) {
        try {
            return klientRepository.save(klient);
        } catch (Exception e) {
            throw new KlientAlreadyExistException("Klient z podanym numerem telefonu już istnieje w bazie");
        }
    }

    public Klient Modyfikacje_danych_klienta(Klient klient){
        Optional<Klient> staryKlient = klientRepository.findByTelefon(klient.getTelefon());

        if(staryKlient.isPresent()){
            //staryKlient.get().setKlientID(klient.getKlientID());
            staryKlient.get().setImie(klient.getImie());
            staryKlient.get().setNazwisko(klient.getNazwisko());
            //staryKlient.get().setTelefon(klient.getTelefon());
            staryKlient.get().setEmail(klient.getEmail());
            return klientRepository.save(staryKlient.get());
        }
        else
        {
            throw new EntityNotFoundException("Klient nie istnieje w bazie");
        }

    }
    public List<Mechanik>Podglad_mechanikow(){
        List<Mechanik> listaMechanikow = new ArrayList<>();
        listaMechanikow = mechanikRepository.findAll();
        return listaMechanikow;
    }
    public Mechanik Dodawanie_mechanika(Mechanik mechanik){
        Mechanik nowyMechanik = new Mechanik(mechanik.getImie(), mechanik.getNazwisko());
        Optional<Mechanik> mechanikWBazie = mechanikRepository.findByImieAndNazwisko(mechanik.getImie(), mechanik.getNazwisko());
        if(mechanikWBazie.isEmpty()){
            return mechanikRepository.save(nowyMechanik);
        }
        else {
            throw new KlientAlreadyExistException("Mechanik już istnieje w bazie");
        }
    }
    public void Zwolnienie_mechanika(Mechanik mechanik){
        // sprawdzic czy mechanik istnieje w bazie
        Optional<Mechanik> mechanikZBazyDanych = mechanikRepository.findByImieAndNazwisko(mechanik.getImie(), mechanik.getNazwisko());
        if(mechanikZBazyDanych.isPresent()){
            mechanikZBazyDanych.get().setCzyZatrudniony("NIE");
            mechanikRepository.save(mechanikZBazyDanych.get());
        }
        else
        {
            throw new EntityNotFoundException("Mechanik nie istnieje w bazie");
        }
    }
    public List<Naprawa>Podglad_napraw(){

        List<Naprawa> listaNapraw = new ArrayList<>();
        listaNapraw = naprawaRepository.findAll();
        return listaNapraw;
    }
    public Naprawa Dodanie_mechanika_do_naprawy(NaprawaDto naprawaDto) {
        //Optional<Pojazd> pojazd = pojazdRepository.findByVin(naprawaDto.getPojazd().getVIN());
        Optional<Mechanik> mechanik = mechanikRepository.findByImieAndNazwisko(naprawaDto.getMechanik().getImie(), naprawaDto.getMechanik().getNazwisko());
        if (mechanik.isEmpty()) {
            throw new MechanikNotFoundError("Nie znalezniono mechanika z podanym imieniem i nazwiskiem");
        }
        Optional<Naprawa> naprawa = naprawaRepository.findById(naprawaDto.getNaprawaID());
        if (naprawa.isEmpty()) {
            throw new NaprawaNotFoundError("Nie znaleziono podanej naprawy. Nie udało się dodać mechanika");
        }
        naprawa.get().setMechanik(mechanik.get());
        return naprawaRepository.save(naprawa.get());
    }

    public Naprawa Dodawanie_naprawy(Naprawa naprawa) {
        return naprawaRepository.save(naprawa);
    }
    public List<Pojazd>Podglad_pojazdow(){

        List<Pojazd> listaPojazdow = new ArrayList<>();
        listaPojazdow = pojazdRepository.findAll();
        return listaPojazdow;
    }
    public Pojazd Dodawanie_pojazdu(Pojazd pojazd, String telefon){
        Optional<Klient> klient = klientRepository.findByTelefon(telefon);
        Optional<Pojazd> staryPojazd = pojazdRepository.findByVin(pojazd.getVIN());
        if(staryPojazd.isPresent()){
            throw new PojazdAlreadyExistError("Pojazd z podanym numerem VIN istnieje już w bazie");
        }
        if (klient.isEmpty()) {
            throw new KlientNotFoundError("Klient z podanym telefonem nie istnieje w bazie");
        }
        pojazd.setKlient(klient.get());
        return pojazdRepository.save(pojazd);

    }
    public void Modyfikacje_danych_pojazdu(Pojazd pojazd){
        Optional<Pojazd> staryPojazd = pojazdRepository.findByVin(pojazd.getVIN());

        if(staryPojazd.isPresent()){
            staryPojazd.get().setPojazdID(pojazd.getPojazdID());
            staryPojazd.get().setMarka(pojazd.getMarka());
            staryPojazd.get().setModel(pojazd.getModel());
            staryPojazd.get().setRejestracja(pojazd.getRejestracja());
            staryPojazd.get().setRocznik(pojazd.getRocznik());
            pojazdRepository.save(staryPojazd.get());
        }
        else
        {
            throw new EntityNotFoundException("Pojazd nie istnieje w bazie");
        }

    }

    public Naprawa Dodanie_nowego_zgloszenia(Klient klient, Pojazd pojazd) {

        Optional<Klient> klientWBazie = klientRepository.findByTelefon(klient.getTelefon());
        Optional<Pojazd> pojazdWBazie = pojazdRepository.findByVin(pojazd.getVIN());
        Klient savedKlient = klientWBazie.orElseGet(() -> Dodawanie_klienta(klient));
        Naprawa nowa_naprawa;
        if (pojazdWBazie.isEmpty()) {
            Pojazd savedPojazd = Dodawanie_pojazdu(pojazd, klient.getTelefon());// Dodawanie_pojazdu(pojazd, klient);
            nowa_naprawa = new Naprawa(savedPojazd);
        } else {
            if (!pojazdWBazie.get().getKlient().getTelefon().equals(savedKlient.getTelefon())) {
                throw new KlientAlreadyExistException("Pojazd posiada już właściciela z innym numerem telefonu");
            }
            nowa_naprawa = new Naprawa(pojazdWBazie.get());
        }

        return Dodawanie_naprawy(nowa_naprawa);
    }

}
