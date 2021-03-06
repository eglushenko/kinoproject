package com.solvve.lab.kinoproject.controller;

import com.solvve.lab.kinoproject.domain.Cast;
import com.solvve.lab.kinoproject.dto.cast.CastCreateDTO;
import com.solvve.lab.kinoproject.dto.cast.CastPatchDTO;
import com.solvve.lab.kinoproject.dto.cast.CastPutDTO;
import com.solvve.lab.kinoproject.dto.cast.CastReadDTO;
import com.solvve.lab.kinoproject.exception.EntityNotFoundException;
import com.solvve.lab.kinoproject.exception.hander.ErrorInfo;
import com.solvve.lab.kinoproject.service.CastService;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(CastController.class)
public class CastControllerTest extends BaseControllerTest {


    @MockBean
    private CastService castService;

    private CastReadDTO createCastRead() {
        CastReadDTO read = generateObject(CastReadDTO.class);
        return read;
    }

    @Test
    public void testGetCast() throws Exception {
        CastReadDTO cast = createCastRead();

        Mockito.when(castService.getCast(cast.getId())).thenReturn(cast);

        String resultJson = mvc.perform(get("/api/v1/casts/{id}", cast.getId()))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        CastReadDTO actual = objectMapper.readValue(resultJson, CastReadDTO.class);
        Assertions.assertThat(actual)
                .isEqualToIgnoringGivenFields(cast, "film", "name", "createdAt", "updatedAt");

        Mockito.verify(castService).getCast(cast.getId());
    }

    @Test
    public void testGetCastWrongId() throws Exception {
        UUID wrongId = UUID.randomUUID();

        EntityNotFoundException exception = new EntityNotFoundException(Cast.class, wrongId);

        Mockito.when(castService.getCast(wrongId)).thenThrow(exception);

        String resultJson = mvc.perform(get("/api/v1/casts/{id}", wrongId))
                .andExpect(status().isNotFound())
                .andReturn().getResponse().getContentAsString();
        Assert.assertTrue(resultJson.contains(exception.getMessage()));
    }

    @Test
    public void testGetCastWrongIdFormat() throws Exception {
        String resultJson = String.valueOf(mvc.perform(get("/api/v1/casts/22222"))
                .andReturn().getResponse().getStatus());
        Assert.assertTrue(resultJson.contains("400"));

    }

    @Test
    public void testCreateCast() throws Exception {
        CastCreateDTO create = generateObject(CastCreateDTO.class);

        CastReadDTO read = createCastRead();

        Mockito.when(castService.createCast(create)).thenReturn(read);

        String resultJson = mvc.perform(post("/api/v1/casts")
                .content(objectMapper.writeValueAsString(create))
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        CastReadDTO castReadDTO = objectMapper.readValue(resultJson, CastReadDTO.class);
        Assertions.assertThat(castReadDTO)
                .isEqualToIgnoringGivenFields(read,
                        "film", "name", "createdAt", "updatedAt");
    }

    @Test
    public void testPatchCast() throws Exception {
        CastPatchDTO patchDTO = generateObject(CastPatchDTO.class);
        CastReadDTO read = createCastRead();

        Mockito.when(castService.patchCast(read.getId(), patchDTO)).thenReturn(read);

        String resultJson = mvc.perform(patch("/api/v1/casts/{id}", read.getId().toString())
                .content(objectMapper.writeValueAsString(patchDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        CastReadDTO actual = objectMapper.readValue(resultJson, CastReadDTO.class);
        Assert.assertEquals(read, actual);

    }

    @Test
    public void testPutCast() throws Exception {
        CastPutDTO putDTO = generateObject(CastPutDTO.class);
        CastReadDTO read = createCastRead();

        Mockito.when(castService.updateCast(read.getId(), putDTO)).thenReturn(read);

        String resultJson = mvc.perform(put("/api/v1/casts/{id}", read.getId().toString())
                .content(objectMapper.writeValueAsString(putDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        CastReadDTO actualCast = objectMapper.readValue(resultJson, CastReadDTO.class);
        Assert.assertEquals(read, actualCast);
    }


    @Test
    public void testDeleteCast() throws Exception {
        UUID id = UUID.randomUUID();

        mvc.perform(delete("/api/v1/casts/{id}", id.toString())).andExpect(status().isOk());

        Mockito.verify(castService).deleteCast(id);
    }

    @Test
    public void testCreateCastValidationFiled() throws Exception {
        CastCreateDTO castCreate = new CastCreateDTO();
        String resultJson = mvc.perform(post("/api/v1/casts")
                .content(objectMapper.writeValueAsString(castCreate))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andReturn().getResponse().getContentAsString();
        objectMapper.readValue(resultJson, ErrorInfo.class);
        Mockito.verify(castService, Mockito.never()).createCast(ArgumentMatchers.any());

    }


}