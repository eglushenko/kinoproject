package com.solvve.lab.kinoproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.solvve.lab.kinoproject.domain.Name;
import com.solvve.lab.kinoproject.dto.NameCreateDTO;
import com.solvve.lab.kinoproject.dto.NamePatchDTO;
import com.solvve.lab.kinoproject.dto.NameReadDTO;
import com.solvve.lab.kinoproject.exception.EntityNotFoundException;
import com.solvve.lab.kinoproject.service.NameService;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@WebMvcTest(NameController.class)
public class NameControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private NameService nameService;

    private NameReadDTO createNameRead() {
        NameReadDTO read = new NameReadDTO();
        read.setId(UUID.randomUUID());
        read.setFirstName("Mark");
        read.setLastName("Dou");
        return read;
    }

    @Test
    public void testGetName() throws Exception {
        NameReadDTO name = createNameRead();

        Mockito.when(nameService.getName(name.getId())).thenReturn(name);

        String resultJson = mvc.perform(get("/api/v1/names/{id}", name.getId()))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        NameReadDTO actual = objectMapper.readValue(resultJson, NameReadDTO.class);

        Assertions.assertThat(actual).isEqualToComparingFieldByField(name);

        Mockito.verify(nameService).getName(name.getId());

    }

    @Test
    public void testGetNameWrongId() throws Exception {
        UUID wrongId = UUID.randomUUID();

        EntityNotFoundException exception = new EntityNotFoundException(Name.class, wrongId);
        Mockito.when(nameService.getName(wrongId)).thenThrow(exception);

        String resultJson = mvc.perform(get("/api/v1/names/{id}", wrongId))
                .andExpect(status().isNotFound())
                .andReturn().getResponse().getContentAsString();

        Assert.assertTrue(resultJson.contains(exception.getMessage()));
    }

    @Test
    public void testGetNameWrongIdFormat() throws Exception {
        String resultJson = String.valueOf(mvc.perform(get("/api/v1/names/Sten"))
                .andReturn().getResponse().getStatus());
        Assert.assertTrue(resultJson.contains("400"));
    }

    @Test
    public void testCreateName() throws Exception {
        NameCreateDTO create = new NameCreateDTO();
        create.setFirstName("Mark");
        create.setLastName("Dou");

        NameReadDTO read = createNameRead();

        Mockito.when(nameService.createName(create)).thenReturn(read);

        String resultJson = mvc.perform(post("/api/v1/names")
                .content(objectMapper.writeValueAsString(create))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        NameReadDTO actual = objectMapper.readValue(resultJson, NameReadDTO.class);
        Assertions.assertThat(actual).isEqualToComparingFieldByField(read);
    }

    @Test
    public void testPatchName() throws Exception {
        NamePatchDTO patch = new NamePatchDTO();
        patch.setFirstName("Mark");
        patch.setLastName("Dou");

        NameReadDTO read = createNameRead();

        Mockito.when(nameService.patchName(read.getId(), patch)).thenReturn(read);

        String resultJson = mvc.perform(patch("/api/v1/names/{id}", read.getId().toString())
                .content(objectMapper.writeValueAsString(patch))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        NameReadDTO actual = objectMapper.readValue(resultJson, NameReadDTO.class);
        Assert.assertEquals(read, actual);
    }

    @Test
    public void testDeleteName() throws Exception {
        UUID id = UUID.randomUUID();

        mvc.perform(delete("/api/v1/names/{id}", id.toString())).andExpect(status().isOk());

        Mockito.verify(nameService).deleteName(id);
    }

}