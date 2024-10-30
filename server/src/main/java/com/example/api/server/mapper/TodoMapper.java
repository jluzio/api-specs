package com.example.api.server.mapper;

import com.example.api.todo.api.v1.model.Todo;
import com.example.api.todo.api.v1.model.TodoCreateRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TodoMapper {

  Todo mapTodo(TodoCreateRequest todoCreateRequest);

}
