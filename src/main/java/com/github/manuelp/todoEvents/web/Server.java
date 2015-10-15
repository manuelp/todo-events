package com.github.manuelp.todoEvents.web;

import com.github.manuelp.todoEvents.listView.ListView;
import com.github.manuelp.todoEvents.listView.TodoItem;
import com.github.manuelp.todoEvents.web.rest.JsonSerialization;
import com.github.manuelp.todoEvents.web.rest.Link;
import com.github.manuelp.todoEvents.web.rest.Resource;
import com.google.gson.Gson;
import fj.data.List;

import java.net.URI;

import static com.github.manuelp.todoEvents.web.rest.Link.link;
import static com.github.manuelp.todoEvents.web.rest.Resource.resource;
import static fj.data.List.list;
import static spark.Spark.get;

public class Server {
  public static final String HAL_CONTENT_TYPE = "application/hal+json";
  private ListView listView;

  public Server(ListView listView) {
    this.listView = listView;
  }

  private Gson gson() {
    return new Gson();
  }

  public void startServer() {
    get("/todo", (req, res) -> {
      res.type(HAL_CONTENT_TYPE);
      List<Link> links = list(link("self", URI.create("/todo")), link("completed", URI.create("/todo/completed")));
      Resource<List<TodoItem>> r = resource(links, listView.notCompleted());
      return gson().toJson(JsonSerialization.itemsListToJson().f(r));
    });
    get("/todo/completed", (req, res) -> {
      res.type(HAL_CONTENT_TYPE);
      List<Link> links = list(link("self", URI.create("/todo/completed")), link("open", URI.create("/todo")));
      Resource<List<TodoItem>> r = resource(links, listView.completed());
      return gson().toJson(JsonSerialization.itemsListToJson().f(r));
    });
  }

}
