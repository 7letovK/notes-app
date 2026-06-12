package com.notepad.notesapp.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.notepad.notesapp.dto.AuthRequest;
import com.notepad.notesapp.dto.RegisterRequest;
import com.notepad.notesapp.entity.Note;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class NoteControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private String authToken;

    @BeforeEach
    void setUp() throws Exception {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("noteuser");
        registerRequest.setEmail("note@example.com");
        registerRequest.setPassword("password123");

        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerRequest)))
                .andExpect(status().isCreated());

        AuthRequest loginRequest = new AuthRequest();
        loginRequest.setUsername("noteuser");
        loginRequest.setPassword("password123");

        MvcResult result = mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        authToken = objectMapper.readTree(response).get("token").asText();
    }

    @Test
    void createNote_ShouldReturnCreatedNote() throws Exception {
        String noteJson = "{\"title\":\"Test Note\",\"content\":\"Test Content\"}";

        mockMvc.perform(post("/api/notes")
                .header("Authorization", "Bearer " + authToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(noteJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Test Note"))
                .andExpect(jsonPath("$.content").value("Test Content"));
    }

    @Test
    void getAllNotes_ShouldReturnList() throws Exception {
        mockMvc.perform(get("/api/notes")
                .header("Authorization", "Bearer " + authToken))
                .andExpect(status().isOk());
    }

    @Test
    void getNoteById_ShouldReturnNote() throws Exception {
        String noteJson = "{\"title\":\"Get By ID\",\"content\":\"Content\"}";

        MvcResult createResult = mockMvc.perform(post("/api/notes")
                .header("Authorization", "Bearer " + authToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(noteJson))
                .andExpect(status().isCreated())
                .andReturn();

        String createdNoteJson = createResult.getResponse().getContentAsString();
        Long noteId = objectMapper.readTree(createdNoteJson).get("id").asLong();

        mockMvc.perform(get("/api/notes/{id}", noteId)
                .header("Authorization", "Bearer " + authToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Get By ID"));
    }

    @Test
    void updateNote_ShouldReturnUpdatedNote() throws Exception {
        String noteJson = "{\"title\":\"Before Update\",\"content\":\"Original\"}";

        MvcResult createResult = mockMvc.perform(post("/api/notes")
                .header("Authorization", "Bearer " + authToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(noteJson))
                .andExpect(status().isCreated())
                .andReturn();

        String createdNoteJson = createResult.getResponse().getContentAsString();
        Long noteId = objectMapper.readTree(createdNoteJson).get("id").asLong();

        String updatedNoteJson = "{\"title\":\"After Update\",\"content\":\"Updated\"}";

        mockMvc.perform(put("/api/notes/{id}", noteId)
                .header("Authorization", "Bearer " + authToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatedNoteJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("After Update"));
    }

    @Test
    void deleteNote_ShouldReturnNoContent() throws Exception {
        String noteJson = "{\"title\":\"To Delete\",\"content\":\"Will be deleted\"}";

        MvcResult createResult = mockMvc.perform(post("/api/notes")
                .header("Authorization", "Bearer " + authToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(noteJson))
                .andExpect(status().isCreated())
                .andReturn();

        String createdNoteJson = createResult.getResponse().getContentAsString();
        Long noteId = objectMapper.readTree(createdNoteJson).get("id").asLong();

        mockMvc.perform(delete("/api/notes/{id}", noteId)
                .header("Authorization", "Bearer " + authToken))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/notes/{id}", noteId)
                .header("Authorization", "Bearer " + authToken))
                .andExpect(status().isNotFound());
    }

    @Test
    void accessWithoutToken_ShouldReturnUnauthorized() throws Exception {
        mockMvc.perform(get("/api/notes"))
                .andExpect(status().isUnauthorized());
    }
}