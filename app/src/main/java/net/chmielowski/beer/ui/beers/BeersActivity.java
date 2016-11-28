package net.chmielowski.beer.ui.beers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import net.chmielowski.beer.BeerApplication;
import net.chmielowski.beer.R;
import net.chmielowski.beer.login.User;
import net.chmielowski.beer.model.ReadBeers;
import net.chmielowski.beer.ui.addbeer.AddBeerActivity;
import net.chmielowski.beer.ui.login.LoginActivity;

import javax.inject.Inject;

import rx.functions.Action1;
import rx.subjects.PublishSubject;


public final class BeersActivity extends AppCompatActivity {

    @Inject
    ReadBeers mBeers;
    @Inject
    User mUser;
    private PublishSubject<Void> mLogoutSubject = PublishSubject.create();

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beers);
        ((BeerApplication) getApplication()).cachedComponent().inject(this);
        final BasicBeersView view = new BasicBeersView(this);
        final ChangeSortingFunc changeSortingFunc =
                new ChangeSortingFunc(
                        mBeers.list(),
                        new ShowBeersAction(view)
                );
        new BeerListPresenter(
                view,
                changeSortingFunc
        );
        new FabPresenter(
                view,
                new StartAddBeerActivityAction()
        );
        new LogoutPresenter(
                mLogoutSubject.asObservable(),
                mUser,
                new Action1<Void>() {
                    @Override
                    public void call(final Void aVoid) {
                        startActivity(new Intent(
                                getApplicationContext(),
                                LoginActivity.class
                        ));
                        finish();
                    }
                },
                changeSortingFunc
        );
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                mLogoutSubject.onNext(null);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public boolean onCreateOptionsMenu(final Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.beers, menu);
        return true;
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
