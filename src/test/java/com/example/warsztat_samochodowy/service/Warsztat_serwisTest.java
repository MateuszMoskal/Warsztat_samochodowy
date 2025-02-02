package com.example.warsztat_samochodowy.service;

import com.example.warsztat_samochodowy.model.Klient;
import com.example.warsztat_samochodowy.repository.KlientRepository;
import com.example.warsztat_samochodowy.repository.MechanikRepository;
import com.example.warsztat_samochodowy.repository.NaprawaRepository;
import com.example.warsztat_samochodowy.repository.PojazdRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class Warsztat_serwisTest {


    @Mock
    private KlientRepository klientRepository;
    //private MechanikRepository mechanikRepository;
    //private NaprawaRepository naprawaRepository;
    //private PojazdRepository pojazdRepository;
    @InjectMocks
    private Warsztat_serwis warsztat_serwis;



    @Test
    void podglad_klientow() {
    }

    @Test
    void dodawanie_klienta() {
        //given
        MockitoAnnotations.openMocks(this);
        Klient testKlient = new Klient("Julia", "Przybylska", "432124541", "juliaprzybylska.gmail.com");
        Mockito.when(klientRepository.save(testKlient)).thenReturn(testKlient);
        //when
        Klient zapisanyKlient = warsztat_serwis.Dodawanie_klienta(testKlient);
        //then
        Assertions.assertEquals(testKlient, zapisanyKlient);
        Mockito.verify(klientRepository,Mockito.times(1)).save(testKlient);
    }

    @Test
    void dodawanie_klienta_z_istniejacym_numerem_telefonu() {
        //given
        MockitoAnnotations.openMocks(this);
        Klient testKlient = new Klient("Julia", "Przybylska", "432124541", "juliaprzybylska.gmail.com");
        Klient testKlient2 = new Klient("Jarek", "Kowarian", "432124541", "jarekkowarian.gmail.com");
        Mockito.when(klientRepository.save(testKlient)).thenReturn(testKlient);
        //when
        Klient zapisanyKlient = warsztat_serwis.Dodawanie_klienta(testKlient);
        //then
        Assertions.assertEquals(testKlient, zapisanyKlient);
        Mockito.verify(klientRepository,Mockito.times(1)).save(testKlient);
    }

    @Test
    void modyfikacje_danych_klienta() {
    }

    @Test
    void podglad_mechanikow() {
    }

    @Test
    void dodawanie_mechanika() {
    }

    @Test
    void zwolnienie_mechanika() {
    }

    @Test
    void podglad_napraw() {
    }

    @Test
    void dodawanie_naprawy() {
    }

    @Test
    void podglad_pojazdow() {
    }

    @Test
    void dodawanie_pojazdu() {
    }

    @Test
    void modyfikacje_danych_pojazdu() {
    }

    @Test
    void dodanie_nowego_zgloszenia() {
    }
}