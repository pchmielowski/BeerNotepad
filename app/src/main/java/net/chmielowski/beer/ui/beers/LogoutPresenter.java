package net.chmielowski.beer.ui.beers;

import net.chmielowski.beer.login.User;

import rx.Observable;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;

class LogoutPresenter {
    private final CompositeSubscription mCompositeSubscription
            = new CompositeSubscription();

    public LogoutPresenter(
            final Observable<Void> logoutClicked, final User user,
            final Action1<Void> returnToLoginActivity,
            final ChangeSortingFunc changeSortingFunc) {
        mCompositeSubscription.addAll(
                logoutClicked.subscribe(
                        new Action1<Void>() {
                            @Override
                            public void call(final Void aVoid) {
                                changeSortingFunc.unsubscribe();
                                user.logout();
                            }
                        }
                ),
                logoutClicked.subscribe(returnToLoginActivity)
        );
    }

    public void unsubscribe() {
        mCompositeSubscription.unsubscribe();
    }
}
