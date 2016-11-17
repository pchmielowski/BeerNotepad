package net.chmielowski.beer.ui.beers;

import net.chmielowski.beer.model.Beer;

import java.util.List;

import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.subjects.Subject;

class ChangeDataObserverAction
        implements Action1<Func1<List<Beer>, List<Beer>>> {
    private final Subject<List<Beer>, List<Beer>> mBeersDataSource;
    private final ShowBeersAction mShowBeersAction; // TODO: just Action1
    private Subscription subscription = null; // TODO: NullSubscription

    ChangeDataObserverAction(final Subject<List<Beer>, List<Beer>> subject,
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
