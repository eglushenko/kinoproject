package com.solvve.lab.kinoproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.solvve.lab.kinoproject.domain.Film;
import com.solvve.lab.kinoproject.dto.FilmReadExtendedDTO;
import com.solvve.lab.kinoproject.dto.film.FilmCreateDTO;
import com.solvve.lab.kinoproject.dto.film.FilmPatchDTO;
import com.solvve.lab.kinoproject.dto.film.FilmReadDTO;
import com.solvve.lab.kinoproject.exception.EntityNotFoundException;
import com.solvve.lab.kinoproject.service.FilmService;
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

import java.time.Instant;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

    private FilmReadDTO createFilmRead() {
        FilmReadDTO read = new FilmReadDTO();
        read.setId(UUID.randomUUID());
        read.setLang("RU");
        read.setFilmText("film example text");
        read.setLength(140); // in minutes
        read.setTitle("some film");
        read.setRate(8.3F);
        read.setCountry("Ukraine");
        read.setActor(" Bob square pents");
        read.setLastUpdate(Instant.parse("2020-01-03T10:15:30.00Z"));
        return read;
    }

    private FilmReadExtendedDTO createFilmReadExtended() {
        FilmReadExtendedDTO read = new FilmReadExtendedDTO();
        read.setId(UUID.randomUUID());
        read.setLang("RU");
        read.setFilmText("film example text");
        read.setLength(140); // in minutes
        read.setTitle("some film");
        read.setRate(8.3F);
        read.setCountry("Ukraine");
        read.setActor(" Bob square pents");
        read.setLastUpdate(Instant.parse("2020-01-03T10:15:30.00Z"));
        return read;
    }

    @Test
    public void testGetFilm() throws Exception {
        FilmReadDTO filmReadDTO = createFilmRead();


        Mockito.when(filmService.getFilm(filmReadDTO.getId())).thenReturn(filmReadDTO);
        String resultJson = mvc.perform(get("/api/v1/films/{id}", filmReadDTO.getId()))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        FilmReadDTO filmActualDTO = objectMapper.readValue(resultJson, FilmReadDTO.class);
        Assertions.assertThat(filmActualDTO).isEqualToIgnoringGivenFields(filmReadDTO, "casts");

        Mockito.verify(filmService).getFilm(filmReadDTO.getId());
    }

    @Test
    public void testGetFilmExtended() throws Exception {
        FilmReadExtendedDTO filmReadExtended = createFilmReadExtended();


        Mockito.when(filmService.getFilmExtended(filmReadExtended.getId())).thenReturn(filmReadExtended);
        String resultJson = mvc.perform(get("/api/v1/films/info/{id}", filmReadExtended.getId()))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        FilmReadExtendedDTO filmActualDTO = objectMapper.readValue(resultJson, FilmReadExtendedDTO.class);
        Assertions.assertThat(filmActualDTO).isEqualToIgnoringGivenFields(filmReadExtended, "casts");

        Mockito.verify(filmService).getFilmExtended(filmReadExtended.getId());
    }

    @Test
    public void testGetFilmWrongId() throws Exception {
        UUID wrongId = UUID.randomUUID();

        EntityNotFoundException exception = new EntityNotFoundException(Film.class, wrongId);
        Mockito.when(filmService.getFilm(wrongId)).thenThrow(exception);

        String resultJson = mvc.perform(get("/api/v1/films/{id}", wrongId))
                .andExpect(status().isNotFound())
                .andReturn().getResponse().getContentAsString();

        Assert.assertTrue(resultJson.contains(exception.getMessage()));
    }

    @Test
    public void testGetFilmWrongUUIDFormat() throws Exception {
        String resultJson = String.valueOf(mvc.perform(get("/api/v1/films/smile"))
                .andReturn().getResponse().getStatus());
        Assert.assertTrue(resultJson.contains("400"));
    }

    @Test
    public void testCreateFilm() throws Exception {
        FilmCreateDTO create = new FilmCreateDTO();
        create.setLang("RU");
        create.setFilmText("film example text");
        create.setLength(140); // in minutes
        create.setTitle("some film");
        create.setRate(8.3F);
        create.setCountry("Ukraine");
        create.setActor(" Bob square pents");
        create.setLastUpdate(Instant.parse("2020-01-03T10:15:30.00Z"));

        FilmReadDTO read = createFilmRead();

        Mockito.when(filmService.createFilm(create)).thenReturn(read);

        String resultJson = mvc.perform(post("/api/v1/films")
                .content(objectMapper.writeValueAsString(create))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        FilmReadDTO actual = objectMapper.readValue(resultJson, FilmReadDTO.class);
        Assertions.assertThat(actual).isEqualToIgnoringGivenFields(read, "casts");
    }

    @Test
    public void testPatchFilm() throws Exception {
        FilmPatchDTO patch = new FilmPatchDTO();
        patch.setLang("EN");
        patch.setFilmText("film example text");
        patch.setLength(140); // in minutes
        patch.setTitle("film");
        patch.setRate(1.3F);
        patch.setCountry("USA");
        patch.setActor(" Bob square pents");
        patch.setLastUpdate(Instant.parse("2020-01-03T10:15:30.00Z"));

        FilmReadDTO read = createFilmRead();

        Mockito.when(filmService.patchFilm(read.getId(), patch)).thenReturn(read);

        String resultJson = mvc.perform(patch("/api/v1/films/{id}", read.getId().toString())
                .content(objectMapper.writeValueAsString(patch))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        FilmReadDTO actual = objectMapper.readValue(resultJson, FilmReadDTO.class);
        Assert.assertEquals(actual, read);
    }

    @Test
    public void testDeleteFilm() throws Exception {
        UUID id = UUID.randomUUID();

        mvc.perform(delete("/api/v1/films/{id}", id.toString())).andExpect(status().isOk());

        Mockito.verify(filmService).deleteFilm(id);
    }


}