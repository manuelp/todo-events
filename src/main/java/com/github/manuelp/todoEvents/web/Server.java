package com.github.manuelp.todoEvents.web;

import com.github.manuelp.todoEvents.listView.ListView;
import com.github.manuelp.todoEvents.listView.TodoItem;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializer;
import fj.data.List;

import java.time.format.DateTimeFormatter;

import static spark.Spark.get;

public class Server {
  private ListView listView;
  private Gson     gson;

  public Server(ListView listView) {
    this.listView = listView;
    this.gson = new GsonBuilder().registerTypeAdapter(TodoItem.class, todoItemSerializer()).create();
  }

  private JsonSerializer<TodoItem> todoItemSerializer() {
    return (x, t, c) -> {
      JsonObject o = new JsonObject();
      o.addProperty("id", x.getId().toString());
      o.addProperty("title", x.getTitle());
      if (x.getNotes().isSome()) o.addProperty("notes", x.getNotes().some());
      o.addProperty("complete", x.isComplete());
      o.addProperty("created", x.getCreated().format(DateTimeFormatter.ISO_DATE_TIME));
      if (x.getUpdated().isSome()) o.addProperty("updated", x.getUpdated().some().format(
          DateTimeFormatter.ISO_DATE_TIME));
      if (x.getCompleted().isSome()) o.addProperty("completed", x.getCompleted().some().format(
          DateTimeFormatter.ISO_DATE_TIME));
      return o;
    };
  }

  public void startServer() {
    get("/todo", (req, res) -> {
      res.type("application/json");
      List<TodoItem> items = listView.notCompleted();
      return gson.toJson(items.toJavaList());
    });
  }
}
