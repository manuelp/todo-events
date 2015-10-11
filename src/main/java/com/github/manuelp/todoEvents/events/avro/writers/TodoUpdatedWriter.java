package com.github.manuelp.todoEvents.events.avro.writers;

import com.github.manuelp.todoEvents.events.TodoCreated;
import com.github.manuelp.todoEvents.events.TodoUpdated;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;

public class TodoUpdatedWriter extends AvroEventDataWriter<TodoUpdated> {

  public TodoUpdatedWriter(String schema) {
    super(schema);
  }

  @Override
  public GenericRecord createRecord(TodoUpdated event) {
    GenericRecord r = new GenericData.Record(schema());
    r.put("id", event.getId().toString());
    r.put("title", event.getTitle());
    r.put("notes", event.getNotes());
    return r;
  }
}
