package com.example.warsztat_samochodowy.service;

import com.example.warsztat_samochodowy.model.Klient;
import com.example.warsztat_samochodowy.model.Mechanik;
import com.example.warsztat_samochodowy.model.Pojazd;
import com.example.warsztat_samochodowy.repository.KlientRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
public class Warsztat_serwisIntegrationTest {

    @Autowired
    private KlientRepository klientRepository;
    @Autowired
    private Warsztat_serwis warsztatSerwis;
    @Autowired
    public MockMvc mockMvc;
    @Autowired
    public ObjectMapper objectMapper;

//    public Warsztat_serwisIntegrationTest(KlientRepository klientRepository, Warsztat_serwis warsztatSerwis) {
//        this.klientRepository = klientRepository;
//        this.warsztatSerwis = warsztatSerwis;
//    }

    @Test
    void happyPath() throws Exception {
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
        ResultActions lista = mockMvc.perform(get("/klienci").content("""
                {
                    "imie": "Bozena",
                    "nazwisko": "Kowalska",
                    "telefon": "753289111",
                    "email": "bozenakowalska777@gmail.com"
                
                }
                """.trim()).contentType(MediaType.APPLICATION_JSON_VALUE));
        //when
        //then = do dokończenia
        MvcResult mvcResult = lista.andExpect(status().isCreated()).andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        List<Klient> klient = objectMapper.readValue(contentAsString, Klient.class);
        Assertions.assertThat(klient.getEmail()).isEqualTo("bozenakowalska777@gmail.com");
        //then
        Assertions.assertThat(klienci).hasSize(2);
        Assertions.assertThat(klienci.get(0).getTelefon()).isEqualTo(testKlient.getTelefon());

        //etap 3 - modyfikacja danych klienta
        //given
        ResultActions perform = mockMvc.perform(patch("/modyfikuj/dane/klienta").content("""
                {
                    "imie": "Bozena",
                    "nazwisko": "Kowalska",
                    "telefon": "753289111",
                    "email": "bozenakowalska777@gmail.com"
                
                }
                """.trim()).contentType(MediaType.APPLICATION_JSON_VALUE));
        //when
        //then
        MvcResult mvcResult = perform.andExpect(status().isCreated()).andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        Klient klient = objectMapper.readValue(contentAsString, Klient.class);
        Assertions.assertThat(klient.getEmail()).isEqualTo("bozenakowalska777@gmail.com");

        // etap 4 - wyswietlanie klientow
        //given
        //when
        klienci = warsztatSerwis.Podglad_klientow();
        //then
        Assertions.assertThat(klienci).hasSize(2);
        Assertions.assertThat(klienci.get(0).getEmail()).isEqualTo("bozenakowalska777@gmail.com");

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
        MvcResult mvcResult2 = perform2.andExpect(status().isCreated()).andReturn();
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
        MvcResult mvcResult3 = perform3.andExpect(status().isCreated()).andReturn();
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
        Mechanik testMechanik = new Mechanik ("Zdzisław","Przybylski","Zdzisław","123");

        ResultActions perform4 = mockMvc.perform(post("/dodaj/mechanika").content("""
                {
                              "imie": "Zdzisław",
                              "nazwisko": "Przybylski",
                              "login": "Zdzisław",
                              "haslo": "123"
                          }
                """.trim()).contentType(MediaType.APPLICATION_JSON_VALUE));

        //when
        //then
        MvcResult mvcResult4 = perform4.andExpect(status().isCreated()).andReturn();
        String contentAsString4 = mvcResult4.getResponse().getContentAsString();
        Mechanik zapisanyMechanik = objectMapper.readValue(contentAsString4, Mechanik.class);
        Assertions.assertThat(zapisanyMechanik.getLogin()).isEqualTo("Zdzisław");
        // szyfrowanie hasła nastęonym razem

        // etap 9 - zwolnienie mechanika
        // given
        ResultActions perform5 = mockMvc.perform(patch("/zwolnij/mechanika").content("""
                {
                      "login": "Zdzisław"
                  }
                """.trim()).contentType(MediaType.APPLICATION_JSON_VALUE));
        //when
        //then
        MvcResult mvcResult5 = perform5.andExpect(status().isCreated()).andReturn();
        String contentAsString5 = mvcResult5.getResponse().getContentAsString();
        Mechanik zwolnionyMechanik = objectMapper.readValue(contentAsString5, Mechanik.class);
        Assertions.assertThat(zwolnionyMechanik.getCzyZatrudniony()).isEqualTo("NIE");

        // etap 10 - wyswietlanie mechanika
        //given
        ResultActions perform4a = mockMvc.perform(post("/dodaj/mechanika").content("""
                {
                              "imie": "Stanisław",
                              "nazwisko": "Wyspianski",
                              "login": "Stanisław",
                              "haslo": "456"
                          }
                """.trim()).contentType(MediaType.APPLICATION_JSON_VALUE));

        List<Mechanik> mechanicy = warsztatSerwis.Podglad_mechanikow();
        //then
        Assertions.assertThat(mechanicy).hasSize(2);

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
        MvcResult mvcResult2 = perform2.andExpect(status().isCreated()).andReturn();
        String contentAsString2 = mvcResult2.getResponse().getContentAsString();
        Pojazd zapisanyPojazd = objectMapper.readValue(contentAsString2, Pojazd.class);
        Assertions.assertThat(zapisanyPojazd.getVIN()).isEqualTo("ABSDAE221293821283");

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
