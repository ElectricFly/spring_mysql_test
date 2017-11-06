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

    @GetMapping("{id}")
    public Lesson getOne(@PathVariable Long id) {
        return this.repository.findOne(id);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id) throws InvalidIdException {
        if (this.repository.findOne(id) != null) {
            this.repository.delete(id);
        }
        else {
            throw new InvalidIdException("ID doesn't exist!");
        }
    }

    @PostMapping("")
    public Lesson create(@RequestBody Lesson lesson) {
        return this.repository.save(lesson);
    }

    @PutMapping("{id}")
    public Lesson update(@RequestBody Lesson lesson, @PathVariable Long id) throws InvalidIdException {
        if (this.repository.findOne(id) != null) {
            lesson.setId(id);
            return this.repository.save(lesson);
        }
        else {
            throw new InvalidIdException("ID doesn't exist!");
        }
    }


}