package com.example.demo.domain.repository;


import com.example.demo.domain.entity.Lesson;
import org.springframework.data.repository.CrudRepository;

public interface LessonRepository extends CrudRepository<Lesson, Long> {

}
