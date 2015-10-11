package com.github.manuelp.todoEvents.commands;

import com.github.manuelp.todoEvents.Validations;
import com.github.manuelp.todoEvents.events.TodoCompleted;
import com.github.manuelp.todoEvents.events.TodoCreated;
import com.github.manuelp.todoEvents.events.TodoUpdated;
import me.manuelp.jevsto.EventStore;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.github.manuelp.todoEvents.events.Events.*;
import static com.github.manuelp.todoEvents.events.TodoCompleted.todoCompleted;
import static com.github.manuelp.todoEvents.events.TodoCreated.todoCreated;
import static com.github.manuelp.todoEvents.events.TodoUpdated.todoUpdated;

public class TodoListCommands implements BasicTodoListCommands {
  private EventStore eventStore;

  public TodoListCommands(EventStore eventStore) {
    Validations.notNull("EventStore", eventStore);
    this.eventStore = eventStore;
  }

  @Override
  public UUID addTodo(String title, String note) {
    TodoCreated event = todoCreated(UUID.randomUUID(), title, note, java.time.LocalDateTime.now());
    eventStore.append(TODO_CREATED.writer().f(event));
    return event.getId();
  }


  @Override
  public void updateTodo(UUID id, String title, String note) {
    TodoUpdated event = todoUpdated(id, title, note);
    eventStore.append(TODO_UPDATED.writer().f(event));
  }

  @Override
  public void markCompleted(UUID id) {
    TodoCompleted event = todoCompleted(id, LocalDateTime.now());
    eventStore.append(TODO_COMPLETED.writer().f(event));
  }
}
