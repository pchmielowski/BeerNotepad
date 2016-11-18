package net.chmielowski.beer.ui.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import net.chmielowski.beer.BeerApplication;
import net.chmielowski.beer.R;
import net.chmielowski.beer.login.User;
import net.chmielowski.beer.ui.beers.BeersActivity;

import javax.inject.Inject;

import rx.functions.Action1;

public final class LoginActivity extends Activity {
    @Inject
    User user;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ((BeerApplication) getApplication()).cachedComponent().inject(this);
        new LoginPresenter(
                new BasicLoginView(this),
                user,
                new StartBeersActivityAction()
        );
    }

    public final class StartBeersActivityAction implements Action1<Void> {
        @Override
        public void call(final Void aVoid) {
            startActivity(
                    new Intent(
                            getApplicationContext(),
                            BeersActivity.class
                    ));
            finish();
        }
    }
}

