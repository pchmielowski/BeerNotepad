package net.chmielowski.beer.model;

import com.google.firebase.storage.FirebaseStorage;

public final class FbPhotos implements Photos {
    private final FirebaseStorage mStorage;
    private final String mUrl;

    public FbPhotos(final FirebaseStorage storage, final String url) {
        mStorage = storage;
        mUrl = url;
    }

    @Override
    public Photo photo(final String id) {
        return new FbPhoto(
                mStorage,
                mUrl,
                id
        );
    }
}
