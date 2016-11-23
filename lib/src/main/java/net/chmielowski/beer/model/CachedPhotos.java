package net.chmielowski.beer.model;

import java.util.HashMap;

import rx.functions.Action1;

public final class CachedPhotos implements Photos {
    private final Photos mPhotos;
    private final HashMap<String, byte[]> mCache = new HashMap<>();

    public CachedPhotos(final Photos photos) {
        mPhotos = photos;
    }

    @Override
    public Photo photo(final String id) {
        if (mCache.containsKey(id)) {
            return new CachedPhoto(mCache.get(id));
        }
        final Photo photo = mPhotos.photo(id);
        photo.bytes().subscribe(new Action1<byte[]>() {
            @Override
            public void call(final byte[] bytes) {
                mCache.put(id, bytes);
            }
        });
        return photo;
    }
}
