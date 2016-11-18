package net.chmielowski.beer.ui.beers;

import net.chmielowski.beer.model.Beer;

import java.util.List;

import rx.functions.Action1;
import rx.functions.Func1;

final class BeersPresenter {

    BeersPresenter(final BeersView view,
            final Action1<Func1<List<Beer>, List<Beer>>> action) {
        view.showLoading(true);
        view.sortingAscending();
        view.sortingMethodNumber()
            .map(new MethodNumberToSortingFunction())
            .subscribe(action);
    }

}
