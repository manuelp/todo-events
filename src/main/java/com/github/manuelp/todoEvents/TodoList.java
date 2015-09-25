package com.github.manuelp.todoEvents;

import com.github.manuelp.todoEvents.events.TodoCreated;
import fj.Unit;
import fj.data.Either;
import me.manuelp.jevsto.EventStore;

import java.util.UUID;

import static com.github.manuelp.todoEvents.events.Events.TODO_CREATED;
import static com.github.manuelp.todoEvents.events.TodoCreated.todoCreated;
import static fj.data.Either.right;

public class TodoList implements BasicTodoList {
  private EventStore eventStore;

  public TodoList(EventStore eventStore) {
    Validations.notNull("EventStore", eventStore);
    this.eventStore = eventStore;
  }

  @Override
  public Either<Throwable, UUID> addTodo(String title, String note) {
    TodoCreated event = todoCreated(UUID.randomUUID(), title, note, java.time.LocalDateTime.now());
    eventStore.append(TODO_CREATED.writer().f(event));
    return right(event.getId());
  }


  @Override
  public Either<Throwable, Unit> updateTodo(UUID id, String title, String note) {
    return Either.left(new UnsupportedOperationException());
  }

  @Override
  public Either<Throwable, Unit> markCompleted(UUID id) {
    return Either.left(new UnsupportedOperationException());
  }
}
