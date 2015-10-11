CREATE TABLE events (
  id UUID NOT NULL PRIMARY KEY,
  created TIMESTAMP NOT NULL DEFAULT NOW(),
  type TEXT NOT NULL,
  data BYTEA
);