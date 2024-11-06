package com.example.warsztat_samochodowy.service;

import com.example.warsztat_samochodowy.model.Klient;
import com.example.warsztat_samochodowy.model.Mechanik;
import com.example.warsztat_samochodowy.model.Naprawa;
import com.example.warsztat_samochodowy.model.Pojazd;
import com.example.warsztat_samochodowy.repository.NaprawaRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
public class Mechanik_serwis {

    private NaprawaRepository naprawaRepository;
    private Warsztat_serwis warsztat_serwis;

    public List<Naprawa> Podglad_napraw(){

        List<Naprawa> listaNapraw = new ArrayList<>();
        listaNapraw = naprawaRepository.findAll();
        return listaNapraw;
    }

    public Naprawa Dodanie_nowego_zgloszenia(Klient klient, Pojazd pojazd, Mechanik mechanik){

        warsztat_serwis.Dodawanie_klienta(klient);
        warsztat_serwis.Dodawanie_pojazdu(pojazd);
        Naprawa nowa_naprawa = new Naprawa(pojazd, mechanik);
        warsztat_serwis.Dodawanie_naprawy(nowa_naprawa);
        return nowa_naprawa;

    }

    public Naprawa Modyfikacja_opisu_usterki(int NaprawaID, String opis_usterki, String stan, String protokol_naprawy){


        Optional<Naprawa> staraNaprawa = naprawaRepository.findByNaprawaID(NaprawaID);

        if(staraNaprawa.isPresent()){
            //staryKlient.get().setKlientID(klient.getKlientID());
            staraNaprawa.get().setOpis_usterki(opis_usterki);
            staraNaprawa.get().setStan(stan);
            staraNaprawa.get().setProtokol_naprawy(protokol_naprawy);
        }

        naprawaRepository.save(staraNaprawa.get());
        return staraNaprawa.get();
    }

    public Naprawa Rozpoczecie_naprawy(int NaprawaID, Date data_rozpoczecia){

        Optional<Naprawa> staraNaprawa = naprawaRepository.findByNaprawaID(NaprawaID);

        if(staraNaprawa.isPresent()){

            staraNaprawa.get().setData_rozpoczecia(data_rozpoczecia);
        }

        naprawaRepository.save(staraNaprawa.get());
        return staraNaprawa.get();

    }

    public Naprawa Przewidywany_czas_naprawy(int NaprawaID, Date data_zakonczenia){

        Optional<Naprawa> staraNaprawa = naprawaRepository.findByNaprawaID(NaprawaID);

        if(staraNaprawa.isPresent()){

            staraNaprawa.get().setData_rozpoczecia(data_zakonczenia);
        }

        naprawaRepository.save(staraNaprawa.get());
        return staraNaprawa.get();
    }
}
