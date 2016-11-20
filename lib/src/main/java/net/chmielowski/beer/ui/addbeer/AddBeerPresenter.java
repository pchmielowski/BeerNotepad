package net.chmielowski.beer.ui.addbeer;

import net.chmielowski.beer.model.Beers;
import net.chmielowski.beer.model.Photo;

import rx.functions.Action1;

class AddBeerPresenter {
    AddBeerPresenter(final AddBeerView view, final Beers beers,
            final Finishable activity,
            final Action1<Void> takePictureAction,
            final Photo photo) {
        view.okClicked().subscribe(new Action1<Void>() {
            @Override
            public void call(final Void aVoid) {
                beers.add(view.name(), view.country(), view.style(),
                          view.rating(), photo.id()
                );
                activity.finish();
            }
        });
        view.takePictureClicked().subscribe(takePictureAction);
    }

}
