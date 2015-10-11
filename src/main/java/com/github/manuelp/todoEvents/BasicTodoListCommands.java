package com.github.manuelp.todoEvents;

import java.util.UUID;

public interface BasicTodoListCommands {
  UUID addTodo(String title, String note);

  void updateTodo(UUID id, String title, String note);

  void markCompleted(UUID id);
}
