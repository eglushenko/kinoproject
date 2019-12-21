package com.solvve.lab.kinoproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.solvve.lab.kinoproject.dto.FilmReadDTO;
import com.solvve.lab.kinoproject.service.FilmService;
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

import java.time.LocalDate;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@WebMvcTest(FilmController.class)
public class FilmControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private FilmService filmService;

    @Test
    public void testGetFilm() throws Exception {
        FilmReadDTO filmReadDTO = new FilmReadDTO();
        filmReadDTO.setId(UUID.randomUUID());
        filmReadDTO.setLang("RU");
        filmReadDTO.setFilmText("film example text");
        filmReadDTO.setLength(140); // in minutes
        filmReadDTO.setTitle("some film");
        filmReadDTO.setRate(8.3F);
        filmReadDTO.setCountry("Ukraine");
        filmReadDTO.setActor(" Bob square pents");
        filmReadDTO.setLastUpdate(LocalDate.of(2019, 12, 12));


        Mockito.when(filmService.getFilm(filmReadDTO.getId())).thenReturn(filmReadDTO);
        String resultJson = mvc.perform(get("/api/v1/films/{id}", filmReadDTO.getId()))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        FilmReadDTO filmActualDTO = objectMapper.readValue(resultJson, FilmReadDTO.class);
        Assertions.assertThat(filmActualDTO).isEqualToComparingFieldByField(filmReadDTO);

        Mockito.verify(filmService).getFilm(filmReadDTO.getId());
    }

}