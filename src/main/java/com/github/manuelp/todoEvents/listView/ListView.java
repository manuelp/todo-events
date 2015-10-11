package com.github.manuelp.todoEvents.listView;

import fj.data.List;
import fj.data.Option;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

import static com.github.manuelp.todoEvents.listView.TodoItem.todoItem;
import static fj.data.List.iterableList;
import static fj.data.Option.fromNull;

public class ListView {
  private static final String SELECT_ITEM = "SELECT id,title,notes,complete,created,updated,completed FROM list_view";
  private DBI dbi;

  public ListView(DBI dbi) {
    this.dbi = dbi;
  }

  public List<TodoItem> notCompleted() {
    return iterableList(dbi.withHandle(h -> h.createQuery(SELECT_ITEM + " WHERE complete IS FALSE").map(readItem())
                                             .list()));
  }

  private ResultSetMapper<TodoItem> readItem() {
    return (index, r, ctx) -> {
      UUID id = UUID.fromString(r.getString("id"));
      String title = r.getString("title");
      Option<String> notes = fromNull(r.getString("notes"));
      boolean complete = r.getBoolean("complete");
      LocalDateTime created = r.getTimestamp("created").toLocalDateTime();
      Option<LocalDateTime> updated = fromNull(r.getTimestamp("updated")).map(Timestamp::toLocalDateTime);
      Option<LocalDateTime> completed = fromNull(r.getTimestamp("completed")).map(Timestamp::toLocalDateTime);
      return todoItem(id, title, notes, complete, created, updated, completed);
    };
  }
}
