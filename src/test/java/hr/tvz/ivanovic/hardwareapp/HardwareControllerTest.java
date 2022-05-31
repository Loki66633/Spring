package hr.tvz.ivanovic.hardwareapp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import javax.print.attribute.standard.Media;
import javax.transaction.Transactional;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class HardwareControllerTest {

    //static String userToken = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyIiwiZXhwIjoxNjU0MzUyMTgyLCJpYXQiOjE2NTM3NDczODIsImF1dGhvcml0aWVzIjoiUk9MRV9VU0VSIn0.I_PKTOxK51jo57xhehPm2qa7dV39wirXQg8Mz27audJ_lQpxSJcY9saRB__-uNHR6MNk-YFf0PAdjuqwHX6HMw";
    //static String adminToken ="eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTY1NDM1MjIxNSwiaWF0IjoxNjUzNzQ3NDE1LCJhdXRob3JpdGllcyI6IlJPTEVfQURNSU4ifQ.d_SKTL7hOAXsHv0pEXB0nwOFUdsCeTwqK3UVWV-H_9iat4qveDhkcCW3YsIx9D1kGbdtOVcPo2rzBEMBC2lWhA";

     String adminToken;
     String userToken;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper = new ObjectMapper();



    @BeforeEach
    public void setAdminToken() throws Exception {
        Map<String,Object> body = new HashMap<>();
        body.put("username", "admin");
        body.put("password", "adminpass");

        MvcResult res = this.mockMvc.perform(
                        post("/authentication/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(body))
                )
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().encoding(StandardCharsets.UTF_8))
                .andExpect(jsonPath("$.jwt").isNotEmpty()).andReturn();


        String nesto = res.getResponse().getContentAsString();
        String adminToken = nesto.substring(8,nesto.length()-2);
        this.adminToken = adminToken;

    }

    @BeforeEach
    public void setUserToken() throws Exception {
        Map<String,Object> body = new HashMap<>();
        body.put("username", "user");
        body.put("password", "userpass");

        MvcResult res = this.mockMvc.perform(
                        post("/authentication/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(body))
                )
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().encoding(StandardCharsets.UTF_8))
                .andExpect(jsonPath("$.jwt").isNotEmpty()).andReturn();


        String nesto = res.getResponse().getContentAsString();
        String userToken = nesto.substring(8,nesto.length()-2);
        this.userToken = userToken;

    }

    @Test
    void getAllHardware() throws Exception {

        this.mockMvc.perform(
                get("/hardware")
                        .with(csrf())
                        .header(HttpHeaders.AUTHORIZATION,"Bearer "+ userToken))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().encoding(StandardCharsets.UTF_8))
                .andExpect(content().json("[{\"name\":\"Test\",\"price\":3000.0,\"code\":\"01234\"},{\"name\":\"RTX 2060\",\"price\":4000.0,\"code\":\"13234\"},{\"name\":\"Ryzen 5 2600x\",\"price\":2400.0,\"code\":\"56974\"},{\"name\":\"Gigabyte Z370P\",\"price\":750.34,\"code\":\"11224\"}]"));

        this.mockMvc.perform(
                        get("/hardware")
                                .with(csrf())
                                .header(HttpHeaders.AUTHORIZATION,"Bearer "+ adminToken))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().encoding(StandardCharsets.UTF_8))
                .andExpect(content().json("[{\"name\":\"Test\",\"price\":3000.0,\"code\":\"01234\"},{\"name\":\"RTX 2060\",\"price\":4000.0,\"code\":\"13234\"},{\"name\":\"Ryzen 5 2600x\",\"price\":2400.0,\"code\":\"56974\"},{\"name\":\"Gigabyte Z370P\",\"price\":750.34,\"code\":\"11224\"}]"));


    }

    @Test
    void getHardwareByCode() throws Exception {
        this.mockMvc.perform(
                        get("/hardware/56974")
                                .with(csrf())
                                .header(HttpHeaders.AUTHORIZATION,"Bearer "+ userToken))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().encoding(StandardCharsets.UTF_8))
                .andExpect(content().json("{\"name\":\"Ryzen 5 2600x\",\"price\":2400.0,\"code\":\"56974\"}"));

        this.mockMvc.perform(
                        get("/hardware/01234")
                                .with(csrf())
                                .header(HttpHeaders.AUTHORIZATION,"Bearer "+ userToken))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().encoding(StandardCharsets.UTF_8))
                .andExpect(content().json("{\"name\":\"Test\",\"price\":3000.0,\"code\":\"01234\"}"));

        this.mockMvc.perform(
                        get("/hardware/13234")
                                .with(csrf())
                                .header(HttpHeaders.AUTHORIZATION,"Bearer "+ userToken))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().encoding(StandardCharsets.UTF_8))
                .andExpect(content().json("{\"name\":\"RTX 2060\",\"price\":4000.0,\"code\":\"13234\"}"));

        this.mockMvc.perform(
                        get("/hardware/11224")
                                .with(csrf())
                                .header(HttpHeaders.AUTHORIZATION,"Bearer "+ userToken))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().encoding(StandardCharsets.UTF_8))
                .andExpect(content().json("{\"name\":\"Gigabyte Z370P\",\"price\":750.34,\"code\":\"11224\"}"));




        this.mockMvc.perform(
                        get("/hardware/56974")
                                .with(csrf())
                                .header(HttpHeaders.AUTHORIZATION,"Bearer "+ adminToken))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().encoding(StandardCharsets.UTF_8))
                .andExpect(content().json("{\"name\":\"Ryzen 5 2600x\",\"price\":2400.0,\"code\":\"56974\"}"));

        this.mockMvc.perform(
                        get("/hardware/01234")
                                .with(csrf())
                                .header(HttpHeaders.AUTHORIZATION,"Bearer "+ adminToken))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().encoding(StandardCharsets.UTF_8))
                .andExpect(content().json("{\"name\":\"Test\",\"price\":3000.0,\"code\":\"01234\"}"));

        this.mockMvc.perform(
                        get("/hardware/13234")
                                .with(csrf())
                                .header(HttpHeaders.AUTHORIZATION,"Bearer "+ adminToken))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().encoding(StandardCharsets.UTF_8))
                .andExpect(content().json("{\"name\":\"RTX 2060\",\"price\":4000.0,\"code\":\"13234\"}"));

        this.mockMvc.perform(
                        get("/hardware/11224")
                                .with(csrf())
                                .header(HttpHeaders.AUTHORIZATION,"Bearer "+ adminToken))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().encoding(StandardCharsets.UTF_8))
                .andExpect(content().json("{\"name\":\"Gigabyte Z370P\",\"price\":750.34,\"code\":\"11224\"}"));




    }

    @Test
    @Transactional
    void saveHardware_Admin() throws Exception {

        Map<String,Object> body = new HashMap<>();
        body.put("name","RTX 3090");
        body.put("code","12345");
        body.put("price",125.0);
        body.put("type","GPU");
        body.put("stock",2);

        this.mockMvc.perform(
                        post("/hardware")
                                .with(csrf())
                                .header(HttpHeaders.AUTHORIZATION,"Bearer "+ adminToken)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().encoding(StandardCharsets.UTF_8))
                .andExpect(content().json("{\"name\":\"RTX 3090\",\"price\":125.0,\"code\":\"12345\"}"));


    }

    @Test
    @Transactional
    void saveHardware_User() throws Exception {

        Map<String,Object> body = new HashMap<>();
        body.put("name","RTX 3090");
        body.put("code","12345");
        body.put("price",125.0);
        body.put("type","GPU");
        body.put("stock",2);

        this.mockMvc.perform(
                        post("/hardware")
                                .with(csrf())
                                .header(HttpHeaders.AUTHORIZATION,"Bearer "+ userToken)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isForbidden());


    }

    @Test
    @Transactional
    void deleteHardware_Admin() throws Exception {
        this.mockMvc.perform(
                        delete("/hardware/01234")
                                .with(csrf())
                                .header(HttpHeaders.AUTHORIZATION,"Bearer "+ adminToken)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNoContent());

        this.mockMvc.perform(
                        delete("/hardware/13234")
                                .with(csrf())
                                .header(HttpHeaders.AUTHORIZATION,"Bearer "+ adminToken)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNoContent());

        this.mockMvc.perform(
                        delete("/hardware/56974")
                                .with(csrf())
                                .header(HttpHeaders.AUTHORIZATION,"Bearer "+ adminToken)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNoContent());

        this.mockMvc.perform(
                        delete("/hardware/11224")
                                .with(csrf())
                                .header(HttpHeaders.AUTHORIZATION,"Bearer "+ adminToken)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNoContent());
    }

    @Test
    @Transactional
    void deleteHardware_User() throws Exception {
        this.mockMvc.perform(
                        delete("/hardware/01234")
                                .with(csrf())
                                .header(HttpHeaders.AUTHORIZATION,"Bearer "+ userToken)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isForbidden());

        this.mockMvc.perform(
                        delete("/hardware/13234")
                                .with(csrf())
                                .header(HttpHeaders.AUTHORIZATION,"Bearer "+ userToken)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isForbidden());

        this.mockMvc.perform(
                        delete("/hardware/56974")
                                .with(csrf())
                                .header(HttpHeaders.AUTHORIZATION,"Bearer "+ userToken)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isForbidden());

        this.mockMvc.perform(
                        delete("/hardware/11224")
                                .with(csrf())
                                .header(HttpHeaders.AUTHORIZATION,"Bearer "+ userToken)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isForbidden());
    }
}

/*
{
        "name": "RTX 3090",
        "code": "12345",
        "price": 125.0,
        "type": "GPU",
        "stock":2
    }
 */