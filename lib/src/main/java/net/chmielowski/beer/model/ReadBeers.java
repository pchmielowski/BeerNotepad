package net.chmielowski.beer.model;

import java.util.List;

import rx.Observable;

public interface ReadBeers {
    Observable<List<Beer>> list();
}
