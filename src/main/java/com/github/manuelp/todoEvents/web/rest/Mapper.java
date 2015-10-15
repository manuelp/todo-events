package com.github.manuelp.todoEvents.web.rest;

import com.google.gson.JsonObject;
import fj.F;
import fj.F2;

public class Mapper {
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
}
