package com.example.api.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import com.example.api.model.Todo;

public interface ITodoRepository extends CrudRepository<Todo, Long> {

  List<Todo> findByTitle(String title);

  Todo findById(long id);
}