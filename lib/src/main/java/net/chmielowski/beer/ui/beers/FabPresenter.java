package net.chmielowski.beer.ui.beers;

import rx.functions.Action1;

final class FabPresenter {
    FabPresenter(final BeersView view,
            final Action1<Void>
                    startAddBeerActivity) {
        view.fabClicked().subscribe(startAddBeerActivity);
    }
}
