package com.github.manuelp.todoEvents;

import com.github.manuelp.todoEvents.events.TodoCreated;
import com.github.manuelp.todoEvents.postgresql.RelationalEventStore;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializer;
import fj.data.List;
import me.manuelp.jevsto.EventStore;
import me.manuelp.jevsto.dataTypes.Event;
import spark.Spark;

import static com.github.manuelp.todoEvents.events.Events.TODO_CREATED;

public class Main {
  private static Gson gson;

  public static void main(String[] args) {
    EventStore eventStore = new RelationalEventStore(
        "jdbc:postgresql://localhost:5432/eventstore?user=eventstore&password=eventstore");
    TodoList todoList = new TodoList(eventStore);

    gson = new GsonBuilder().registerTypeAdapter(TodoCreated.class, todoCreatedSerializer()).create();
    Spark.get("/created", (req, res) -> {
      res.type("application/json");
      List<Event> events = eventStore.getAll().filter(e -> Event.isOfType(TODO_CREATED.getType()).call(e));
      return gson.toJson(events.map(TODO_CREATED.reader()).toJavaList());
    });
  }

  private static JsonSerializer<TodoCreated> todoCreatedSerializer() {
    return (x, t, c) -> {
      JsonObject data = new JsonObject();
      data.addProperty("id", x.getId().toString());
      data.addProperty("created", x.getCreated().format(java.time.format.DateTimeFormatter.ISO_DATE_TIME));
      data.addProperty("title", x.getTitle());
      data.addProperty("notes", x.getNotes());
      return data;
    };
  }
}
