package com.example.warsztat_samochodowy.service;

import com.example.warsztat_samochodowy.exception.KlientAlreadyExistException;
import com.example.warsztat_samochodowy.model.Klient;
import com.example.warsztat_samochodowy.model.Mechanik;
import com.example.warsztat_samochodowy.model.Naprawa;
import com.example.warsztat_samochodowy.model.Pojazd;
import com.example.warsztat_samochodowy.repository.KlientRepository;
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

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class ExceptionIntegrationTest {

    @Autowired
    private Warsztat_serwis warsztatSerwis;
    @Autowired
    public MockMvc mockMvc;
    @Autowired
    public ObjectMapper objectMapper;

    @Test
    void KlientAlreadyExistException() {
        //etap 1 - dodanie klienta z istniejącym numerem
        //given
        Klient testKlient = new Klient("Julia", "Przybylska", "432124548", "juliaprzybylska.gmail.com");
        Klient testKlient2 = new Klient("Julia", "Przybylska", "432124548", "juliaprzybylska.gmail.com");
        //when
        warsztatSerwis.Dodawanie_klienta(testKlient);
        //Klient zapisanyKlient2 = warsztat_serwis.Dodawanie_klienta(testKlient2);
        //then
        assertThatThrownBy(() -> warsztatSerwis.Dodawanie_klienta(testKlient2))
                .isInstanceOf(KlientAlreadyExistException.class)
                .hasMessage("Klient z podanym numerem telefonu juz istnieje w bazie");
    }

    @Test
    void KlientNotFoundException() throws Exception {
        //given
        Klient testKlient = new Klient("Julia", "Przybylska", "432124549", "juliaprzybylska.gmail.com");
        //when
        warsztatSerwis.Dodawanie_klienta(testKlient);

        ResultActions perform1a = mockMvc.perform(patch("/modyfikuj/dane/klienta").content("""
                {
                    "imie": "Julia",
                    "nazwisko": "Przybylska",
                    "telefon": "432124542",
                    "email": "juliaprzybylska777.gmail.com"
                
                }
                """.trim()).contentType(MediaType.APPLICATION_JSON_VALUE));
        //when
        //then
        MvcResult mvcResult1a = perform1a.andExpect(status().isNotFound()).andReturn();
        String contentAsString1a = mvcResult1a.getResponse().getContentAsString();
        Klient klient = objectMapper.readValue(contentAsString1a, Klient.class);
        //Assertions.assertThat(klient).isNull();
        Assertions.assertThat(contentAsString1a)
                .contains("message\":\"Klient nie istnieje w bazie")
                .contains("\"status\":\"NOT_FOUND\"");

    }

    @Test
    void MechanikAlreadyExistException() throws Exception {

        Mechanik testMechanik = new Mechanik ("Zdzislaw","Przybylski","Przybyl","123");
        warsztatSerwis.Dodawanie_mechanika(testMechanik);

        ResultActions perform4 = mockMvc.perform(post("/dodaj/mechanika").content("""
                {
                              "imie": "Kornel",
                              "nazwisko": "Janczyk",
                              "login": "Przybyl",
                              "haslo": "Kornelcio321"
                          }
                """.trim()).contentType(MediaType.APPLICATION_JSON_VALUE));

        //when
        //then
        MvcResult mvcResult4 = perform4.andExpect(status().isConflict()).andReturn();
        String contentAsString4 = mvcResult4.getResponse().getContentAsString();
        //Mechanik zapisanyMechanik = objectMapper.readValue(contentAsString4, Mechanik.class);
        //String normalizedActual = Normalizer.normalize(zapisanyMechanik.getLogin(), Normalizer.Form.NFC);
        Assertions.assertThat(contentAsString4)
                .contains("message\":\"Mechanik juz istnieje w bazie")
                .contains("\"status\":\"CONFLICT\"");
    }

    @Test
    void MechanikNotFoundException() throws Exception {

        Mechanik testMechanik = new Mechanik ("Antoni","Kowalski","Antek","321453");
        warsztatSerwis.Dodawanie_mechanika(testMechanik);

        // given
        ResultActions perform5 = mockMvc.perform(patch("/zwolnij/mechanika").content("""
                {
                      "login": "Antoni"
                  }
                """.trim()).contentType(MediaType.APPLICATION_JSON_VALUE));
        //when
        //then
        MvcResult mvcResult5 = perform5.andExpect(status().isNotFound()).andReturn();
        String contentAsString5 = mvcResult5.getResponse().getContentAsString();
        Mechanik zwolnionyMechanik = objectMapper.readValue(contentAsString5, Mechanik.class);
        //Assertions.assertThat(zwolnionyMechanik.getCzyZatrudniony()).isEqualTo("NIE");
        Assertions.assertThat(contentAsString5)
                .contains("message\":\"Mechanik nie istnieje w bazie")
                .contains("\"status\":\"NOT_FOUND\"");
    }

    @Test
    void NaprawaNotFoundException() throws Exception {
        //given
        Naprawa naprawa = new Naprawa();
        warsztatSerwis.Dodawanie_naprawy(naprawa);
        ResultActions perform7 = mockMvc.perform(patch("/przyjecie/naprawy").content("""
               {
                   "naprawaID": 1,
                   "mechanikLogin": "Kazimierz"
               }
               """.trim()).contentType(MediaType.APPLICATION_JSON_VALUE));
        //when
        //then
        MvcResult mvcResult7 = perform7.andExpect(status().isNotFound()).andReturn();
        String contentAsString7 = mvcResult7.getResponse().getContentAsString();
        Naprawa przyjetaNaprawa = objectMapper.readValue(contentAsString7, Naprawa.class);
        Assertions.assertThat(contentAsString7)
                .contains("message\":\"Nie znalezniono mechanika z podanym loginem")
                .contains("\"status\":\"NOT_FOUND\"");
    }

    @Test
    void PojazdAlreadyExistException() throws Exception {
        //given
        Klient testKlient = new Klient("Julia", "Przybylska", "432124541", "juliaprzybylska.gmail.com");
        warsztatSerwis.Dodawanie_klienta(testKlient);
        Pojazd testPojazd = new Pojazd("DW 19831","Audi","S7",2011,"ABSDAE221293921283");
        String telefonKlienta = "432124541";
        warsztatSerwis.Dodawanie_pojazdu(testPojazd, telefonKlienta);

        ResultActions perform2 = mockMvc.perform(post("/dodaj/pojazdy").content("""
                {
                             "pojazd": {
                             "rejestracja": "DW 19831",
                                     "marka": "Audi",
                                     "model": "S7",
                                     "rocznik": 2011,
                                     "vin": "ABSDAE221293921283"
                         },
                             "telefonKlienta": "432124541"
                         }
                """.trim()).contentType(MediaType.APPLICATION_JSON_VALUE));
        //when
        //then
        MvcResult mvcResult2 = perform2.andExpect(status().isConflict()).andReturn();
        String contentAsString2 = mvcResult2.getResponse().getContentAsString();
        Pojazd zapisanyPojazd = objectMapper.readValue(contentAsString2, Pojazd.class);
        Assertions.assertThat(contentAsString2)
                .contains("message\":\"Pojazd z podanym numerem VIN istnieje juz w bazie")
                .contains("\"status\":\"CONFLICT\"");
    }

    @Test
    void PojazdNotFoundException() throws Exception {

        Klient testKlient = new Klient("Julia", "Przybylska", "432124543", "juliaprzybylska.gmail.com");
        warsztatSerwis.Dodawanie_klienta(testKlient);
        Pojazd testPojazd = new Pojazd("DW 19831","Audi","S7",2011,"ABSDAE222293821283");
        String telefonKlienta = "432124543";
        warsztatSerwis.Dodawanie_pojazdu(testPojazd, telefonKlienta);

        // given
        ResultActions perform3 = mockMvc.perform(patch("/modyfikuj/dane/pojazdow").content("""
                {
                      "rejestracja": "DW 20131",
                      "marka": "Audi",
                      "model": "S7",
                      "rocznik": 2011,
                      "vin": "ABSDAE222293821263"
                 }
                """.trim()).contentType(MediaType.APPLICATION_JSON_VALUE));
        //when
        //then
        MvcResult mvcResult3 = perform3.andExpect(status().isNotFound()).andReturn();
        String contentAsString3 = mvcResult3.getResponse().getContentAsString();
        Pojazd zmodyfikowanyPojazd = objectMapper.readValue(contentAsString3, Pojazd.class);
        Assertions.assertThat(contentAsString3)
                .contains("message\":\"Pojazd nie istnieje w bazie")
                .contains("\"status\":\"NOT_FOUND\"");
    }

    @Test
    void DataToEarlyExistException() throws Exception {

        Naprawa naprawa = new Naprawa();
        Date date = new Date(2024,10,19);
        naprawa.setData_rozpoczecia(date);
        warsztatSerwis.Dodawanie_naprawy(naprawa);

        ResultActions perform10 = mockMvc.perform(patch("/modyfikuj/zakonczenie_naprawy").content("""
                {
                     "naprawaID": 3,
                     "data_zakonczenia": "2024-10-11"
                }
               """.trim()).contentType(MediaType.APPLICATION_JSON_VALUE));
        //when
        //then
        MvcResult mvcResult10 = perform10.andExpect(status().isNotAcceptable()).andReturn();
        String contentAsString10 = mvcResult10.getResponse().getContentAsString();
        Naprawa modyfikacjaZakonczeniaNaprawy = objectMapper.readValue(contentAsString10, Naprawa.class);
        Assertions.assertThat(contentAsString10)
                .contains("message\":\"Data zakonczenia nie moze byc wczesniejsza niz data rozpoczecia")
                .contains("\"status\":\"NOT_ACCEPTABLE\"");
    }

}


