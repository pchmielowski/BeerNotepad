package net.chmielowski.beer.ui.beers;

final class BeersPresenter {

    BeersPresenter(final BeersView view,
            final ChangeDataObserverAction action) {
        view.showLoading(true);
        view.sortingMethodNumber()
            .map(new MethodNumberToSortingFunction())
            .subscribe(action);
    }

}
