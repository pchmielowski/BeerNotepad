package net.chmielowski.beer.model;

import java.util.List;

import rx.Observable;

public interface Beers {
    Observable<List<Beer>> list();

    void add(String name, String country, String style, float rating,
            String photo);
}
