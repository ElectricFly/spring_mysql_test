package com.example.demo.controller;


import com.example.demo.domain.entity.Lesson;
import com.example.demo.domain.repository.LessonRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class LessonsControllerTestMockMvc {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    LessonRepository lessonRepository;

    private ObjectMapper mapper = new ObjectMapper();

    @Before
    public void setUp() throws Exception {
        lessonRepository.deleteAll();
    }

    @After
    public void tearDown() throws Exception {
        lessonRepository.deleteAll();
    }

    @Test
    public void testGetAll() throws Exception {
        Lesson expected = new Lesson();
        expected.setTitle("Lesson1");
        expected.setDeliveredOn(new Date().getTime());

        lessonRepository.save(expected);

        String response = mockMvc.perform(get("/lessons"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<Lesson> result = mapper.readValue(response, new TypeReference<List<Lesson>>() {});

        Assert.assertEquals("GET should return one item", 1, result.size());
        Lesson actual = result.get(0);
        Assert.assertTrue("GET response should match what is in DB", expected.equals(actual));
    }
}
