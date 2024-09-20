package com.example.api.server.service;

import com.example.api.todo.api.v1.model.Todo;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
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
          .startTime(
              LocalDate.now()
                  .plusDays(-7)
                  .atStartOfDay()
                  .atOffset(ZoneOffset.UTC))
          .deadline(
              LocalDate.now()
                  .plusDays(7)
                  .atStartOfDay()
                  .atOffset(ZoneOffset.UTC))
      ).toList();

  public List<Todo> getTodos() {
    return todos;
  }
}
