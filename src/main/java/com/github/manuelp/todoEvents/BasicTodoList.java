package com.github.manuelp.todoEvents;

import fj.Unit;
import fj.data.Either;

import java.util.UUID;

public interface BasicTodoList {
  Either<Throwable, UUID> addTodo(String title, String note);

  Either<Throwable, Unit> updateTodo(UUID id, String title, String note);

  Either<Throwable, Unit> markCompleted(UUID id);
}
