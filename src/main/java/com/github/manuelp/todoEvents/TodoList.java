package com.github.manuelp.todoEvents;

import com.github.manuelp.todoEvents.events.TodoCreated;
import com.github.manuelp.todoEvents.events.avro.writers.TodoCreatedWriter;
import fj.Unit;
import fj.data.Either;
import me.manuelp.jevsto.EventStore;
import me.manuelp.jevsto.dataTypes.Event;
import me.manuelp.jevsto.dataTypes.EventData;
import me.manuelp.jevsto.dataTypes.EventType;
import org.threeten.bp.LocalDateTime;

import java.util.UUID;

import static com.github.manuelp.todoEvents.events.TodoCreated.todoCreated;

public class TodoList implements BasicTodoList {
  private EventStore eventStore;

  public TodoList(EventStore eventStore) {
    Validations.notNull("EventStore", eventStore);
    this.eventStore = eventStore;
  }

  @Override
  public Either<Throwable, UUID> addTodo(String title, String note) {
    TodoCreated event     = todoCreated(UUID.randomUUID(), title, note, java.time.LocalDateTime.now());
    EventData   eventData = new TodoCreatedWriter("TodoCreated.avsc").f(event);
    Event       test      = Event.event(LocalDateTime.now(), EventType.eventType("test"), eventData);
    eventStore.append(test);
    return Either.right(event.getId());
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
