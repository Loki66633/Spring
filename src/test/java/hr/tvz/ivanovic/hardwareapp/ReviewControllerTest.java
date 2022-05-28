package hr.tvz.ivanovic.hardwareapp;

import com.fasterxml.jackson.databind.ObjectMapper;
import jdk.jfr.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ReviewControllerTest {

    static String userToken = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyIiwiZXhwIjoxNjU0MzUyMTgyLCJpYXQiOjE2NTM3NDczODIsImF1dGhvcml0aWVzIjoiUk9MRV9VU0VSIn0.I_PKTOxK51jo57xhehPm2qa7dV39wirXQg8Mz27audJ_lQpxSJcY9saRB__-uNHR6MNk-YFf0PAdjuqwHX6HMw";
    static String adminToken ="eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTY1NDM1MjIxNSwiaWF0IjoxNjUzNzQ3NDE1LCJhdXRob3JpdGllcyI6IlJPTEVfQURNSU4ifQ.d_SKTL7hOAXsHv0pEXB0nwOFUdsCeTwqK3UVWV-H_9iat4qveDhkcCW3YsIx9D1kGbdtOVcPo2rzBEMBC2lWhA";


    @Autowired
    private MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();
    @Test
    void getAllReviews() throws Exception {
        this.mockMvc.perform(
                        get("/review")
                                .with(csrf())
                                .header(HttpHeaders.AUTHORIZATION,"Bearer "+ userToken))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().encoding(StandardCharsets.UTF_8))
                .andExpect(content().json("[{\"title\":\"Dosta dobro!\",\"text\":\"Veoma korisna komponenta\",\"rating\":5},{\"title\":\"Katastrofa\",\"text\":\"Nemojte nikada ovo kupiti\",\"rating\":1},{\"title\":\"OK\",\"text\":\"Okej\",\"rating\":3},{\"title\":\"Jako zadovoljan\",\"text\":\"Najbolja komponenta na tržištu\",\"rating\":5},{\"title\":\"Uredu\",\"text\":\"Dosta dobro ali može bolje\",\"rating\":4},{\"title\":\"Razočaran\",\"text\":\"Nikada više ne kupujem ovo smeće\",\"rating\":1}]"));


        this.mockMvc.perform(
                        get("/review")
                                .with(csrf())
                                .header(HttpHeaders.AUTHORIZATION,"Bearer "+ adminToken))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().encoding(StandardCharsets.UTF_8))
                .andExpect(content().json("[{\"title\":\"Dosta dobro!\",\"text\":\"Veoma korisna komponenta\",\"rating\":5},{\"title\":\"Katastrofa\",\"text\":\"Nemojte nikada ovo kupiti\",\"rating\":1},{\"title\":\"OK\",\"text\":\"Okej\",\"rating\":3},{\"title\":\"Jako zadovoljan\",\"text\":\"Najbolja komponenta na tržištu\",\"rating\":5},{\"title\":\"Uredu\",\"text\":\"Dosta dobro ali može bolje\",\"rating\":4},{\"title\":\"Razočaran\",\"text\":\"Nikada više ne kupujem ovo smeće\",\"rating\":1}]"));

    }

    @Test
    void getAllReviewsByHardwareCode() throws Exception {
        this.mockMvc.perform(
                        get("/review/56974")
                                .with(csrf())
                                .header(HttpHeaders.AUTHORIZATION,"Bearer "+ userToken))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().encoding(StandardCharsets.UTF_8))
                .andExpect(content().json("[{\"title\":\"Jako zadovoljan\",\"text\":\"Najbolja komponenta na tržištu\",\"rating\":5}]"));

        this.mockMvc.perform(
                        get("/review/01234")
                                .with(csrf())
                                .header(HttpHeaders.AUTHORIZATION,"Bearer "+ userToken))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().encoding(StandardCharsets.UTF_8))
                .andExpect(content().json("[{\"title\":\"Katastrofa\",\"text\":\"Nemojte nikada ovo kupiti\",\"rating\":1},{\"title\":\"OK\",\"text\":\"Okej\",\"rating\":3}]"));

        this.mockMvc.perform(
                        get("/review/13234")
                                .with(csrf())
                                .header(HttpHeaders.AUTHORIZATION,"Bearer "+ userToken))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().encoding(StandardCharsets.UTF_8))
                .andExpect(content().json("[{\"title\":\"Dosta dobro!\",\"text\":\"Veoma korisna komponenta\",\"rating\":5}]"));

        this.mockMvc.perform(
                        get("/review/11224")
                                .with(csrf())
                                .header(HttpHeaders.AUTHORIZATION,"Bearer "+ userToken))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().encoding(StandardCharsets.UTF_8))
                .andExpect(content().json("[{\"title\":\"Uredu\",\"text\":\"Dosta dobro ali može bolje\",\"rating\":4},{\"title\":\"Razočaran\",\"text\":\"Nikada više ne kupujem ovo smeće\",\"rating\":1}]"));






        this.mockMvc.perform(
                        get("/review/56974")
                                .with(csrf())
                                .header(HttpHeaders.AUTHORIZATION,"Bearer "+ adminToken))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().encoding(StandardCharsets.UTF_8))
                .andExpect(content().json("[{\"title\":\"Jako zadovoljan\",\"text\":\"Najbolja komponenta na tržištu\",\"rating\":5}]"));

        this.mockMvc.perform(
                        get("/review/01234")
                                .with(csrf())
                                .header(HttpHeaders.AUTHORIZATION,"Bearer "+ adminToken))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().encoding(StandardCharsets.UTF_8))
                .andExpect(content().json("[{\"title\":\"Katastrofa\",\"text\":\"Nemojte nikada ovo kupiti\",\"rating\":1},{\"title\":\"OK\",\"text\":\"Okej\",\"rating\":3}]"));

        this.mockMvc.perform(
                        get("/review/13234")
                                .with(csrf())
                                .header(HttpHeaders.AUTHORIZATION,"Bearer "+ adminToken))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().encoding(StandardCharsets.UTF_8))
                .andExpect(content().json("[{\"title\":\"Dosta dobro!\",\"text\":\"Veoma korisna komponenta\",\"rating\":5}]"));

        this.mockMvc.perform(
                        get("/review/11224")
                                .with(csrf())
                                .header(HttpHeaders.AUTHORIZATION,"Bearer "+ adminToken))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().encoding(StandardCharsets.UTF_8))
                .andExpect(content().json("[{\"title\":\"Uredu\",\"text\":\"Dosta dobro ali može bolje\",\"rating\":4},{\"title\":\"Razočaran\",\"text\":\"Nikada više ne kupujem ovo smeće\",\"rating\":1}]"));

    }
}