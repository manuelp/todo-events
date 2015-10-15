package com.github.manuelp.todoEvents.web.rest;

import java.net.URI;

import static com.github.manuelp.todoEvents.Validations.mustNotBeNull;
import static fj.P.p;

public class Link {
  private final String rel;
  private final URI    href;

  private Link(String rel, URI href) {
    mustNotBeNull(p("rel", rel), p("href", href));
    this.rel = rel;
    this.href = href;
  }

  public static Link link(String rel, URI href) {
    return new Link(rel, href);
  }

  public String getRel() {
    return rel;
  }

  public URI getHref() {
    return href;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Link link = (Link) o;

    if (!rel.equals(link.rel)) return false;
    return href.equals(link.href);

  }

  @Override
  public int hashCode() {
    int result = rel.hashCode();
    result = 31 * result + href.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return "Link{" +
           "rel='" + rel + '\'' +
           ", href=" + href +
           '}';
  }
}
