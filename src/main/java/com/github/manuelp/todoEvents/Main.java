package com.github.manuelp.todoEvents;

import com.github.manuelp.todoEvents.commands.TodoListCommands;
import com.github.manuelp.todoEvents.events.postgresql.RelationalEventStore;
import com.github.manuelp.todoEvents.listView.CompleteTodo;
import com.github.manuelp.todoEvents.listView.CreateTodo;
import com.github.manuelp.todoEvents.listView.ListView;
import com.github.manuelp.todoEvents.listView.UpdateTodo;
import com.github.manuelp.todoEvents.web.Server;
import me.manuelp.jevsto.EventStore;
import me.manuelp.jevsto.dataTypes.Event;
import org.skife.jdbi.v2.DBI;
import rx.Observable;

import static com.github.manuelp.todoEvents.events.Events.*;

public class Main {

  public static void main(String[] args) {
    DBI dbi = new DBI("jdbc:postgresql://localhost:5432/eventstore?user=eventstore&password=eventstore");

    EventStore eventStore = new RelationalEventStore(dbi);
    wireObservers(eventStore, dbi);

    TodoListCommands todoList = new TodoListCommands(eventStore);
    //    todoList.addTodo("Order pizza", "Today is pizza day!");
    //    todoList.updateTodo(UUID.fromString("231a34f2-b9e6-47fd-8fff-1a43e2ed959c"), "New title", "Enjoy your editing!");
    //    todoList.markCompleted(UUID.fromString("231a34f2-b9e6-47fd-8fff-1a43e2ed959c"));

    ListView listView = new ListView(dbi);
    new Server(listView).startServer();
  }

  private static void wireObservers(EventStore eventStore, DBI dbi) {
    Observable<Event> stream = eventStore.getEvents();
    stream.filter(Event.isOfType(TODO_CREATED.getType())).map(e -> TODO_CREATED.reader().f(e)).subscribe(new CreateTodo(
        dbi));
    stream.filter(Event.isOfType(TODO_UPDATED.getType())).map(e -> TODO_UPDATED.reader().f(e)).subscribe(new UpdateTodo(
        dbi));
    stream.filter(Event.isOfType(TODO_COMPLETED.getType())).map(e -> TODO_COMPLETED.reader().f(e)).subscribe(
        new CompleteTodo(dbi));
  }
}
