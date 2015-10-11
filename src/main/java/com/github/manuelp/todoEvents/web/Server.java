package com.github.manuelp.todoEvents.web;

import com.github.manuelp.todoEvents.events.TodoCreated;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializer;
import fj.data.List;
import me.manuelp.jevsto.EventStore;
import me.manuelp.jevsto.dataTypes.Event;

import static com.github.manuelp.todoEvents.events.Events.TODO_CREATED;
import static spark.Spark.get;

public class Server {
  private static Gson gson;

  public Server() {
    gson = new GsonBuilder().registerTypeAdapter(TodoCreated.class, todoCreatedSerializer()).create();
  }

  public void startServer(EventStore eventStore) {
    get("/todo", (req, res) -> {
      res.type("application/json");
      List<Event> events = eventStore.getAll().filter(e -> Event.isOfType(TODO_CREATED.getType()).call(e));
      return gson.toJson(events.map(TODO_CREATED.reader()).toJavaList());
    });
  }

  private JsonSerializer<TodoCreated> todoCreatedSerializer() {
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
