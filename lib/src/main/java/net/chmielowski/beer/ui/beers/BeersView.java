package net.chmielowski.beer.ui.beers;

import net.chmielowski.beer.model.Beer;

import java.util.List;

import rx.Observable;

interface BeersView {
    Observable<Integer> sortingMethodNumber();

    Observable<Boolean> sortingAscending();

    Observable fabClicked();

    void add(List<Beer> beers);

    void showLoading(boolean b);

    void showError(Throwable e);
}
