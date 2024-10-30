package com.example.api.server.service;

import com.example.api.server.mapper.TodoMapper;
import com.example.api.todo.api.v1.model.Todo;
import com.example.api.todo.api.v1.model.TodoCreateRequest;
import jakarta.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TodoService {

  private final AtomicInteger idCounter = new AtomicInteger();
  private final Supplier<Integer> idGenerator = idCounter::incrementAndGet;
  private final TodoMapper mapper;
  private final List<Todo> todos = new ArrayList<>();

  @PostConstruct
  public void init() {
    IntStream.rangeClosed(1, 10)
        .map(n -> idGenerator.get())
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
        ).forEach(todos::add);
  }

  public List<Todo> getTodos() {
    return todos;
  }

  public Todo createTodo(TodoCreateRequest request) {
    Todo todo = mapper.mapTodo(request);
    todo.setId(idGenerator.get());
    todos.add(todo);
    return todo;
  }
}
