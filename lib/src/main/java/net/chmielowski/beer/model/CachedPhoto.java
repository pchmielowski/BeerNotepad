package net.chmielowski.beer.model;

import java.io.ByteArrayOutputStream;

import rx.Observable;

final class CachedPhoto implements Photo {
    private final byte[] mBytes;

    CachedPhoto(final byte[] bytes) {
        mBytes = bytes;
    }

    @Override
    public String id() {
        throw new IllegalStateException("Cached photo has no id");
    }

    @Override
    public void save(final ByteArrayOutputStream bitmap) {
        throw new IllegalStateException("Cached photo cannot save");
    }

    @Override
    public Observable<byte[]> bytes() {
        return Observable.just(mBytes);
    }
}
