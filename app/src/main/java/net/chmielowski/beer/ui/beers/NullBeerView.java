package net.chmielowski.beer.ui.beers;

class NullBeerView implements BeerView {

    @Override
    public void showBeer(final String name, final float rating,
            final String style,
            final String country, final FbPhoto photo) {

    }

    @Override
    public void showBeer(final String name, final float rating,
            final String style, final String country) {

    }

    @Override
    public void expand() {

    }

    @Override
    public void collapse() {

    }
}
