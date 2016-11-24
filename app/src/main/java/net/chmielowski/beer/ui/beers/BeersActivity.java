package net.chmielowski.beer.ui.beers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import net.chmielowski.beer.BeerApplication;
import net.chmielowski.beer.R;
import net.chmielowski.beer.model.ReadBeers;
import net.chmielowski.beer.ui.addbeer.AddBeerActivity;

import javax.inject.Inject;

import rx.functions.Action1;


public final class BeersActivity extends AppCompatActivity {

    @Inject
    ReadBeers mBeers;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beers);
        ((BeerApplication) getApplication()).cachedComponent().inject(this);
        final BasicBeersView view = new BasicBeersView(this);
        new BeerListPresenter(
                view,
                new ChangeDataObserverAction(
                        mBeers.list(),
                        new ShowBeersAction(view)
                )
        );
        new FabPresenter(
                view,
                new StartAddBeerActivityAction()
        );
    }

    private class StartAddBeerActivityAction implements Action1<Void> {
        @Override
        public void call(final Void aVoid) {
            startActivity(
                    new Intent(
                            getApplicationContext(),
                            AddBeerActivity.class
                    ));
        }
    }
}
