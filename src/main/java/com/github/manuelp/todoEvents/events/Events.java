package com.github.manuelp.todoEvents.events;

import com.github.manuelp.todoEvents.events.avro.readers.TodoCreatedReader;
import com.github.manuelp.todoEvents.events.avro.writers.TodoCreatedWriter;
import me.manuelp.jevsto.dataTypes.EventDescriptor;

import static me.manuelp.jevsto.dataTypes.EventDescriptor.eventDescriptor;
import static me.manuelp.jevsto.dataTypes.EventType.eventType;

public class Events {
  public static EventDescriptor<TodoCreated> TODO_CREATED = eventDescriptor(eventType("todoCreated"),
                                                                            new TodoCreatedReader("TodoCreated.avsc"),
                                                                            new TodoCreatedWriter("TodoCreated.avsc"));
}
