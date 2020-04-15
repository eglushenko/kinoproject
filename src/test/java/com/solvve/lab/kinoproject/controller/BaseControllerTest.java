package com.solvve.lab.kinoproject.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.bitbucket.brunneng.br.RandomObjectGenerator;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


@ActiveProfiles("test")
@RunWith(SpringRunner.class)
public abstract class BaseControllerTest {

    @Autowired
    protected MockMvc mvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Value("${spring.data.web.pageable.default-page-size}")
    protected int defaultPageSize;

    @Value("${spring.data.web.pageable.max-page-size}")
    protected int maxPageSize;

    private RandomObjectGenerator generator = new RandomObjectGenerator();

    protected <T> T generateObject(Class<T> objectClass) {
        return generator.generateRandomObject(objectClass);
    }


}
