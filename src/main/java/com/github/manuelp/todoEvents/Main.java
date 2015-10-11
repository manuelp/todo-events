package com.github.manuelp.todoEvents;

import com.github.manuelp.todoEvents.postgresql.RelationalEventStore;
import com.github.manuelp.todoEvents.web.Server;
import me.manuelp.jevsto.EventStore;

public class Main {

  public static void main(String[] args) {
    EventStore eventStore = new RelationalEventStore(
        "jdbc:postgresql://localhost:5432/eventstore?user=eventstore&password=eventstore");
    TodoList todoList = new TodoList(eventStore);

    new Server().startServer(eventStore);
  }


}
