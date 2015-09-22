package com.github.manuelp.todoEvents.events.avro.writers;

import com.github.manuelp.todoEvents.events.TodoCreated;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;

public class TodoCreatedWriter extends AvroEventDataWriter<TodoCreated> {

  public TodoCreatedWriter(String schema) {
    super(schema);
  }

  @Override
  public GenericRecord createRecord(TodoCreated event) {
    GenericRecord r = new GenericData.Record(schema());
    r.put("id", event.getId().toString());
    r.put("title", event.getTitle());
    r.put("notes", event.getNotes());
    r.put("created", event.getCreated().toString());
    return r;
  }
}
