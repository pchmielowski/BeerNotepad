package net.chmielowski.beer.ui.beers;

import net.chmielowski.beer.model.Photo;

public interface BeerView {

    void show(String name, float rating,
            String style,
            String country, Photo photo);
}
