package com.solvve.lab.kinoproject.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.solvve.lab.kinoproject.dto.genere.GenereReadDTO;
import com.solvve.lab.kinoproject.service.GenereService;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(GenereController.class)
public class GenereControllerTest extends BaseControllerTest {

    @MockBean
    private GenereService genereService;

    @Test
    public void testAddGenereToFilm() throws Exception {
        UUID filmId = UUID.randomUUID();
        UUID genereId = UUID.randomUUID();

        GenereReadDTO read = generateObject(GenereReadDTO.class);
        read.setId(genereId);
        List<GenereReadDTO> expected = List.of(read);

        Mockito.when(genereService.addGenereToFilm(filmId, genereId)).thenReturn(expected);

        String resultJson = mvc.perform(post("/api/v1/films/{filmId}/generes/{id}", filmId, genereId))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        List<GenereReadDTO> actualRes = objectMapper.readValue(resultJson, new TypeReference<>() {
        });
        Assert.assertEquals(expected, actualRes);
    }


}