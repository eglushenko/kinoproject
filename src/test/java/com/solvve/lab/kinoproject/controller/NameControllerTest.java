package com.solvve.lab.kinoproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.solvve.lab.kinoproject.dto.NameReadDTO;
import com.solvve.lab.kinoproject.service.NameService;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

}