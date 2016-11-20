package net.chmielowski.beer.ui.beers;

public interface BeerView {

    void showBeer(String name, float rating, String style, String country,
            byte[] photo);

    void showBeer(String name, float rating, String style, String country);

    void expand();

    void collapse();
}
