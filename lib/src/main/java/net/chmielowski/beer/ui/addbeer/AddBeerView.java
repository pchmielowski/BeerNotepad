package net.chmielowski.beer.ui.addbeer;

import rx.Observable;

interface AddBeerView {
    Observable okClicked();

    String name();

    String country();

    float rating();

    String style();
}
