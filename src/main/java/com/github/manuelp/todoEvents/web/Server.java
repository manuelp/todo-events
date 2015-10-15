package com.github.manuelp.todoEvents.web;

import com.github.manuelp.todoEvents.listView.ListView;
import com.github.manuelp.todoEvents.listView.TodoItem;
import com.github.manuelp.todoEvents.web.rest.Mapper;
import com.github.manuelp.todoEvents.web.rest.Resource;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializer;

import java.net.URI;
import java.time.format.DateTimeFormatter;

import static com.github.manuelp.todoEvents.web.rest.Link.link;
import static fj.data.List.list;
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
      return gson.toJson(listView.notCompleted().toJavaList());
    });
    get("/todo/completed", (req, res) -> {
      res.type("application/json");
      return gson.toJson(listView.completed().toJavaList());
    });

    get("/test", (req, res) -> {
      res.type("application/json");
      Resource<String> resource = Resource.resource(list(link("_self", URI.create("/test"))), "Hello!");
      return gson.toJson(Mapper.toJson((JsonObject o, String s) -> {
        o.addProperty("LOL", s);
        return o;
      }).f(resource));
    });
  }
}
