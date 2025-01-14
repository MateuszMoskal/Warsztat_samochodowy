package com.example.warsztat_samochodowy.service;

import com.example.warsztat_samochodowy.dto.NaprawaDto;
import com.example.warsztat_samochodowy.exception.DataToEarlyException;
import com.example.warsztat_samochodowy.exception.MechanikNotFoundException;
import com.example.warsztat_samochodowy.exception.NaprawaNotFoundException;
import com.example.warsztat_samochodowy.model.Mechanik;
import com.example.warsztat_samochodowy.model.Naprawa;
import com.example.warsztat_samochodowy.repository.MechanikRepository;
import com.example.warsztat_samochodowy.repository.NaprawaRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class Mechanik_serwis {

    private NaprawaRepository naprawaRepository;
    private MechanikRepository mechanikRepository;

    public Mechanik_serwis(NaprawaRepository naprawaRepository, MechanikRepository mechanikRepository) {
        this.naprawaRepository = naprawaRepository;
        this.mechanikRepository = mechanikRepository;
    }

    public List<Naprawa> Podglad_napraw(){

        List<Naprawa> listaNapraw = new ArrayList<>();
        listaNapraw = naprawaRepository.findAll();
        return listaNapraw;
    }

    public Naprawa Przyjecie_naprawy(Naprawa naprawa, Mechanik mechanik){

        naprawa.setMechanik(mechanik);
        return naprawaRepository.save(naprawa);

    }

    public Naprawa Modyfikacja_opisu_usterki(int NaprawaID, String opis_usterki, String stan, String protokol_naprawy){


        Optional<Naprawa> staraNaprawa = naprawaRepository.findByNaprawaID(NaprawaID);

        if(staraNaprawa.isPresent()){
            //staryKlient.get().setKlientID(klient.getKlientID());
            staraNaprawa.get().setOpis_usterki(opis_usterki);
            staraNaprawa.get().setStan(stan);
            staraNaprawa.get().setProtokol_naprawy(protokol_naprawy);
        }
        else {
            throw new NaprawaNotFoundException("Nie znaleziono naprawy z takim ID");
        }
        naprawaRepository.save(staraNaprawa.get());
        return staraNaprawa.get();
    }

    public Naprawa Rozpoczecie_naprawy(Naprawa naprawa){

        Optional<Naprawa> staraNaprawa = naprawaRepository.findByNaprawaID(naprawa.getNaprawaID());

        if(staraNaprawa.isPresent()){

            staraNaprawa.get().setData_rozpoczecia(naprawa.getData_rozpoczecia());
        }

        naprawaRepository.save(staraNaprawa.get());
        return staraNaprawa.get();

    }

    public Naprawa Dodanie_mechanika_do_naprawy(NaprawaDto naprawaDto) {
        //Optional<Pojazd> pojazd = pojazdRepository.findByVin(naprawaDto.getPojazd().getVIN());
        Optional<Mechanik> mechanik = mechanikRepository.findByLogin(naprawaDto.getMechanikLogin());
        if (mechanik.isEmpty()) {
            throw new MechanikNotFoundException("Nie znalezniono mechanika z podanym loginem");
        }
        Optional<Naprawa> naprawa = naprawaRepository.findById(naprawaDto.getNaprawaID());
        if (naprawa.isEmpty()) {
            throw new NaprawaNotFoundException("Nie znaleziono podanej naprawy. Nie udało się dodać mechanika");
        }
        naprawa.get().setMechanik(mechanik.get());
        return naprawaRepository.save(naprawa.get());
    }

    public Naprawa Przewidywany_czas_naprawy(Naprawa naprawa){

        Optional<Naprawa> staraNaprawa = naprawaRepository.findByNaprawaID(naprawa.getNaprawaID());

        if (staraNaprawa.isEmpty()) {
            throw new NaprawaNotFoundException("Nie znalezniono podanej naprawy w bazie");
        }
        staraNaprawa.get().setData_zakonczenia(naprawa.getData_zakonczenia());
        if (naprawa.getData_zakonczenia().before(staraNaprawa.get().getData_rozpoczecia())) {
            throw new DataToEarlyException("Data zakończenia nie może być wcześniejsza niż data rozpoczęcia");
        }
        naprawaRepository.save(staraNaprawa.get());
        return staraNaprawa.get();
    }
}
