package net.chmielowski.beer.ui.beers;

import net.chmielowski.beer.model.Beer;
import net.chmielowski.beer.model.Beers;

import java.util.List;

import rx.subjects.BehaviorSubject;
import rx.subjects.Subject;

final class BeersPresenter {

    BeersPresenter(final BeersView view, final Beers beers) {
        configureShowingBeers(view, beers);
    }

    private void configureShowingBeers(final BeersView view,
            final Beers beers) {
        view.showLoading(true);
        final Subject<List<Beer>, List<Beer>> dataSource =
                BehaviorSubject.create();
        beers.list().subscribe(dataSource);

        view.sortingMethodNumber()
            .map(new MethodNumberToSortingFunction())
            .subscribe(new ChangeDataObserverAction(
                    dataSource,
                    new ShowBeersAction(view)
            ));

    }

}
