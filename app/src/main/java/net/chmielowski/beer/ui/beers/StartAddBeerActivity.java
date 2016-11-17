package net.chmielowski.beer.ui.beers;

import rx.functions.Action1;

class StartAddBeerActivity implements Action1<Void> {
    private final BeersActivity mActivity;

    StartAddBeerActivity(final BeersActivity activity) {
        mActivity = activity;
    }

    @Override
    public void call(final Void aVoid) {
        mActivity.startAddBeerActivity();
    }
}
