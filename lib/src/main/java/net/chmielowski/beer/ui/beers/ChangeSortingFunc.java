package net.chmielowski.beer.ui.beers;

import net.chmielowski.beer.model.Beer;

import java.util.List;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.subscriptions.CompositeSubscription;

final class ChangeSortingFunc
        implements Action1<Func1<List<Beer>, List<Beer>>> {
    private final Observable<List<Beer>> mBeersDataSource;
    private final ShowBeersAction mShowBeersAction; // TODO: just Action1
    private final CompositeSubscription mSubs = new CompositeSubscription();

    ChangeSortingFunc(final Observable<List<Beer>> subject,
            final ShowBeersAction showBeers) {
        this.mBeersDataSource = subject;
        this.mShowBeersAction = showBeers;
    }

    @Override
    public void call(final Func1<List<Beer>, List<Beer>> sortingFunction) {
        mSubs.add(
                mBeersDataSource
                        .map(sortingFunction)
                        .subscribe(mShowBeersAction));
    }

    void unsubscribe() {
        mSubs.unsubscribe();
    }
}
