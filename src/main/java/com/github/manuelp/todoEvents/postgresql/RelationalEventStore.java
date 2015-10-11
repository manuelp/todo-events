package com.github.manuelp.todoEvents.postgresql;

import fj.data.List;
import fj.data.Option;
import me.manuelp.jevsto.EventStore;
import me.manuelp.jevsto.dataTypes.Event;
import org.skife.jdbi.v2.DBI;
import org.threeten.bp.LocalDateTime;
import rx.Observable;

import java.util.UUID;

public class RelationalEventStore implements EventStore {
  private final DBI dbi;

  public RelationalEventStore(String connectionString) {
    dbi = new DBI(connectionString);
  }

  @Override
  public void append(Event event) {

  }

  @Override
  public Observable<Event> getEvents() {
    return null;
  }

  @Override
  public Observable<Event> getAllEvents() {
    return null;
  }

  @Override
  public Observable<Event> getAllEventsFrom(LocalDateTime localDateTime) {
    return null;
  }

  @Override
  public List<Event> getAll() {
    return null;
  }

  @Override
  public List<Event> getFrom(LocalDateTime localDateTime) {
    return null;
  }

  @Override
  public Option<Event> getById(UUID uuid) {
    return null;
  }
}
