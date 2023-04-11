package com.example.api.server.api;

import com.example.api.server.service.TodoService;
import com.example.api.todo.api.v1.TodoApi;
import com.example.api.todo.api.v1.model.Todo;
import java.util.ArrayList;
import java.util.List;
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
  public ResponseEntity<List<Todo>> getTodos(Integer userId, Integer id, String title,
      Boolean completed) {
    List<Predicate<Todo>> predicates = new ArrayList<>();
//    Function<Todo, Todo> identity = Function.identity();
//    ofNullable(userId)
//        .map(identity.andThen(Todo::userId).andThen())
//    if (userId != null) {
//      todoPredicate = todoPredicate.and()
//    }
    Predicate<Todo> filter = todo -> true;
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
