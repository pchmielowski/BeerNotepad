package net.chmielowski.beer.ui.addbeer;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.google.firebase.storage.FirebaseStorage;

import net.chmielowski.beer.BeerApplication;
import net.chmielowski.beer.R;
import net.chmielowski.beer.model.Beers;
import net.chmielowski.beer.model.FbPhoto;

import java.io.ByteArrayOutputStream;

import javax.inject.Inject;

import rx.functions.Action1;

public final class AddBeerActivity extends AppCompatActivity {
    static final int REQUEST_IMAGE_CAPTURE = 1;
    @Inject
    Beers mBeers;
    private BasicAddBeerView mView;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_beer);
        ((BeerApplication) getApplication()).cachedComponent().inject(this);
        mView = new BasicAddBeerView(this);
        new AddBeerPresenter(
                mView,
                mBeers,
                new Finishable() {
                    @Override
                    public void finish() {
                        AddBeerActivity.this.finish();
                    }
                },
                new TakePictureAction()
        );
    }

    @Override
    protected void onActivityResult(
            final int requestCode,
            final int resultCode,
            final Intent data) {
        if (requestCode != REQUEST_IMAGE_CAPTURE || resultCode != RESULT_OK) {
            return;
        }
        Bitmap bmp = photo(data);
        showImage(bmp);
        final FbPhoto fbPhoto = new FbPhoto(
                FirebaseStorage.getInstance(),
                getString(R.string.fb_storage_addr)
        );
        fbPhoto.save(compressed(bmp));
        mView.mPhoto = fbPhoto.id();
    }

    private Bitmap photo(final Intent intent) {
        return (Bitmap) intent.getExtras().get("data");
    }

    private void showImage(final Bitmap bmp) {
        ((ImageView) findViewById(R.id.add_iv_picture))
                .setImageBitmap(bmp);
    }

    @NonNull
    private ByteArrayOutputStream compressed(final Bitmap imageBitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        final int quality = 100;
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, quality, stream);
        return stream;
    }

    class TakePictureAction implements Action1<Void> {
        @Override
        public void call(final Void aVoid) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (intent.resolveActivity(getPackageManager()) == null) {
                return;
            }
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
        }

    }
}
