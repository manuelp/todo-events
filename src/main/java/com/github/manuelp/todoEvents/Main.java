package com.github.manuelp.todoEvents;

import com.github.manuelp.todoEvents.postgresql.RelationalEventStore;
import me.manuelp.jevsto.EventStore;
import me.manuelp.jevsto.dataTypes.Event;
import rx.functions.Action1;

import static com.github.manuelp.todoEvents.events.Events.TODO_CREATED;

public class Main {
  public static void main(String[] args) {
    //    EventStore eventStore = new MemoryEventStore();
    EventStore eventStore = new RelationalEventStore(
        "jdbc:postgresql://localhost:5432/eventstore?user=eventstore&password=eventstore");
    TodoList todoList = new TodoList(eventStore);

    //    eventStore.getEvents().subscribeOn(Schedulers.io()).subscribe(printEvent());
    //    eventStore.getEvents().subscribeOn(Schedulers.io()).filter(e -> e.getType().equals(TODO_CREATED.getType()))
    //              .subscribe(printDeserializedEvent());
    //
    //    Either<Throwable, UUID> res = todoList.addTodo("Test this", "This must be tested thoroughly.");
    //    System.out.println("Created todo item result: " + res);

    //    Option<Event> res = eventStore.getById(UUID.fromString("2f58d8d2-3e85-44b0-9e7d-46a5687cb9e1"));
    //    Observable.just(res.some()).subscribe(printDeserializedEvent());

    eventStore.getAllEvents().subscribe(printDeserializedEvent());

    //    eventStore.getAllEventsFrom(LocalDateTime.now()).subscribe(printDeserializedEvent());
  }

  private static Action1<Event> printEvent() {
    return e -> System.out.println(String.format("Serialized -> %s (data size: %d bytes)", e.toString(),
                                                 e.getData().getData().length));
  }

  private static Action1<Event> printDeserializedEvent() {
    return e -> System.out.println(String.format("Deserialized -> %s", TODO_CREATED.reader().f(e)));
  }
}
