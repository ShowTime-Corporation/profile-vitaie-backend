package showtime_corp.profile_vitaile.integration.controller;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import showtime_corp.profile_vitaile.dto.AuthRequest;
import showtime_corp.profile_vitaile.dto.RegisterRequest;
import tools.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class AuthControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Order(1)
    void ShouldRegisterUserSuccessfully() throws Exception {
        RegisterRequest request = new RegisterRequest();
        request.setEmail("test@email.com");
        request.setPassword("Password123");
        request.setFirstName("Test");
        request.setLastName("User");

        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @Order(2)
    void ShouldReturn409EmailDuplicate() throws Exception{
        RegisterRequest request = new RegisterRequest();
        request.setEmail("test@email.com");
        request.setPassword("Password123");
        request.setFirstName("santiago");
        request.setLastName("Toro");

        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isConflict());
    }

    @Test
    @Order(3)
    void ShouldReturn400BadRequest() throws Exception{
        RegisterRequest request = new RegisterRequest();
        request.setEmail("testcom");
        request.setPassword("Password123");
        request.setFirstName("santiago");
        request.setLastName("Toro");

        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(4)
    void ShouldLoginSuccessfullyandReturnJwt() throws Exception{
        AuthRequest request = new AuthRequest();
        request.setEmail("test@email.com");
        request.setPassword("Password123");
        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        ).andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists())
                .andExpect(jsonPath("$.token").isString());
    }

    @Test
    @Order(5)
    void ShouldReturn404WhenUserDoesntExits() throws Exception{
        AuthRequest request = new AuthRequest();
        request.setEmail("ximenaa@email.com");
        request.setPassword("Password123");
        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        ).andExpect(status().isNotFound());
    }


    @Test
    @Order(6)
    void ShouldReturn400WhenRequestIsInvalid() throws Exception{
        AuthRequest request = new AuthRequest();
        request.setEmail("ximenaa@email.com");
        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        ).andExpect(status().isBadRequest());

    }

    @Test
    @Order(7)
    void ShouldReturn401WhenPasswordIsWrong() throws Exception {
        AuthRequest request = new AuthRequest();
        request.setEmail("test@email.com");
        request.setPassword("WrongPassword");

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized());
    }




}
