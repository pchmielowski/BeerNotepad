package net.chmielowski.beer.model;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

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
        final StorageReference reference = mStorage.getReferenceFromUrl(mAddr)
                                                   .child("photos/" + mId);
        if (fileExist(reference)) {
            throw new IllegalStateException(
                    "Photo with ID " + mId + " already exist");
        }
        reference.putBytes(stream.toByteArray());
    }

    private boolean fileExist(final StorageReference reference) {
        return reference.getDownloadUrl().isSuccessful();
    }
}
