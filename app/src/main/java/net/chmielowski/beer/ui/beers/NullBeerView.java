package net.chmielowski.beer.ui.beers;

import net.chmielowski.beer.model.Photo;

class NullBeerView implements BeerView {

    @Override
    public void show(final String name, final float rating,
            final String style,
            final String country, final Photo photo) {

    }

}
