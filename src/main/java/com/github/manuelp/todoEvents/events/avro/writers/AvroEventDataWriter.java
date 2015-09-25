package com.github.manuelp.todoEvents.events.avro.writers;

import com.github.manuelp.todoEvents.events.avro.AvroSchema;
import me.manuelp.jevsto.EventDataWriter;
import me.manuelp.jevsto.dataTypes.EventData;
import org.apache.avro.Schema;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.GenericRecord;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import static me.manuelp.jevsto.dataTypes.EventData.eventData;

public abstract class AvroEventDataWriter<T> implements EventDataWriter<T> {
  private final Schema schema;

  public AvroEventDataWriter(String schema) {
    String schemaFilePath = this.getClass().getClassLoader().getResource(schema).getFile();
    File   schemaFile     = new File(schemaFilePath);
    this.schema = AvroSchema.readSchema(schemaFile);
  }

  public AvroEventDataWriter(File schemaFile) {
    this.schema = AvroSchema.readSchema(schemaFile);
  }

  public Schema schema() {
    return schema;
  }

  @Override
  public EventData f(T event) {
    EventData eventData;
    try (ByteArrayOutputStream out = new ByteArrayOutputStream();
         DataFileWriter<GenericRecord> dataWriter = new DataFileWriter<>(new GenericDatumWriter<>(schema))) {
      dataWriter.create(schema, out);
      dataWriter.append(createRecord(event));
      dataWriter.flush();
      eventData = eventData(out.toByteArray());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return eventData;
  }

  public abstract GenericRecord createRecord(T event);
}
