package com.example.demo.controller;

import com.google.gson.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LessonsControllerTestTemplate {

    @Autowired
    TestRestTemplate testRestTemplate;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Before
    public void clearDatabase() throws Exception {

        this.jdbcTemplate.execute("TRUNCATE TABLE lessons");

    }

    @Test
    public void testGetAll() {

        long timestamp = 1510054108997L;
        jdbcTemplate.execute("INSERT INTO lessons (title, delivered_on) VALUES ('Test', '1510054108997');");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> requestEntity = new HttpEntity<>(null, headers);
        ResponseEntity<String> responseEntity = testRestTemplate.exchange("/lessons", HttpMethod.GET, requestEntity, String.class);

        assertThat(responseEntity.getStatusCode(), equalTo(HttpStatus.OK));
        JsonArray lessonsList = new JsonParser().parse(responseEntity.getBody()).getAsJsonArray();

        JsonObject jsonObject = lessonsList.get(0).getAsJsonObject();
        assertThat(jsonObject.get("id").getAsInt(), equalTo(1));
        assertThat(jsonObject.get("title").getAsString(), equalTo("Test"));
        assertThat(jsonObject.get("deliveredOn").getAsString(), equalTo("1510054108997"));
    }
}
