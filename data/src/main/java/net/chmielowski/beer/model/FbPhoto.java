package net.chmielowski.beer.model;

import com.google.firebase.storage.FirebaseStorage;
import com.kelvinapps.rxfirebase.RxFirebaseStorage;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

import rx.Observable;

public final class FbPhoto implements Photo {
    private final FirebaseStorage mStorage;
    private final String mAddr;
    private final String mId;

    public FbPhoto(final FirebaseStorage storage, final String addr,
            final String id) {
        mStorage = storage;
        mAddr = addr;
        mId = id;
    }

    public FbPhoto(final FirebaseStorage storage, final String addr) {
        this(storage, addr, UUID.randomUUID().toString());
    }

    @Override
    public String id() {
        return mId;
    }

    @Override
    public void save(final ByteArrayOutputStream stream) {
        mStorage.getReferenceFromUrl(mAddr)
                .child("photos/" + mId)
                .putBytes(stream.toByteArray());
    }

    @Override
    public Observable<byte[]> bytes() {
        // CHECKSTYLE:OFF
        return RxFirebaseStorage.getBytes(
                mStorage.getReferenceFromUrl(mAddr)
                        .child("photos/" + mId), 1024 * 1024);
        // CHECKSTYLE:ON
    }
}
