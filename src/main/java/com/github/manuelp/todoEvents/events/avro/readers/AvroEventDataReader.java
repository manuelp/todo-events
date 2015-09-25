package com.github.manuelp.todoEvents.events.avro.readers;

import com.github.manuelp.todoEvents.events.avro.AvroSchema;
import me.manuelp.jevsto.EventDataReader;
import me.manuelp.jevsto.dataTypes.EventData;
import org.apache.avro.Schema;
import org.apache.avro.file.DataFileStream;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.generic.GenericRecord;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

public abstract class AvroEventDataReader<T> implements EventDataReader<T> {
  private final Schema schema;

  public AvroEventDataReader(String schema) {
    String schemaFilePath = this.getClass().getClassLoader().getResource(schema).getFile();
    File   schemaFile     = new File(schemaFilePath);
    this.schema = AvroSchema.readSchema(schemaFile);
  }

  public AvroEventDataReader(File schemaFile) {
    this.schema = AvroSchema.readSchema(schemaFile);
  }

  @Override
  public T f(EventData eventData) {
    GenericDatumReader<GenericRecord> datumReader = new GenericDatumReader<>(schema);
    try (ByteArrayInputStream in = new ByteArrayInputStream(eventData.getData());
         DataFileStream<GenericRecord> fileStream = new DataFileStream<>(in, datumReader)) {
      GenericRecord r = new GenericData.Record(schema);
      fileStream.next(r);
      return readRecord(r);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public abstract T readRecord(GenericRecord record);
}
