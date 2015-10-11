package com.github.manuelp.todoEvents.events;

import com.github.manuelp.todoEvents.events.avro.readers.TodoCompletedReader;
import com.github.manuelp.todoEvents.events.avro.readers.TodoCreatedReader;
import com.github.manuelp.todoEvents.events.avro.readers.TodoUpdatedReader;
import com.github.manuelp.todoEvents.events.avro.writers.TodoCompletedWriter;
import com.github.manuelp.todoEvents.events.avro.writers.TodoCreatedWriter;
import com.github.manuelp.todoEvents.events.avro.writers.TodoUpdatedWriter;
import me.manuelp.jevsto.dataTypes.EventDescriptor;

import static me.manuelp.jevsto.dataTypes.EventDescriptor.eventDescriptor;
import static me.manuelp.jevsto.dataTypes.EventType.eventType;

public class Events {
  public static EventDescriptor<TodoCreated>   TODO_CREATED   = eventDescriptor(eventType("todoCreated"),
                                                                                new TodoCreatedReader(
                                                                                    "TodoCreated.avsc"),
                                                                                new TodoCreatedWriter(
                                                                                    "TodoCreated.avsc"));
  public static EventDescriptor<TodoUpdated>   TODO_UPDATED   = eventDescriptor(eventType("todoUpdated"),
                                                                                new TodoUpdatedReader(
                                                                                    "TodoUpdated.avsc"),
                                                                                new TodoUpdatedWriter(
                                                                                    "TodoUpdated.avsc"));
  public static EventDescriptor<TodoCompleted> TODO_COMPLETED = eventDescriptor(eventType("todoCompleted"),
                                                                                new TodoCompletedReader(
                                                                                    "TodoCompleted.avsc"),
                                                                                new TodoCompletedWriter(
                                                                                    "TodoCompleted.avsc"));
}
