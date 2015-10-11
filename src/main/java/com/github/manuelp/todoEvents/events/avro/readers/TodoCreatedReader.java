package com.github.manuelp.todoEvents.events.avro.readers;

import com.github.manuelp.todoEvents.events.TodoCreated;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.util.Utf8;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.github.manuelp.todoEvents.events.TodoCreated.todoCreated;

public class TodoCreatedReader extends AvroEventDataReader<TodoCreated> {
  public TodoCreatedReader(String schema) {
    super(schema);
  }

  @Override
  public TodoCreated readRecord(GenericRecord record) {
    UUID          id      = UUID.fromString(readString(record, "id"));
    String        title   = readString(record, "title");
    String        notes   = readString(record, "notes");
    LocalDateTime created = LocalDateTime.parse(readString(record, "created"));
    return todoCreated(id, title, notes, created);
  }
}
