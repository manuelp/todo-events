package com.github.manuelp.todoEvents.events.avro.writers;

import com.github.manuelp.todoEvents.events.TodoCompleted;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;

public class TodoCompletedWriter extends AvroEventDataWriter<TodoCompleted> {

  public TodoCompletedWriter(String schema) {
    super(schema);
  }

  @Override
  public GenericRecord createRecord(TodoCompleted event) {
    GenericRecord r = new GenericData.Record(schema());
    r.put("id", event.getId().toString());
    r.put("completed", event.getCompleted().toString());
    return r;
  }
}
