package com.solvve.lab.kinoproject.controller;

import com.solvve.lab.kinoproject.domain.Review;
import com.solvve.lab.kinoproject.dto.typo.TypoCreateDTO;
import com.solvve.lab.kinoproject.dto.typo.TypoPatchDTO;
import com.solvve.lab.kinoproject.dto.typo.TypoPutDTO;
import com.solvve.lab.kinoproject.dto.typo.TypoReadDTO;
import com.solvve.lab.kinoproject.exception.EntityNotFoundException;
import com.solvve.lab.kinoproject.service.TypoService;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TypoController.class)
public class TypoControllerTest extends BaseControllerTest {

    @MockBean
    private TypoService typoService;


    private TypoReadDTO createTypoRead() {
        return generateObject(TypoReadDTO.class);
    }

    @Test
    public void testGetTypo() throws Exception {
        TypoReadDTO review = createTypoRead();

        Mockito.when(typoService.getTypo(review.getId())).thenReturn(review);

        String resultJson = mvc.perform(get("/api/v1/typos/{id}", review.getId()))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        TypoReadDTO actual = objectMapper.readValue(resultJson, TypoReadDTO.class);

        Assertions.assertThat(actual).isEqualToComparingFieldByField(review);

        Mockito.verify(typoService).getTypo(review.getId());

    }

    @Test
    public void testGetTypoWrongId() throws Exception {
        UUID wrongId = UUID.randomUUID();

        EntityNotFoundException exception = new EntityNotFoundException(Review.class, wrongId);
        Mockito.when(typoService.getTypo(wrongId)).thenThrow(exception);

        String resultJson = mvc.perform(get("/api/v1/typos/{id}", wrongId))
                .andExpect(status().isNotFound())
                .andReturn().getResponse().getContentAsString();

        Assert.assertTrue(resultJson.contains(exception.getMessage()));
    }

    @Test
    public void testGetTypoWrongIdFormat() throws Exception {
        String resultJson = String.valueOf(mvc.perform(get("/api/v1/typos/1"))
                .andReturn().getResponse().getStatus());
        Assert.assertTrue(resultJson.contains("400"));
    }

    @Test
    public void testCreateTypo() throws Exception {
        TypoCreateDTO create = generateObject(TypoCreateDTO.class);

        TypoReadDTO read = createTypoRead();

        Mockito.when(typoService.createTypo(create)).thenReturn(read);

        String resultJson = mvc.perform(post("/api/v1/typos")
                .content(objectMapper.writeValueAsString(create))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        TypoReadDTO actual = objectMapper.readValue(resultJson, TypoReadDTO.class);
        Assertions.assertThat(actual).isEqualToIgnoringGivenFields(read, "createdAt", "updatedAt");
    }

    @Test
    public void testPatchTypo() throws Exception {
        TypoPatchDTO patch = generateObject(TypoPatchDTO.class);

        TypoReadDTO read = createTypoRead();

        Mockito.when(typoService.patchTypo(read.getId(), patch)).thenReturn(read);

        String resultJson = mvc.perform(patch("/api/v1/typos/{id}", read.getId().toString())
                .content(objectMapper.writeValueAsString(patch))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        TypoReadDTO actual = objectMapper.readValue(resultJson, TypoReadDTO.class);
        Assert.assertEquals(read, actual);
    }

    @Test
    public void testPutTypo() throws Exception {
        TypoPutDTO putDTO = generateObject(TypoPutDTO.class);

        TypoReadDTO read = createTypoRead();

        Mockito.when(typoService.updateTypo(read.getId(), putDTO)).thenReturn(read);

        String resultJson = mvc.perform(put("/api/v1/typos/{id}", read.getId().toString())
                .content(objectMapper.writeValueAsString(putDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        TypoReadDTO actual = objectMapper.readValue(resultJson, TypoReadDTO.class);
        Assert.assertEquals(read, actual);
    }

    @Test
    public void testDeleteTypo() throws Exception {
        UUID id = UUID.randomUUID();

        mvc.perform(delete("/api/v1/typos/{id}", id.toString())).andExpect(status().isOk());

        Mockito.verify(typoService).deleteTypo(id);
    }


}