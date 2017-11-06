package com.example.demo.controller;

import com.example.demo.domain.entity.Lesson;
import com.example.demo.domain.repository.LessonRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/lessons")
public class LessonsController {

    private final LessonRepository repository;

    public LessonsController(LessonRepository repository) {
        this.repository = repository;
    }

    @GetMapping("")
    public Iterable<Lesson> all() {
        return this.repository.findAll();
    }

    @PostMapping("")
    public Lesson create(@RequestBody Lesson lesson) {
        return this.repository.save(lesson);
    }

    @PutMapping("")
    public Lesson update(@RequestBody Lesson lesson) throws InvalidIdException {
        if (this.repository.findOne(lesson.getId()) != null) {
            return this.repository.save(lesson);
        }
        else {
            throw new InvalidIdException("ID doesn't exost!");
        }
    }

}