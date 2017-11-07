package com.example.demo.controller;

import com.example.demo.domain.entity.Lesson;
import com.example.demo.domain.repository.LessonRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.ArrayList;
import java.util.Date;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.is;

@RunWith(SpringRunner.class)
@WebMvcTest(LessonsController.class)
public class LessonsControllerTestMockDB {


    @Autowired
    private MockMvc mvc;

    @MockBean
    LessonRepository lessonRepositoryMock;

    @Before
    public void setup() {
    }

    @Test
    public void getAllTest() throws Exception {

        // Setup
        ArrayList<Lesson> lessonList = new ArrayList<>();
        Lesson lesson = new Lesson();
        lesson.setId(Long.valueOf(1));
        long timestamp = new Date().getTime();
        lesson.setDeliveredOn(timestamp);
        lesson.setTitle("Lesson");
        lessonList.add(lesson);
        when(lessonRepositoryMock.findAll()).thenReturn(lessonList);


        //Exercise & Assert
        mvc.perform(get("/lessons"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].deliveredOn", is(timestamp)));

        verify(lessonRepositoryMock, times(1)).findAll();
    }

    @Test
    public void testCreate() throws Exception {
        String title = "Test200";
        MockHttpServletRequestBuilder request = post("/lessons")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\": \"" + title + "\"}");

        Lesson lesson = new Lesson();

        lesson.setTitle(title);

        when(lessonRepositoryMock.save(any(Lesson.class))).thenReturn(lesson);

        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", equalTo(title) ));

        verify(lessonRepositoryMock).save(any(Lesson.class));    // <----- #2
    }
}
