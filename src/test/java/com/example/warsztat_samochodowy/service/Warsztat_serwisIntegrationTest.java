package com.example.warsztat_samochodowy.service;

import com.example.warsztat_samochodowy.model.Klient;
import com.example.warsztat_samochodowy.repository.KlientRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
public class Warsztat_serwisIntegrationTest {

    @Autowired
    private KlientRepository klientRepository;
    @Autowired
    private Warsztat_serwis warsztatSerwis;

//    public Warsztat_serwisIntegrationTest(KlientRepository klientRepository, Warsztat_serwis warsztatSerwis) {
//        this.klientRepository = klientRepository;
//        this.warsztatSerwis = warsztatSerwis;
//    }

    @Test
    void happyPath() {
        //etap 1 - dodanie klienta
        //given
        Klient testKlient = new Klient("Julia", "Przybylska", "432124541", "juliaprzybylska.gmail.com");
        Klient testKlient2 = new Klient("Julia", "Przybylska", "432124541", "juliaprzybylska.gmail.com");
        //when
        Klient zapisanyKlient = warsztatSerwis.Dodawanie_klienta(testKlient);
        Klient zapisanyKlient2 = warsztatSerwis.Dodawanie_klienta(testKlient2);
        //then
        Assertions.assertThat(zapisanyKlient).isNotNull();
        Assertions.assertThat(zapisanyKlient.getEmail()).isEqualTo(testKlient.getEmail());
        Assertions.assertThat(zapisanyKlient.getTelefon()).isEqualTo(testKlient.getTelefon());

        //etap 2 - wyswietlanie klientow
        //given
        //when
        List<Klient> klienci = warsztatSerwis.Podglad_klientow();
        //then
        Assertions.assertThat(klienci).hasSize(2);
        Assertions.assertThat(klienci.get(0).getTelefon()).isEqualTo(testKlient.getTelefon());
    }

    @Test
    void wyjatki() {
        //etap 1 - dodanie klienta z istniejącym numerem
        //given
        Klient testKlient = new Klient("Julia", "Przybylska", "432124541", "juliaprzybylska.gmail.com");
        Klient testKlient2 = new Klient("Julia", "Przybylska", "432124541", "juliaprzybylska.gmail.com");
        //when
        Klient zapisanyKlient = warsztatSerwis.Dodawanie_klienta(testKlient);
        Klient zapisanyKlient2 = warsztatSerwis.Dodawanie_klienta(testKlient2);
        //then
        Assertions.assertThat(zapisanyKlient).isNotNull();
    }
}
