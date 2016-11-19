package net.chmielowski.beer.model;

import com.google.firebase.storage.FirebaseStorage;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

public final class FbPhoto implements Photo {
    private final FirebaseStorage mStorage;
    private final String mAddr;
    private final String mId;

    private FbPhoto(final FirebaseStorage storage, final String addr,
            final String id) {
        mStorage = storage;
        mAddr = addr;
        mId = id;
    }

    public FbPhoto(final FirebaseStorage storage, final String addr) {
        this(storage, addr, UUID.randomUUID().toString());
    }

    public String id() {
        return mId;
    }

    @Override
    public void save(final ByteArrayOutputStream stream) {
        mStorage.getReferenceFromUrl(mAddr)
                .child("photos/" + mId)
                .putBytes(stream.toByteArray());
    }
}
