package net.chmielowski.beer.ui.beers;

final class BeersPresenter {

    private final ChangeDataObserverAction mAction;

    BeersPresenter(final BeersView view,
            final ChangeDataObserverAction action) {
        mAction = action;
        configureShowingBeers(view);
    }

    private void configureShowingBeers(final BeersView view) {
        view.showLoading(true);
        view.sortingMethodNumber()
            .map(new MethodNumberToSortingFunction())
            .subscribe(mAction);

    }

}
