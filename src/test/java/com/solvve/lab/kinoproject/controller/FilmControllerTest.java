package com.solvve.lab.kinoproject.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.solvve.lab.kinoproject.domain.Film;
import com.solvve.lab.kinoproject.dto.FilmFilter;
import com.solvve.lab.kinoproject.dto.FilmReadExtendedDTO;
import com.solvve.lab.kinoproject.dto.PageResult;
import com.solvve.lab.kinoproject.dto.film.FilmCreateDTO;
import com.solvve.lab.kinoproject.dto.film.FilmPatchDTO;
import com.solvve.lab.kinoproject.dto.film.FilmPutDTO;
import com.solvve.lab.kinoproject.dto.film.FilmReadDTO;
import com.solvve.lab.kinoproject.exception.EntityNotFoundException;
import com.solvve.lab.kinoproject.service.FilmService;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(FilmController.class)
public class FilmControllerTest extends BaseControllerTest {

    @MockBean
    private FilmService filmService;

    private FilmReadDTO createFilmRead() {
        return generateObject(FilmReadDTO.class);
    }

    private FilmReadExtendedDTO createFilmReadExtended() {
        return generateObject(FilmReadExtendedDTO.class);
    }

    @Test
    public void testGetFilm() throws Exception {
        FilmReadDTO filmReadDTO = createFilmRead();


        Mockito.when(filmService.getFilm(filmReadDTO.getId())).thenReturn(filmReadDTO);
        String resultJson = mvc.perform(get("/api/v1/films/{id}", filmReadDTO.getId()))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        FilmReadDTO filmActualDTO = objectMapper.readValue(resultJson, FilmReadDTO.class);
        Assertions.assertThat(filmActualDTO)
                .isEqualToIgnoringGivenFields(filmReadDTO,
                        "casts", "createdAt", "updatedAt");

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
        Assertions.assertThat(filmActualDTO)
                .isEqualToIgnoringGivenFields(filmReadExtended,
                        "casts", "createdAt", "updatedAt");

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
        FilmCreateDTO create = generateObject(FilmCreateDTO.class);

        FilmReadDTO read = createFilmRead();

        Mockito.when(filmService.createFilm(create)).thenReturn(read);

        String resultJson = mvc.perform(post("/api/v1/films")
                .content(objectMapper.writeValueAsString(create))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        FilmReadDTO actual = objectMapper.readValue(resultJson, FilmReadDTO.class);
        Assertions.assertThat(actual)
                .isEqualToIgnoringGivenFields(read,
                        "casts", "stils", "createdAt", "updatedAt");
    }

    @Test
    public void testPatchFilm() throws Exception {
        FilmPatchDTO patch = generateObject(FilmPatchDTO.class);

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
    public void testPutFilm() throws Exception {
        FilmPutDTO putDTO = generateObject(FilmPutDTO.class);
        FilmReadDTO read = createFilmRead();

        Mockito.when(filmService.updateFilm(read.getId(), putDTO)).thenReturn(read);

        String resultJson = mvc.perform(put("/api/v1/films/{id}", read.getId().toString())
                .content(objectMapper.writeValueAsString(putDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        FilmReadDTO actual = objectMapper.readValue(resultJson, FilmReadDTO.class);
        Assert.assertEquals(read, actual);
    }

    @Test
    public void testDeleteFilm() throws Exception {
        UUID id = UUID.randomUUID();

        mvc.perform(delete("/api/v1/films/{id}", id.toString())).andExpect(status().isOk());

        Mockito.verify(filmService).deleteFilm(id);
    }

    @Test
    public void testGetFilms() throws Exception {
        FilmFilter filter = new FilmFilter();
        filter.setLength(83);
        filter.setRealiseYear(Instant.now());
        filter.setLastUpdate(Instant.now());

        FilmReadDTO read = generateObject(FilmReadDTO.class);
        read.setLength(filter.getLength());
        read.setRealiseYear(filter.getRealiseYear());
        read.setLastUpdate(filter.getLastUpdate());

        PageResult<FilmReadDTO> expectedRes = new PageResult<>();
        expectedRes.setData(List.of(read));
        Mockito.when(filmService.getFilms(filter, PageRequest.of(0, defaultPageSize))).thenReturn(expectedRes);

        String resultJson = mvc.perform(get("/api/v1/films")
                .param("length", filter.getLength().toString())
                .param("realiseYear", filter.getRealiseYear().toString())
                .param("lastUpdate", filter.getLastUpdate().toString()))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        PageResult<FilmReadDTO> actualPage = objectMapper.readValue(resultJson, new TypeReference<>() {
        });
        Assert.assertEquals(expectedRes, actualPage);

    }

    @Test
    public void testGetFilmsWithPageingAndSorting() throws Exception {
        FilmFilter filter = new FilmFilter();
        FilmReadDTO read = createFilmRead();

        int page = 1;
        int size = 25;

        PageResult<FilmReadDTO> resultPage = new PageResult<>();
        resultPage.setPage(page);
        resultPage.setPageSize(size);
        resultPage.setTotalElements(100);
        resultPage.setTotalPages(4);
        resultPage.setData(List.of(read));

        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "length"));
        Mockito.when(filmService.getFilms(filter, pageRequest)).thenReturn(resultPage);

        String resultJson = mvc.perform(get("/api/v1/films")
                .param("page", Integer.toString(page))
                .param("size", Integer.toString(size))
                .param("sort", "length,desc"))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        PageResult<FilmReadDTO> actualPage = objectMapper.readValue(resultJson, new TypeReference<>() {
        });
        Assert.assertEquals(resultPage, actualPage);

    }

    @Test
    public void testGetFilmsWithBigPage() throws Exception {
        FilmFilter filter = new FilmFilter();
        FilmReadDTO read = createFilmRead();

        int page = 0;
        int size = 9999;

        PageResult<FilmReadDTO> resultPage = new PageResult<>();
        resultPage.setPage(page);
        resultPage.setPageSize(size);
        resultPage.setTotalElements(100);
        resultPage.setTotalPages(4);
        resultPage.setData(List.of(read));

        PageRequest pageRequest = PageRequest.of(page, maxPageSize);
        Mockito.when(filmService.getFilms(filter, pageRequest)).thenReturn(resultPage);

        String resultJson = mvc.perform(get("/api/v1/films")
                .param("page", Integer.toString(page))
                .param("size", Integer.toString(size)))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        PageResult<FilmReadDTO> actualPage = objectMapper.readValue(resultJson, new TypeReference<>() {
        });
        Assert.assertEquals(resultPage, actualPage);
    }


}