package com.example.api.server.api;

import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.StringUtils.contains;

import com.example.api.server.service.TodoService;
import com.example.api.todo.api.v1.TodoApi;
import com.example.api.todo.api.v1.model.Todo;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequiredArgsConstructor
public class TodoResource implements TodoApi {

  private final TodoService todoService;

  @Override
  public ResponseEntity<List<Todo>> getTodos(List<Integer> ids, String title, Boolean completed) {
    Predicate<Todo> filter = todo -> true;
    if (nonNull(ids)) {
      filter = filter.and(todo -> ids.contains(todo.getId()));
    }
    if (nonNull(title)) {
      filter = filter.and(todo -> contains(todo.getTitle(), title));
    }
    if (nonNull(completed)) {
      filter = filter.and(todo -> Objects.equals(todo.getCompleted(), completed));
    }
    return ResponseEntity.ok(
        todoService.getTodos().stream()
            .filter(filter)
            .toList()
    );
  }

  @Override
  public ResponseEntity<Todo> getTodo(Integer id) {
    return todoService.getTodos().stream()
        .filter(Function.<Todo>identity()
            .andThen(Todo::getId)
            .andThen(id::equals)
            ::apply)
        .findFirst()
        .map(ResponseEntity::ok)
        .orElseThrow(() -> new ResponseStatusException(
            HttpStatusCode.valueOf(404),
            "Todo %s not found'".formatted(id)));
  }

}
