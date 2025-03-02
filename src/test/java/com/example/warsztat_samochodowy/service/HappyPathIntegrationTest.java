package com.example.warsztat_samochodowy.service;

import com.example.warsztat_samochodowy.model.Klient;
import com.example.warsztat_samochodowy.model.Mechanik;
import com.example.warsztat_samochodowy.model.Naprawa;
import com.example.warsztat_samochodowy.model.Pojazd;
import com.example.warsztat_samochodowy.repository.KlientRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.text.Normalizer;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class HappyPathIntegrationTest {

    @Autowired
    private KlientRepository klientRepository;
    @Autowired
    private Warsztat_serwis warsztatSerwis;
    @Autowired
    public MockMvc mockMvc;
    @Autowired
    public ObjectMapper objectMapper;
    @Autowired
    private Mechanik_serwis mechanik_serwis;

//    public Warsztat_serwisIntegrationTest(KlientRepository klientRepository, Warsztat_serwis warsztatSerwis) {
//        this.klientRepository = klientRepository;
//        this.warsztatSerwis = warsztatSerwis;
//    }

    @Test
    void happyPath() throws Exception {
        //etap 1 - dodanie klienta
        //given
        Klient testKlient = new Klient("Bozena", "Kowalska", "753289111", "bozenakowalska@gmail.com");
        //when
        ResultActions perform = mockMvc.perform(post("/dodaj/klienta").content("""
                {
                              "imie": "Bozena",
                              "nazwisko": "Kowalska",
                              "telefon": "753289111",
                              "email": "bozenakowalska@gmail.com"
                          }
                """.trim()).contentType(MediaType.APPLICATION_JSON_VALUE));
        //then
        MvcResult mvcResult = perform.andExpect(status().isOk()).andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        Klient zapisanyKlient = objectMapper.readValue(contentAsString, Klient.class);
        Assertions.assertThat(zapisanyKlient).isNotNull();
        Assertions.assertThat(zapisanyKlient.getEmail()).isEqualTo(testKlient.getEmail());
        Assertions.assertThat(zapisanyKlient.getTelefon()).isEqualTo(testKlient.getTelefon());


        //etap 2 - wyswietlanie klientow
        ResultActions lista = mockMvc.perform(get("/klienci").contentType(MediaType.APPLICATION_JSON_VALUE));
        //when
        //then = do dokończenia
        MvcResult mvcResult1b = lista.andExpect(status().isOk()).andReturn();
        String contentAsString1b = mvcResult1b.getResponse().getContentAsString();
        List<Klient> klienci = objectMapper.readValue(contentAsString1b, new TypeReference<List<Klient>>() {
        });
        Assertions.assertThat(klienci.get(0).getEmail()).isEqualTo("bozenakowalska@gmail.com");
        //then
        Assertions.assertThat(klienci).hasSize(1);
        Assertions.assertThat(klienci.get(0).getTelefon()).isEqualTo(testKlient.getTelefon());

        //etap 3 - modyfikacja danych klienta
        //given
        ResultActions perform1a = mockMvc.perform(patch("/modyfikuj/dane/klienta").content("""
                {
                    "imie": "Bozena",
                    "nazwisko": "Kowalska",
                    "telefon": "753289111",
                    "email": "bozenakowalska777@gmail.com"
                
                }
                """.trim()).contentType(MediaType.APPLICATION_JSON_VALUE));
        //when
        //then
        MvcResult mvcResult1a = perform1a.andExpect(status().isOk()).andReturn();
        String contentAsString1a = mvcResult1a.getResponse().getContentAsString();
        Klient klient = objectMapper.readValue(contentAsString1a, Klient.class);
        Assertions.assertThat(klient.getEmail()).isEqualTo("bozenakowalska777@gmail.com");

        // etap 4 - wyswietlanie klientow
        //given
        //when
        ResultActions lista1 = mockMvc.perform(get("/klienci").contentType(MediaType.APPLICATION_JSON_VALUE));
        //then = do dokończenia
        MvcResult mvcResult1c = lista1.andExpect(status().isOk()).andReturn();
        String contentAsString1c = mvcResult1c.getResponse().getContentAsString();
        List<Klient> klienci1c = objectMapper.readValue(contentAsString1c, new TypeReference<List<Klient>>() {
        });
        //then
        Assertions.assertThat(klienci1c).hasSize(1);
        Assertions.assertThat(klienci1c.get(0).getEmail()).isEqualTo("bozenakowalska777@gmail.com");

        // etap 5 - dodawanie pojazdow
        //given
        Pojazd testPojazd = new Pojazd("DW 19831","Audi","S7",2011,"ABSDAE221293821283");
        String telefonKlienta = "753289111";

        ResultActions perform2 = mockMvc.perform(post("/dodaj/pojazdy").content("""
                {
                             "pojazd": {
                             "rejestracja": "DW 19831",
                                     "marka": "Audi",
                                     "model": "S7",
                                     "rocznik": 2011,
                                     "vin": "ABSDAE221293821283"
                         },
                             "telefonKlienta": "753289111"
                         }
                """.trim()).contentType(MediaType.APPLICATION_JSON_VALUE));
        //when
        //then
        MvcResult mvcResult2 = perform2.andExpect(status().isOk()).andReturn();
        String contentAsString2 = mvcResult2.getResponse().getContentAsString();
        Pojazd zapisanyPojazd = objectMapper.readValue(contentAsString2, Pojazd.class);
        Assertions.assertThat(zapisanyPojazd.getVIN()).isEqualTo("ABSDAE221293821283");

        // etap 6 - modyfikuj dane pojazdów
        // given
        ResultActions perform3 = mockMvc.perform(patch("/modyfikuj/dane/pojazdow").content("""
                {
                      "rejestracja": "DW 20131",
                      "marka": "Audi",
                      "model": "S7",
                      "rocznik": 2011,
                      "vin": "ABSDAE221293821283"
                 }
                """.trim()).contentType(MediaType.APPLICATION_JSON_VALUE));
        //when
        //then
        MvcResult mvcResult3 = perform3.andExpect(status().isOk()).andReturn();
        String contentAsString3 = mvcResult3.getResponse().getContentAsString();
        Pojazd zmodyfikowanyPojazd = objectMapper.readValue(contentAsString3, Pojazd.class);
        Assertions.assertThat(zmodyfikowanyPojazd.getRejestracja()).isEqualTo("DW 20131");

        // etap 7 - wyswietlanie pojazdów
        //given
        List<Pojazd> pojazdy = warsztatSerwis.Podglad_pojazdow();
        //then
        Assertions.assertThat(pojazdy).hasSize(1);
        Assertions.assertThat(pojazdy.get(0).getRejestracja()).isEqualTo("DW 20131");

        // etap 8 - dodawanie mechanika
        //given
        Mechanik testMechanik = new Mechanik ("Zdzislaw","Przybylski","Zdzisław","123");

        ResultActions perform4 = mockMvc.perform(post("/dodaj/mechanika").content("""
                {
                              "imie": "Zdzislaw",
                              "nazwisko": "Przybylski",
                              "login": "Zdzislaw",
                              "haslo": "123"
                          }
                """.trim()).contentType(MediaType.APPLICATION_JSON_VALUE));

        //when
        //then
        MvcResult mvcResult4 = perform4.andExpect(status().isOk()).andReturn();
        String contentAsString4 = mvcResult4.getResponse().getContentAsString();
        Mechanik zapisanyMechanik = objectMapper.readValue(contentAsString4, Mechanik.class);
        String normalizedActual = Normalizer.normalize(zapisanyMechanik.getLogin(), Normalizer.Form.NFC);
        Assertions.assertThat(normalizedActual).isEqualTo("Zdzislaw");
        // szyfrowanie hasła nastęonym razem

        // etap 9 - zwolnienie mechanika
        // given
        ResultActions perform5 = mockMvc.perform(patch("/zwolnij/mechanika").content("""
                {
                      "login": "Zdzislaw"
                  }
                """.trim()).contentType(MediaType.APPLICATION_JSON_VALUE));
        //when
        //then
        MvcResult mvcResult5 = perform5.andExpect(status().isOk()).andReturn();
        String contentAsString5 = mvcResult5.getResponse().getContentAsString();
        Mechanik zwolnionyMechanik = objectMapper.readValue(contentAsString5, Mechanik.class);
        //Assertions.assertThat(zwolnionyMechanik.getCzyZatrudniony()).isEqualTo("NIE");

        // etap 10 - wyswietlanie mechanika
        //given
        ResultActions perform4a = mockMvc.perform(post("/dodaj/mechanika").content("""
                {
                              "imie": "Stanislaw",
                              "nazwisko": "Wyspianski",
                              "login": "Stanislaw",
                              "haslo": "456"
                          }
                """.trim()).contentType(MediaType.APPLICATION_JSON_VALUE));


        //then
        ResultActions lista2 = mockMvc.perform(get("/mechanicy").contentType(MediaType.APPLICATION_JSON_VALUE));
        //when
        //then = do dokończenia
        MvcResult mvcResult7a = lista2.andExpect(status().isOk()).andReturn();
        String contentAsString7a = mvcResult7a.getResponse().getContentAsString();
        List<Mechanik> mechanicy = objectMapper.readValue(contentAsString7a, new TypeReference<List<Mechanik>>() {
        });
        Assertions.assertThat(mechanicy).hasSize(2);
        Assertions.assertThat(mechanicy.get(0).getCzyZatrudniony()).isEqualTo("NIE");

        // etap 11 - dodawanie nowego zgloszenia
        //given

        ResultActions perform6 = mockMvc.perform(post("/dodaj/nowe/zgloszenie").content("""
                {
                               "klient": {
                                   "imie": "Bozena",
                                   "nazwisko": "Kowalska",
                                   "telefon": "753289111",
                                   "email": "bozenakowalska@gmail.com"
                               },
                               "pojazd": {
                                   "rejestracja": "DW 19831",
                                   "marka": "Audi",
                                   "model": "S7",
                                   "rocznik": 2011,
                                   "vin": "ABSDAE221293821283"
                               }
                }
                """.trim()).contentType(MediaType.APPLICATION_JSON_VALUE));
        //when
        //then
        MvcResult mvcResult6 = perform6.andExpect(status().isOk()).andReturn();
        String contentAsString6 = mvcResult6.getResponse().getContentAsString();
        Naprawa zapisanyNaprawa = objectMapper.readValue(contentAsString6, Naprawa.class);
        Assertions.assertThat(zapisanyNaprawa.getPojazd().getVIN()).isEqualTo("ABSDAE221293821283");

        // etap 12 - przyjecie naprawy
        //given
        ResultActions perform7 = mockMvc.perform(patch("/przyjecie/naprawy").content("""
               {
                   "naprawaID": 1,
                   "mechanikLogin": "Stanislaw"
               }
               """.trim()).contentType(MediaType.APPLICATION_JSON_VALUE));
        //when
        //then
        MvcResult mvcResult7 = perform7.andExpect(status().isOk()).andReturn();
        String contentAsString7 = mvcResult7.getResponse().getContentAsString();
        Naprawa przyjetaNaprawa = objectMapper.readValue(contentAsString7, Naprawa.class);
        Assertions.assertThat(przyjetaNaprawa.getMechanik().getLogin()).isEqualTo("Stanislaw");

        // etap 13 - modyfikuj opis usterki
        //given
        ResultActions perform8 = mockMvc.perform(patch("/modyfikuj/opis_usterki").content("""
                {
                    "naprawaID": 1,
                    "opis_usterki": "Problemy z silnikiem",
                    "stan": "Rozpoczety",
                    "protokol_naprawy": "Wymiana czesci silnika"
                }
               """.trim()).contentType(MediaType.APPLICATION_JSON_VALUE));
        //when
        //then
        MvcResult mvcResult8 = perform8.andExpect(status().isOk()).andReturn();
        String contentAsString8 = mvcResult8.getResponse().getContentAsString();
        Naprawa modyfikacjaUsterki = objectMapper.readValue(contentAsString8, Naprawa.class);
        Assertions.assertThat(modyfikacjaUsterki.getOpis_usterki()).isEqualTo("Problemy z silnikiem");
        Assertions.assertThat(modyfikacjaUsterki.getStan()).isEqualTo("Rozpoczety");
        Assertions.assertThat(modyfikacjaUsterki.getProtokol_naprawy()).isEqualTo("Wymiana czesci silnika");

        // etap 14 - modyfikuj rozpoczecie naprawy
        //given
        ResultActions perform9 = mockMvc.perform(patch("/modyfikuj/rozpoczecie_naprawy").content("""
                {
                     "naprawaID": 1,
                     "data_rozpoczecia": "2024-10-02"
                }
               """.trim()).contentType(MediaType.APPLICATION_JSON_VALUE));
        //when
        //then
        MvcResult mvcResult9 = perform9.andExpect(status().isOk()).andReturn();
        String contentAsString9 = mvcResult9.getResponse().getContentAsString();
        Naprawa modyfikacjaRozpoczeciaNaprawy = objectMapper.readValue(contentAsString9, Naprawa.class);
        Assertions.assertThat(modyfikacjaRozpoczeciaNaprawy.getData_rozpoczecia()).isEqualTo("2024-10-02T02:00:00.000");

        // etap 15 - modyfikuj zakoczenie naprawy
        //given
        ResultActions perform10 = mockMvc.perform(patch("/modyfikuj/zakonczenie_naprawy").content("""
                {
                     "naprawaID": 1,
                     "data_zakonczenia": "2024-10-11"
                }
               """.trim()).contentType(MediaType.APPLICATION_JSON_VALUE));
        //when
        //then
        MvcResult mvcResult10 = perform10.andExpect(status().isOk()).andReturn();
        String contentAsString10 = mvcResult10.getResponse().getContentAsString();
        Naprawa modyfikacjaZakonczeniaNaprawy = objectMapper.readValue(contentAsString10, Naprawa.class);
        Assertions.assertThat(modyfikacjaZakonczeniaNaprawy.getData_zakonczenia()).isEqualTo("2024-10-11T02:00:00.000");
    }

}
