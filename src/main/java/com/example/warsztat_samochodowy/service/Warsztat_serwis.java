package com.example.warsztat_samochodowy.service;

import com.example.warsztat_samochodowy.model.Klient;
import com.example.warsztat_samochodowy.model.Mechanik;
import com.example.warsztat_samochodowy.model.Naprawa;
import com.example.warsztat_samochodowy.model.Pojazd;
import com.example.warsztat_samochodowy.repository.KlientRepository;
import com.example.warsztat_samochodowy.repository.MechanikRepository;
import com.example.warsztat_samochodowy.repository.NaprawaRepository;
import com.example.warsztat_samochodowy.repository.PojazdRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service

public class Warsztat_serwis {

    private KlientRepository klientRepository;
    private MechanikRepository mechanikRepository;
    private NaprawaRepository naprawaRepository;
    private PojazdRepository pojazdRepository;



    public List<Klient>Podglad_klientow(){
        List<Klient> listaKlientow = new ArrayList<>();
        listaKlientow = klientRepository.findAll();
        return listaKlientow;
    }
    public Klient Dodawanie_klienta(Klient klient){
        Optional<Klient> staryKlient = klientRepository.findByTelefon(klient.getTelefon());
        if(staryKlient.isEmpty()){
            klientRepository.save(klient);
            return klient;
        }
        return staryKlient.get();
    }
    public void Modyfikacje_danych_klienta(Klient klient){
        Optional<Klient> staryKlient = klientRepository.findByTelefon(klient.getTelefon());

        if(staryKlient.isPresent()){
            //staryKlient.get().setKlientID(klient.getKlientID());
            staryKlient.get().setImie(klient.getImie());
            staryKlient.get().setNazwisko(klient.getNazwisko());
            staryKlient.get().setTelefon(klient.getTelefon());
            staryKlient.get().setEmail(klient.getEmail());
        }

        klientRepository.save(staryKlient.get());
    }
    public List<Mechanik>Podglad_mechanikow(){
        List<Mechanik> listaMechanikow = new ArrayList<>();
        listaMechanikow = mechanikRepository.findAll();
        return listaMechanikow;
    }
    public Mechanik Dodawanie_mechanika(Mechanik mechanik){
        mechanikRepository.save(mechanik);
        return mechanik;
    }
    public void Usuniecie_danych_mechanika(Mechanik mechanik){
        // sprawdzic czy mechanik istnieje w bazie
        mechanikRepository.delete(mechanik);
    }
    public List<Naprawa>Podglad_napraw(){

        List<Naprawa> listaNapraw = new ArrayList<>();
        listaNapraw = naprawaRepository.findAll();
        return listaNapraw;
    }
    public Naprawa Dodawanie_naprawy(Naprawa naprawa){
       naprawaRepository.save(naprawa);
       return naprawa;
    }
    public List<Pojazd>Podglad_pojazdow(){

        List<Pojazd> listaPojazdow = new ArrayList<>();
        listaPojazdow = pojazdRepository.findAll();
        return listaPojazdow;
    }
    public Pojazd Dodawanie_pojazdu(Pojazd pojazd){
        Optional<Pojazd> staryPojazd = pojazdRepository.findByPojazdID(pojazd.getPojazdID());
        if(staryPojazd.isEmpty()) {
            pojazdRepository.save(pojazd);
            return pojazd;
        }
        return staryPojazd.get();
    }
    public void Modyfikacje_danych_pojazdu(Pojazd pojazd){
        Optional<Pojazd> staryPojazd = pojazdRepository.findByPojazdID(pojazd.getPojazdID());

        if(staryPojazd.isPresent()){
            staryPojazd.get().setPojazdID(pojazd.getPojazdID());
            staryPojazd.get().setMarka(pojazd.getMarka());
            staryPojazd.get().setModel(pojazd.getModel());
            staryPojazd.get().setRejestracja(pojazd.getRejestracja());
            staryPojazd.get().setRocznik(pojazd.getRocznik());
        }

        pojazdRepository.save(staryPojazd.get());
    }

    public Naprawa Dodanie_nowego_zgloszenia(Klient klient, Pojazd pojazd){

        Dodawanie_klienta(klient);
        Dodawanie_pojazdu(pojazd);
        Naprawa nowa_naprawa = new Naprawa(pojazd);
        Dodawanie_naprawy(nowa_naprawa);
        return nowa_naprawa;

    }

}
