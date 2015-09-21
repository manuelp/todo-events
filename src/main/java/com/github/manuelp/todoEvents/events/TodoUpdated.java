package com.github.manuelp.todoEvents.events;

import com.github.manuelp.todoEvents.Validations;

import java.util.UUID;

import static fj.P.p;
import static fj.data.List.list;

public class TodoUpdated {
  private final UUID id;
  private final String title, notes;

  private TodoUpdated(UUID id, String title, String notes) {
    Validations.mustNotBeNull(list(p("ID", id), p("Title", title), p("Notes", notes)));
    this.id = id;
    this.title = title;
    this.notes = notes;
  }

  public static TodoUpdated todoCreated(UUID id, String title, String notes) {
    return new TodoUpdated(id, title, notes);
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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    TodoUpdated that = (TodoUpdated) o;

    if (!id.equals(that.id)) return false;
    if (!title.equals(that.title)) return false;
    return notes.equals(that.notes);

  }

  @Override
  public int hashCode() {
    int result = id.hashCode();
    result = 31 * result + title.hashCode();
    result = 31 * result + notes.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return "TodoUpdated{" +
           "id=" + id +
           ", title='" + title + '\'' +
           ", notes='" + notes + '\'' +
           '}';
  }
}
