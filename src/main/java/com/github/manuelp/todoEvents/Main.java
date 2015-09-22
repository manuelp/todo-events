package com.github.manuelp.todoEvents;

import fj.data.Either;
import me.manuelp.jevsto.EventStore;
import me.manuelp.jevsto.inMemory.MemoryEventStore;
import rx.schedulers.Schedulers;

import java.util.UUID;

public class Main {
  public static void main(String[] args) {
    System.out.println("Hello, World!");

    EventStore eventStore = new MemoryEventStore();
    TodoList   todoList   = new TodoList(eventStore);
    eventStore.getEvents().subscribeOn(Schedulers.io()).subscribe(e -> System.out.println(
        "-> " + Thread.currentThread().toString() +
        ":" + e.toString()));

    Either<Throwable, UUID> res = todoList.addTodo("Test this", "This must be tested thoroughly.");
    System.out.println("Received: " + res);
  }
}
