package com.example.api.server.service;

import com.example.api.todo.api.v1.model.Todo;
import java.util.List;
import java.util.stream.IntStream;
import org.springframework.stereotype.Service;

@Service
public class TodoService {

  private final List<Todo> todos = IntStream.rangeClosed(1, 10)
      .mapToObj(id -> new Todo()
          .id(id)
          .title("todo-%s".formatted(id))
          .userId(id)
          .completed(false)
      ).toList();

  public List<Todo> getTodos() {
    return todos;
  }
}
