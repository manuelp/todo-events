package com.github.manuelp.todoEvents.events.avro.readers;

import com.github.manuelp.todoEvents.events.TodoCompleted;
import org.apache.avro.generic.GenericRecord;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.github.manuelp.todoEvents.events.TodoCompleted.todoCompleted;

public class TodoCompletedReader extends AvroEventDataReader<TodoCompleted> {
  public TodoCompletedReader(String schema) {
    super(schema);
  }

  @Override
  public TodoCompleted readRecord(GenericRecord record) {
    UUID          id        = UUID.fromString(readString(record, "id"));
    LocalDateTime completed = LocalDateTime.parse(readString(record, "completed"));
    return todoCompleted(id, completed);
  }
}
