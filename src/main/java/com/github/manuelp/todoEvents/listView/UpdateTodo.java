package com.github.manuelp.todoEvents.listView;

import com.github.manuelp.todoEvents.events.TodoUpdated;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.skife.jdbi.v2.DBI;
import rx.Observer;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class UpdateTodo implements Observer<TodoUpdated> {
  private static final Logger logger = LogManager.getLogger(UpdateTodo.class);
  private DBI dbi;

  public UpdateTodo(DBI dbi) {
    this.dbi = dbi;
  }

  @Override
  public void onCompleted() {

  }

  @Override
  public void onError(Throwable e) {

  }

  @Override
  public void onNext(TodoUpdated todoUpdated) {
    logger.debug("Processing: " + todoUpdated.toString());
    dbi.withHandle(h -> {
      h.createStatement("UPDATE list_view SET title=:title,notes=:notes,updated=:updated WHERE id=:id").bind("id",
                                                                                                             todoUpdated
                                                                                                                 .getId())
       .bind("title", todoUpdated.getTitle()).bind("notes", todoUpdated.getNotes()).bind("updated", Timestamp.valueOf(
          LocalDateTime.now())).execute();
      return null;
    });
  }
}
