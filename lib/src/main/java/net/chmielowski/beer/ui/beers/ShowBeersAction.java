package net.chmielowski.beer.ui.beers;

import net.chmielowski.beer.model.Beer;

import java.util.List;

import rx.Observer;

class ShowBeersAction implements Observer<List<Beer>> {
    private final BeersView mView;

    ShowBeersAction(final BeersView view) {
        this.mView = view;
    }

    @Override
    public void onCompleted() {
    }

    @Override
    public void onError(final Throwable e) {
        mView.showError(e);
    }

    @Override
    public void onNext(final List<Beer> list) {
        mView.add(list);
        mView.showLoading(false);
    }
}
