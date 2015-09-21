package com.github.manuelp.todoEvents.events;

import com.github.manuelp.todoEvents.Validations;

import java.time.LocalDateTime;
import java.util.UUID;

import static fj.P.p;
import static fj.data.List.list;

public class TodoCompleted {
  private final UUID id;
  private final LocalDateTime completed;

  private TodoCompleted(UUID id, LocalDateTime completed) {
    Validations.mustNotBeNull(list(p("ID", id), p("Completed", completed)));
    this.id = id;
    this.completed = completed;
  }

  public static TodoCompleted todoCompleted(UUID id, LocalDateTime completed) {
    return new TodoCompleted(id, completed);
  }

  public UUID getId() {
    return id;
  }

  public LocalDateTime getCompleted() {
    return completed;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    TodoCompleted that = (TodoCompleted) o;

    if (!id.equals(that.id)) return false;
    return completed.equals(that.completed);

  }

  @Override
  public int hashCode() {
    int result = id.hashCode();
    result = 31 * result + completed.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return "TodoCompleted{" +
           "id=" + id +
           ", completed=" + completed +
           '}';
  }
}
