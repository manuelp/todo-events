package com.github.manuelp.todoEvents.listView;

import com.github.manuelp.todoEvents.events.TodoCreated;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.skife.jdbi.v2.DBI;
import rx.Observer;

import java.sql.Timestamp;

public class CreateTodo implements Observer<TodoCreated> {
  private static final Logger logger = LogManager.getLogger(CreateTodo.class);

  private DBI dbi;

  public CreateTodo(DBI dbi) {
    this.dbi = dbi;
  }

  @Override
  public void onCompleted() {

  }

  @Override
  public void onError(Throwable e) {

  }

  @Override
  public void onNext(TodoCreated todoCreated) {
    logger.debug("Processing: " + todoCreated.toString());
    dbi.withHandle(h -> {
      h.createStatement("INSERT INTO list_view (id, title, notes, created) VALUES (:id,:title,:notes,:created)").bind(
          "id", todoCreated.getId()).bind("title", todoCreated.getTitle()).bind("notes", todoCreated.getNotes()).bind(
          "created", Timestamp.valueOf(todoCreated.getCreated())).execute();
      return null;
    });
  }
}
