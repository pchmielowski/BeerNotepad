package net.chmielowski.beer.ui.beers;

import net.chmielowski.beer.model.Photo;

public interface BeerView {

    void showBeer(String name, float rating,
            String style,
            String country, Photo photo);

    void expand();

    void collapse();
}
