package showtime_corp.profile_vitaile.integration.security;

import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import showtime_corp.profile_vitaile.dto.AuthRequest;
import showtime_corp.profile_vitaile.dto.RegisterRequest;
import tools.jackson.databind.ObjectMapper;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class JwtSecurityIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private static String jwt;

    @Test
    @Order(1)
    @DisplayName("Should reject access without JWT")
    void shouldRejectWithoutToken() throws Exception {
        mockMvc.perform(get("/user/me"))
                .andExpect(status().isForbidden());
    }

    @Test
    @Order(2)
    @DisplayName("Should register and login user and return JWT")
    void shouldLoginAndReturnJwt() throws Exception {

        RegisterRequest register = new RegisterRequest();
        register.setEmail("jwt@test.com");
        register.setPassword("Password123");
        register.setFirstName("JWT");
        register.setLastName("User");

        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(register)))
                .andExpect(status().is2xxSuccessful());

        AuthRequest login = new AuthRequest();
        login.setEmail("jwt@test.com");
        login.setPassword("Password123");

        MvcResult result = mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(login)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists())
                .andReturn();

        jwt = JsonPath.read(
                result.getResponse().getContentAsString(),
                "$.token"
        );

        assert jwt != null;
    }

    @Test
    @Order(3)
    @DisplayName("Should allow access with valid JWT")
    void shouldAllowWithValidJwt() throws Exception {
        mockMvc.perform(get("/user/me")
                        .header("Authorization", "Bearer " + jwt))
                .andExpect(status().isOk());
    }
}
