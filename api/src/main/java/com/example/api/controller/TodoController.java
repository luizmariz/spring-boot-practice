package com.example.api.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.api.repository.ITodoRepository;
import com.example.api.model.Todo;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping({ "/todo" })
public class TodoController {

  @Autowired
  private ITodoRepository repository;

  @GetMapping
  public List<Todo> getAll() {
    return (List<Todo>) repository.findAll();
  }

  @PostMapping
  public ResponseEntity<Object> create(@RequestBody Todo todo) {
    repository.save(todo);
    URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(todo.getId()).toUri();

    return ResponseEntity.created(location).build();
  }

  @GetMapping({ "/{id}" })
  public @ResponseBody Todo read(@PathVariable Long id) throws ResponseStatusException {
    Optional<Todo> optionalTodo = repository.findById(id);

    if (!optionalTodo.isPresent())
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Todo not found");

    return optionalTodo.get();

  }

  @PutMapping({ "/{id}" })
  public ResponseEntity<Object> update(@PathVariable Long id, @RequestBody Todo todo) {
    Optional<Todo> optionalCurrentTodo = repository.findById(id);

    if (!optionalCurrentTodo.isPresent())
      return ResponseEntity.notFound().build();

    todo.setId(id);
    repository.save(todo);

    return ResponseEntity.noContent().build();

  }

  @DeleteMapping({ "/{id}" })
  public void delete(@PathVariable Long id) {
    repository.deleteById(id);
  }
}