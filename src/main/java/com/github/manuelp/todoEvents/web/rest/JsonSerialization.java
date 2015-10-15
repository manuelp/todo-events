package com.github.manuelp.todoEvents.web.rest;

import com.github.manuelp.todoEvents.listView.TodoItem;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import fj.F;
import fj.F2;
import fj.data.List;

import java.time.format.DateTimeFormatter;

import static fj.F2Functions.tuple;
import static fj.data.List.replicate;

public class JsonSerialization {
  public static <T> F<Resource<T>, JsonObject> toJson(F2<JsonObject, T, JsonObject> dataToJson) {
    return r -> {
      JsonObject o = new JsonObject();

      JsonObject links = r.getLinks().foldLeft(obj -> l -> {
        JsonObject link = new JsonObject();
        link.addProperty("href", l.getHref().toString());
        obj.add(l.getRel(), link);
        return obj;
      }, new JsonObject());
      o.add("_links", links);

      return dataToJson.f(o, r.getData());
    };
  }

  public static F2<JsonObject, TodoItem, JsonObject> jsonTodoItem() {
    return (o, x) -> {
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

  public static F<Resource<List<TodoItem>>, JsonObject> itemsListToJson() {
    return toJson((JsonObject o, List<TodoItem> s) -> {
      List<JsonObject> jsonItems = replicate(s.length(), new JsonObject()).zip(s).map(tuple(jsonTodoItem()));
      o.add("items", jsonItems.foldLeft((JsonArray arr, JsonObject obj) -> {
        arr.add(obj);
        return arr;
      }, new JsonArray()));
      return o;
    });
  }
}
