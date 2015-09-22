package com.github.manuelp.todoEvents.events.avro.readers;

import me.manuelp.jevsto.EventDataReader;
import me.manuelp.jevsto.dataTypes.EventData;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.BinaryDecoder;
import org.apache.avro.io.DecoderFactory;

import java.io.File;
import java.io.IOException;

public abstract class AvroEventDataReader<T> implements EventDataReader<T> {
  private final Schema schema;

  public AvroEventDataReader(String schema) {
    try {
      String schemaFile = this.getClass().getClassLoader().getResource(schema).getFile();
      this.schema = new Schema.Parser().parse(new File(schemaFile));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public T f(EventData eventData) {
    BinaryDecoder                     decoder = DecoderFactory.get().binaryDecoder(eventData.getData(), null);
    GenericDatumReader<GenericRecord> reader  = new GenericDatumReader<>(schema);
    GenericRecord                     record;
    try {
      record = reader.read(null, decoder);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    return readRecord(record);
  }

  public abstract T readRecord(GenericRecord record);
}
