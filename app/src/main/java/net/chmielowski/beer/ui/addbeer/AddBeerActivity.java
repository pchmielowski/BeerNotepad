package net.chmielowski.beer.ui.addbeer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import net.chmielowski.beer.BeerApplication;
import net.chmielowski.beer.R;
import net.chmielowski.beer.model.Beers;

import javax.inject.Inject;

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
                        finish();
                    }
                }
        );
    }

}
