package com.github.manuelp.todoEvents;

import com.github.manuelp.todoEvents.events.postgresql.RelationalEventStore;
import com.github.manuelp.todoEvents.web.Server;
import me.manuelp.jevsto.EventStore;

import java.util.UUID;

public class Main {

  public static void main(String[] args) {
    EventStore eventStore = new RelationalEventStore(
        "jdbc:postgresql://localhost:5432/eventstore?user=eventstore&password=eventstore");
    TodoListCommands todoList = new TodoListCommands(eventStore);

    UUID id = todoList.addTodo("Test", "Some interesting text");
    System.out.println("Added: " + id.toString());

    todoList.updateTodo(id, "New test", "Let's update this thing!");
    System.out.println("Updated: " + id.toString());

    todoList.markCompleted(id);
    System.out.println("Marked complete: " + id.toString());

    new Server().startServer(eventStore);
  }


}
