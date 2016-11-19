package net.chmielowski.beer.ui.addbeer;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import net.chmielowski.beer.BeerApplication;
import net.chmielowski.beer.R;
import net.chmielowski.beer.model.Beers;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

import javax.inject.Inject;

import rx.functions.Action1;

public final class AddBeerActivity extends AppCompatActivity {
    final int requestImageCapture = 1;
    @Inject
    Beers mBeers;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_beer);
        ((BeerApplication) getApplication()).cachedComponent().inject(this);
        new AddBeerPresenter(
                new BasicAddBeerView(this),
                mBeers,
                new Finishable() {
                    @Override
                    public void finish() {
                        finish(); // TODO: why does it work?
                    }
                },
                new TakePictureAction()
        );
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode,
            final Intent data) {
        if (requestCode == requestImageCapture && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            ((ImageView) findViewById(R.id.add_iv_picture)).setImageBitmap(
                    imageBitmap);

            savePicture(imageBitmap);
        }
    }

    private void savePicture(final Bitmap bitmap) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference
                storageRef = storage.getReferenceFromUrl(
                getString(R.string.fb_storage_addr));
        StorageReference imagesRef = storageRef.child(
                "photos/" + UUID.randomUUID().toString());
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final int quality = 100;
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos);
        byte[] data = baos.toByteArray();
        UploadTask uploadTask = imagesRef.putBytes(data);
    }

    class TakePictureAction implements Action1<Void> {
        @Override
        public void call(final Void aVoid) {
            Intent takePictureIntent = new Intent(
                    MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager())
                    != null) {
                startActivityForResult(
                        takePictureIntent, requestImageCapture);
            }
        }

    }
}
