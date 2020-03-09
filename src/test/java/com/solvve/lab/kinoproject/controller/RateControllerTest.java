package com.solvve.lab.kinoproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.solvve.lab.kinoproject.domain.Rate;
import com.solvve.lab.kinoproject.dto.rate.RateCreateDTO;
import com.solvve.lab.kinoproject.dto.rate.RatePatchDTO;
import com.solvve.lab.kinoproject.dto.rate.RatePutDTO;
import com.solvve.lab.kinoproject.dto.rate.RateReadDTO;
import com.solvve.lab.kinoproject.enums.RateObjectType;
import com.solvve.lab.kinoproject.exception.EntityNotFoundException;
import com.solvve.lab.kinoproject.service.RateService;
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
@WebMvcTest(RateController.class)
public class RateControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RateService rateService;

    private RateReadDTO createRateRead() {
        RateReadDTO read = new RateReadDTO();
        read.setId(UUID.randomUUID());
        read.setRate(3.0);
        read.setRatedObjectId(UUID.randomUUID());
        read.setType(RateObjectType.FILM);
        return read;
    }

    @Test
    public void testGetRate() throws Exception {
        RateReadDTO rate = createRateRead();

        Mockito.when(rateService.getRate(rate.getId())).thenReturn(rate);

        String resultJson = mvc.perform(get("/api/v1/rates/{id}", rate.getId()))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        RateReadDTO actual = objectMapper.readValue(resultJson, RateReadDTO.class);

        Assertions.assertThat(actual)
                .isEqualToIgnoringGivenFields(rate, "customerId", "createdAt", "updatedAt");

        Mockito.verify(rateService).getRate(rate.getId());

    }

    @Test
    public void testGetRateWrongId() throws Exception {
        UUID wrongId = UUID.randomUUID();

        EntityNotFoundException exception = new EntityNotFoundException(Rate.class, wrongId);
        Mockito.when(rateService.getRate(wrongId)).thenThrow(exception);

        String resultJson = mvc.perform(get("/api/v1/rates/{id}", wrongId))
                .andExpect(status().isNotFound())
                .andReturn().getResponse().getContentAsString();

        Assert.assertTrue(resultJson.contains(exception.getMessage()));
    }

    @Test
    public void testGetRateWrongIdFormat() throws Exception {
        String resultJson = String.valueOf(mvc.perform(get("/api/v1/rates/00000001"))
                .andReturn().getResponse().getStatus());
        Assert.assertTrue(resultJson.contains("400"));
    }

    @Test
    public void testCreateRate() throws Exception {
        RateCreateDTO create = new RateCreateDTO();
        create.setRate(3.0);
        create.setRatedObjectId(UUID.randomUUID());
        create.setType(RateObjectType.FILM);

        RateReadDTO read = createRateRead();

        Mockito.when(rateService.createRate(create)).thenReturn(read);

        String resultJson = mvc.perform(post("/api/v1/rates")
                .content(objectMapper.writeValueAsString(create))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        RateReadDTO actual = objectMapper.readValue(resultJson, RateReadDTO.class);
        Assertions.assertThat(actual)
                .isEqualToIgnoringGivenFields(read, "customerId", "createdAt", "updatedAt");
    }

    @Test
    public void testPatchRate() throws Exception {
        RatePatchDTO patch = new RatePatchDTO();
        patch.setRate(3.6);
        patch.setType(RateObjectType.COMMENT);
        patch.setRatedObjectId(UUID.randomUUID());

        RateReadDTO read = createRateRead();

        Mockito.when(rateService.patchRate(read.getId(), patch)).thenReturn(read);

        String resultJson = mvc.perform(patch("/api/v1/rates/{id}", read.getId().toString())
                .content(objectMapper.writeValueAsString(patch))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        RateReadDTO actual = objectMapper.readValue(resultJson, RateReadDTO.class);
        Assert.assertEquals(read, actual);
    }

    @Test
    public void testPutRate() throws Exception {
        RatePutDTO putDTO = new RatePutDTO();
        putDTO.setType(RateObjectType.FILM);
        putDTO.setRatedObjectId(UUID.randomUUID());
        putDTO.setRate(6.6);

        RateReadDTO read = createRateRead();

        Mockito.when(rateService.updateRate(read.getId(), putDTO)).thenReturn(read);

        String resultJson = mvc.perform(put("/api/v1/rates/{id}", read.getId().toString())
                .content(objectMapper.writeValueAsString(putDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        RateReadDTO actual = objectMapper.readValue(resultJson, RateReadDTO.class);
        Assert.assertEquals(read, actual);
    }

    @Test
    public void testDeleteRate() throws Exception {
        UUID id = UUID.randomUUID();

        mvc.perform(delete("/api/v1/rates/{id}", id.toString())).andExpect(status().isOk());

        Mockito.verify(rateService).deleteRate(id);
    }


}