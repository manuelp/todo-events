package com.github.manuelp.todoEvents.events.avro.writers;

import me.manuelp.jevsto.EventDataWriter;
import me.manuelp.jevsto.dataTypes.EventData;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.BinaryEncoder;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.io.EncoderFactory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import static me.manuelp.jevsto.dataTypes.EventData.eventData;

public abstract class AvroEventDataWriter<T> implements EventDataWriter<T> {
  private final Schema schema;

  public AvroEventDataWriter(String schema) {
    try {
      String schemaFile = this.getClass().getClassLoader().getResource(schema).getFile();
      this.schema = new Schema.Parser().parse(new File(schemaFile));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public Schema schema() {
    return schema;
  }

  @Override
  public EventData f(T event) {
    EventData eventData;
    try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
      GenericRecord r = createRecord(event);
      DatumWriter<GenericRecord> writer = new GenericDatumWriter<>(schema);
      BinaryEncoder binaryEncoder = EncoderFactory.get().binaryEncoder(out, null);
      writer.write(r, binaryEncoder);
      binaryEncoder.flush();
      eventData = eventData(out.toByteArray());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return eventData;
  }

  public abstract GenericRecord createRecord(T event);
}
