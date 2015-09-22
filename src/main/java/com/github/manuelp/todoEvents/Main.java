package com.github.manuelp.todoEvents;

import com.github.manuelp.todoEvents.events.avro.readers.TodoCreatedReader;
import fj.data.Either;
import me.manuelp.jevsto.EventStore;
import me.manuelp.jevsto.dataTypes.Event;
import me.manuelp.jevsto.inMemory.MemoryEventStore;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

import java.util.UUID;

import static me.manuelp.jevsto.dataTypes.EventType.eventType;

public class Main {
  public static void main(String[] args) {
    System.out.println("Hello, World!");

    EventStore eventStore = new MemoryEventStore();
    TodoList   todoList   = new TodoList(eventStore);
    eventStore.getEvents().subscribeOn(Schedulers.io()).subscribe(printEvent());
    eventStore.getEvents().subscribeOn(Schedulers.io()).filter(e -> e.getType().equals(eventType("todoCreated")))
              .subscribe(printDeserializedEvent());

    Either<Throwable, UUID> res = todoList.addTodo("Test this", "This must be tested thoroughly.");
    System.out.println("Received: " + res);
  }

  private static Action1<Event> printEvent() {
    return e -> System.out.println(String.format("Serialized -> %s", e.toString()));
  }

  private static Action1<Event> printDeserializedEvent() {
    return e -> {
      TodoCreatedReader reader = new TodoCreatedReader("TodoCreated.avsc");
      System.out.println(String.format("Deserialized -> %s", reader.f(e.getData())));
    };
  }
}
