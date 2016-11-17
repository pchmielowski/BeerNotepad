package net.chmielowski.beer.ui.addbeer;

import net.chmielowski.beer.model.Beers;

import rx.functions.Action1;

class AddBeerPresenter {
    AddBeerPresenter(final AddBeerView view, final Beers beers,
                     final Finishable activity) {
        view.okClicked().subscribe(new Action1() {
            @Override
            public void call(final Object o) {
                beers.add(view.name(), view.country(), view.style(),
                          view.rating());
                activity.finish();
            }
        });
    }
}
