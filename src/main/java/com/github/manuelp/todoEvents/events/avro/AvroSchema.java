package com.github.manuelp.todoEvents.events.avro;

import org.apache.avro.Schema;

import java.io.File;
import java.io.IOException;

public class AvroSchema {
  public static Schema readSchema(File schemaFile) {
    try {
      return new Schema.Parser().parse(schemaFile);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
