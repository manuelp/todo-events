package com.github.manuelp.todoEvents.web.rest;

import fj.data.List;

import static com.github.manuelp.todoEvents.Validations.mustNotBeNull;
import static fj.P.p;

public class Resource<T> {
  private final List<Link> links;
  private final T          data;

  private Resource(List<Link> links, T data) {
    mustNotBeNull(p("Links", links), p("Data", data));
    this.links = links;
    this.data = data;
  }

  public static <T> Resource<T> resource(List<Link> links, T data) {
    return new Resource<>(links, data);
  }

  public List<Link> getLinks() {
    return links;
  }

  public T getData() {
    return data;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Resource<?> resource = (Resource<?>) o;

    if (!links.equals(resource.links)) return false;
    return data.equals(resource.data);

  }

  @Override
  public int hashCode() {
    int result = links.hashCode();
    result = 31 * result + data.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return "Resource{" +
           "links=" + links +
           ", data=" + data +
           '}';
  }
}
