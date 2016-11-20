package net.chmielowski.beer.model;

import java.io.ByteArrayOutputStream;

public interface Photo {
    String id();

    void save(ByteArrayOutputStream bitmap);
}
