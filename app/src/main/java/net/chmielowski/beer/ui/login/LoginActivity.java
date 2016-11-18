package net.chmielowski.beer.ui.login;

import android.app.Activity;
import android.os.Bundle;

import net.chmielowski.beer.BeerApplication;
import net.chmielowski.beer.R;
import net.chmielowski.beer.login.User;

import javax.inject.Inject;

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
                new StartBeersActivityAction(this)
        );
    }
}

