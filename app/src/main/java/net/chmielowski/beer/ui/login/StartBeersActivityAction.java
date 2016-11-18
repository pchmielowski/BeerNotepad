package net.chmielowski.beer.ui.login;

import android.app.Activity;
import android.content.Intent;

import net.chmielowski.beer.ui.beers.BeersActivity;

import rx.functions.Action1;

public final class StartBeersActivityAction
        implements Action1<Void> {
    private final Activity mActivity;

    public StartBeersActivityAction(final Activity activity) {
        this.mActivity = activity;
    }

    @Override
    public void call(final Void aVoid) {
        mActivity.startActivity(
                new Intent(
                        mActivity.getApplicationContext(),
                        BeersActivity.class
                ));
        mActivity.finish();
    }
}
