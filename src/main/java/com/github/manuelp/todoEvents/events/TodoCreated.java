package com.github.manuelp.todoEvents.events;

import com.github.manuelp.todoEvents.Validations;

import java.time.LocalDateTime;
import java.util.UUID;

import static fj.P.p;
import static fj.data.List.list;

public class TodoCreated {
  private final UUID id;
  private final String title, notes;
  private final LocalDateTime created;

  private TodoCreated(UUID id, String title, String notes, LocalDateTime created) {
    Validations.mustNotBeNull(list(p("ID", id), p("Title", title), p("Notes", notes), p("Created", created)));
    this.id = id;
    this.title = title;
    this.notes = notes;
    this.created = created;
  }

  public static TodoCreated todoCreated(UUID id, String title, String notes, LocalDateTime created) {
    return new TodoCreated(id, title, notes, created);
  }

  public UUID getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public String getNotes() {
    return notes;
  }

  public LocalDateTime getCreated() {
    return created;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    TodoCreated that = (TodoCreated) o;

    if (!id.equals(that.id)) return false;
    if (!title.equals(that.title)) return false;
    if (!notes.equals(that.notes)) return false;
    return created.equals(that.created);

  }

  @Override
  public int hashCode() {
    int result = id.hashCode();
    result = 31 * result + title.hashCode();
    result = 31 * result + notes.hashCode();
    result = 31 * result + created.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return "TodoCreated{" +
           "id=" + id +
           ", title='" + title + '\'' +
           ", notes='" + notes + '\'' +
           ", created=" + created +
           '}';
  }
}
