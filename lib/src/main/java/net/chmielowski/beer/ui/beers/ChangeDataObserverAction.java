package net.chmielowski.beer.ui.beers;

import net.chmielowski.beer.model.Beer;

import java.util.List;

import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func1;

class ChangeDataObserverAction
        implements Action1<Func1<List<Beer>, List<Beer>>> {
    private final Observable<List<Beer>> mBeersDataSource;
    private final ShowBeersAction mShowBeersAction; // TODO: just Action1
    private Subscription subscription = null; // TODO: NullSubscription

    ChangeDataObserverAction(final Observable<List<Beer>> subject,
            final ShowBeersAction showBeers) {
        this.mBeersDataSource = subject;
        this.mShowBeersAction = showBeers;
    }

    @Override
    public void call(final Func1<List<Beer>, List<Beer>> sortingFunction) {
        Subscription tmp = mBeersDataSource
                .map(sortingFunction)
                .subscribe(mShowBeersAction);
//            if (subscription != null) {
//                subscription.unsubscribe();
//            }
//            subscription = tmp;
    }
}
