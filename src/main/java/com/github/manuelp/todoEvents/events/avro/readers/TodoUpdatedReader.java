package com.github.manuelp.todoEvents.events.avro.readers;

import com.github.manuelp.todoEvents.events.TodoUpdated;
import org.apache.avro.generic.GenericRecord;

import java.util.UUID;

public class TodoUpdatedReader extends AvroEventDataReader<TodoUpdated> {
  public TodoUpdatedReader(String schema) {
    super(schema);
  }

  @Override
  public TodoUpdated readRecord(GenericRecord record) {
    UUID   id    = UUID.fromString(readString(record, "id"));
    String title = readString(record, "title");
    String notes = readString(record, "notes");
    return TodoUpdated.todoUpdated(id, title, notes);
  }

}
