package net.chmielowski.beer.ui.beers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import net.chmielowski.beer.BeerApplication;
import net.chmielowski.beer.R;
import net.chmielowski.beer.model.Beers;
import net.chmielowski.beer.ui.addbeer.AddBeerActivity;

import javax.inject.Inject;


public final class BeersActivity extends AppCompatActivity {

    @Inject
    Beers mBeers;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beers);
        ((BeerApplication) getApplication()).cachedComponent().inject(this);
        final BasicBeersView view = new BasicBeersView(this);
        new BeersPresenter(
                view,
                new ChangeDataObserverAction(
                        mBeers.list(),
                        new ShowBeersAction(view)
                )
        );
        new FabPresenter(
                view,
                new StartAddBeerActivity(this)
        );
    }

    void startAddBeerActivity() {
        startActivity(
                new Intent(
                        getApplicationContext(),
                        AddBeerActivity.class
                ));
    }

}
