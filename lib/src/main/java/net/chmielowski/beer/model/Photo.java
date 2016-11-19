package net.chmielowski.beer.model;

import java.io.ByteArrayOutputStream;

interface Photo {
    void save(ByteArrayOutputStream bitmap);
}
