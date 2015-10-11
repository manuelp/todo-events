package com.github.manuelp.todoEvents;

import com.github.manuelp.todoEvents.postgresql.RelationalEventStore;
import fj.data.Either;
import me.manuelp.jevsto.EventStore;
import me.manuelp.jevsto.dataTypes.Event;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

import java.util.UUID;

import static com.github.manuelp.todoEvents.events.Events.TODO_CREATED;

public class Main {
  public static void main(String[] args) {
    //    EventStore eventStore = new MemoryEventStore();
    EventStore eventStore = new RelationalEventStore("jdbc:h2:mem:test");
    TodoList   todoList   = new TodoList(eventStore);

    eventStore.getEvents().subscribeOn(Schedulers.io()).subscribe(printEvent());
    eventStore.getEvents().subscribeOn(Schedulers.io()).filter(e -> e.getType().equals(TODO_CREATED.getType()))
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
