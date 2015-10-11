package com.github.manuelp.todoEvents.listView;

import com.github.manuelp.todoEvents.events.TodoCompleted;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.skife.jdbi.v2.DBI;
import rx.Observer;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class CompleteTodo implements Observer<TodoCompleted> {
  private static final Logger logger = LogManager.getLogger(CompleteTodo.class);
  private DBI dbi;

  public CompleteTodo(DBI dbi) {
    this.dbi = dbi;
  }

  @Override
  public void onCompleted() {

  }

  @Override
  public void onError(Throwable e) {

  }

  @Override
  public void onNext(TodoCompleted todoCompleted) {
    logger.debug("Processing: " + todoCompleted);
    dbi.withHandle(h -> {
      h.createStatement("UPDATE list_view SET complete=true, completed=:completed WHERE id=:id").bind("id",
                                                                                                      todoCompleted
                                                                                                          .getId())
       .bind("completed", Timestamp.valueOf(LocalDateTime.now())).execute();
      return null;
    });
  }
}
