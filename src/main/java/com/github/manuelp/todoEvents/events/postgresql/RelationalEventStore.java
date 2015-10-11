package com.github.manuelp.todoEvents.events.postgresql;

import fj.data.List;
import fj.data.Option;
import me.manuelp.jevsto.EventStore;
import me.manuelp.jevsto.dataTypes.Event;
import me.manuelp.jevsto.dataTypes.EventData;
import me.manuelp.jevsto.dataTypes.EventType;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Query;
import org.skife.jdbi.v2.tweak.ResultSetMapper;
import org.threeten.bp.DateTimeUtils;
import org.threeten.bp.LocalDateTime;
import rx.Observable;
import rx.subjects.PublishSubject;

import java.sql.SQLException;
import java.util.Map;
import java.util.UUID;

import static fj.data.List.iterableList;
import static me.manuelp.jevsto.dataTypes.Event.event;
import static me.manuelp.jevsto.dataTypes.EventData.eventData;
import static me.manuelp.jevsto.dataTypes.EventType.eventType;

public class RelationalEventStore implements EventStore {
  private static final String SELECT_EVENT = "SELECT id,created,type,data FROM events";

  private final DBI                   dbi;
  private final PublishSubject<Event> stream;

  public RelationalEventStore(String connectionString) {
    dbi = new DBI(connectionString);
    stream = PublishSubject.create();
  }

  @Override
  public void append(Event event) {
    dbi.withHandle(h -> {
      h.execute("INSERT INTO events (id,created,type,data) VALUES (?,?,?,?)", event.getId(),
                DateTimeUtils.toSqlTimestamp(event.getTimestamp()), event.getType().getType(),
                event.getData().getData());
      System.out.println("Saved event " + event.getId().toString());
      return null;
    });
    stream.onNext(event);
  }

  @Override
  public Observable<Event> getEvents() {
    return stream;
  }

  @Override
  public Observable<Event> getAllEvents() {
    return Observable.from(getAll()).concatWith(stream);
  }

  @Override
  public Observable<Event> getAllEventsFrom(LocalDateTime t) {
    return Observable.from(getFrom(t)).concatWith(stream);
  }

  @Override
  public List<Event> getAll() {
    return dbi.withHandle(h -> iterableList(h.createQuery(SELECT_EVENT).map(readEvent()).list()));
  }

  @Override
  public List<Event> getFrom(LocalDateTime t) {
    return dbi.withHandle(h -> {
      Query<Map<String, Object>> q = h.createQuery(SELECT_EVENT + " WHERE created>=:when").bind("when", DateTimeUtils
          .toSqlTimestamp(t));
      return iterableList(q.map(readEvent()).list());
    });
  }

  @Override
  public Option<Event> getById(UUID uuid) {
    return dbi.withHandle(h -> {
      Query<Event> q = h.createQuery(SELECT_EVENT + " WHERE id=:id").bind("id", uuid).map(readEvent());
      java.util.List<Event> events = q.list();
      return events.size() == 1 ? Option.some(events.get(0)) : Option.<Event>none();
    });
  }

  private ResultSetMapper<Event> readEvent() throws SQLException {
    return (index, r, ctx) -> {
      UUID id = UUID.fromString(r.getString("id"));
      LocalDateTime timestamp = DateTimeUtils.toLocalDateTime(r.getTimestamp("created"));
      EventType type = eventType(r.getString("type"));
      EventData data = eventData(r.getBytes("data"));
      return event(id, timestamp, type, data);
    };
  }
}
