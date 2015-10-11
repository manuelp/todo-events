package com.github.manuelp.todoEvents.listView;

import com.github.manuelp.todoEvents.Validations;
import fj.data.Option;

import java.time.LocalDateTime;
import java.util.UUID;

import static fj.P.p;

public class TodoItem {
  private final UUID                  id;
  private final String                title;
  private final Option<String>        notes;
  private final boolean               complete;
  private final LocalDateTime         created;
  private final Option<LocalDateTime> updated, completed;

  private TodoItem(UUID id, String title, Option<String> notes, boolean complete, LocalDateTime created,
                   Option<LocalDateTime> updated, Option<LocalDateTime> completed) {
    Validations.mustNotBeNull(p("id", id), p("Title", title), p("Notes", notes), p("created", created), p("Updated",
                                                                                                          updated), p(
        "Completed", completed));
    this.id = id;
    this.title = title;
    this.notes = notes;
    this.complete = complete;
    this.created = created;
    this.updated = updated;
    this.completed = completed;
  }

  public static TodoItem todoItem(UUID id, String title, Option<String> notes, boolean complete, LocalDateTime created,
                                  Option<LocalDateTime> updated, Option<LocalDateTime> completed) {
    return new TodoItem(id, title, notes, complete, created, updated, completed);
  }

  public UUID getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public Option<String> getNotes() {
    return notes;
  }

  public boolean isComplete() {
    return complete;
  }

  public LocalDateTime getCreated() {
    return created;
  }

  public Option<LocalDateTime> getUpdated() {
    return updated;
  }

  public Option<LocalDateTime> getCompleted() {
    return completed;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    TodoItem todoItem = (TodoItem) o;

    if (complete != todoItem.complete) return false;
    if (!id.equals(todoItem.id)) return false;
    if (!title.equals(todoItem.title)) return false;
    if (!notes.equals(todoItem.notes)) return false;
    if (!created.equals(todoItem.created)) return false;
    if (!updated.equals(todoItem.updated)) return false;
    return completed.equals(todoItem.completed);

  }

  @Override
  public int hashCode() {
    int result = id.hashCode();
    result = 31 * result + title.hashCode();
    result = 31 * result + notes.hashCode();
    result = 31 * result + (complete ? 1 : 0);
    result = 31 * result + created.hashCode();
    result = 31 * result + updated.hashCode();
    result = 31 * result + completed.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return "TodoItem{" +
           "id=" + id +
           ", title='" + title + '\'' +
           ", notes=" + notes +
           ", complete=" + complete +
           ", created=" + created +
           ", updated=" + updated +
           ", completed=" + completed +
           '}';
  }
}
