package com.github.manuelp.todoEvents;

import fj.data.Either;
import me.manuelp.jevsto.EventStore;
import me.manuelp.jevsto.dataTypes.Event;
import me.manuelp.jevsto.inMemory.MemoryEventStore;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

import java.util.UUID;

import static com.github.manuelp.todoEvents.events.Events.TODO_CREATED;
import static me.manuelp.jevsto.dataTypes.EventType.eventType;

public class Main {
  public static void main(String[] args) {
    EventStore eventStore = new MemoryEventStore();
    TodoList   todoList   = new TodoList(eventStore);

    eventStore.getEvents().subscribeOn(Schedulers.io()).subscribe(printEvent());
    eventStore.getEvents().subscribeOn(Schedulers.io()).filter(e -> e.getType().equals(eventType("todoCreated")))
              .subscribe(printDeserializedEvent());

    Either<Throwable, UUID> res = todoList.addTodo("Test this", "This must be tested thoroughly.");
    System.out.println("Created todo item result: " + res);
  }

  private static Action1<Event> printEvent() {
    return e -> System.out.println(String.format("Serialized -> %s (data size: %d bytes)", e.toString(),
                                                 e.getData().getData().length));
  }

  private static Action1<Event> printDeserializedEvent() {
    return e -> System.out.println(String.format("Deserialized -> %s", TODO_CREATED.reader().f(e)));
  }
}
