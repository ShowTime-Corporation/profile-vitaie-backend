package showtime_corp.profile_vitaile.integration.controller;

import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import showtime_corp.profile_vitaile.dto.AuthRequest;
import showtime_corp.profile_vitaile.dto.RegisterRequest;
import showtime_corp.profile_vitaile.dto.UserProfileRequestDTO;
import tools.jackson.databind.ObjectMapper;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserProfileControllerIT {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    private String jwt;

    @Test
    @Order(1)
    void setupUserAndLogin() throws Exception {
        RegisterRequest register = new RegisterRequest();
        register.setEmail("profile@test.com");
        register.setPassword("Password123");
        register.setFirstName("Profile");
        register.setLastName("User");

        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(register)))
                .andExpect(status().is2xxSuccessful());

        AuthRequest login = new AuthRequest();
        login.setEmail("profile@test.com");
        login.setPassword("Password123");

        MvcResult result = mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(login)))
                .andExpect(status().isOk())
                .andReturn();

        jwt = JsonPath.read(
                result.getResponse().getContentAsString(),
                "$.token"
        );
    }

    @Test
    @Order(2)
    void shouldGetMyProfile() throws Exception {

        mockMvc.perform(get("/user/me")
                        .header("Authorization", "Bearer " + jwt)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
    }


    @Test
    @Order(3)
    void shouldUploadCv() throws Exception {
        byte[] pdfBytes = Files.readAllBytes(
                Paths.get("src/test/resources/test-cv.pdf")
        );

        MockMultipartFile file = new MockMultipartFile(
                "cv",
                "test-cv.pdf",
                MediaType.APPLICATION_PDF_VALUE,
                pdfBytes
        );


        mockMvc.perform(multipart("/user/analyze/1")
                        .file(file)
                        .header("Authorization", "Bearer " + jwt))
                .andExpect(status().isOk());
    }

    @Test
    @Order(4)
    void shouldReturn404WhenResumeNotFound() throws Exception {
        mockMvc.perform(get("/user/resume/999")
                        .header("Authorization", "Bearer " + jwt))
                .andExpect(status().isNotFound());
    }



}
