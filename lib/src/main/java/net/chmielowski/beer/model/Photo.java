package net.chmielowski.beer.model;

import java.io.ByteArrayOutputStream;

import rx.Observable;

public interface Photo {
    String id();

    void save(ByteArrayOutputStream bitmap);

    Observable<byte[]> bytes();
}
