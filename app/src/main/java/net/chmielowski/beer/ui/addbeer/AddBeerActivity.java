package net.chmielowski.beer.ui.addbeer;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;

import net.chmielowski.beer.BeerApplication;
import net.chmielowski.beer.R;
import net.chmielowski.beer.model.Beers;

import javax.inject.Inject;

import rx.functions.Action1;

public final class AddBeerActivity extends AppCompatActivity {
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

    class TakePictureAction implements Action1<Void> {
        @Override
        public void call(final Void aVoid) {
            final int requestImageCapture = 1;
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
