package net.chmielowski.beer.ui.regiser;

import net.chmielowski.beer.login.User;

import rx.functions.Action1;
import rx.functions.Func1;

final class RegisterPresenter {
    RegisterPresenter(final RegisterView view, final User user,
            final Action1<Void> startBeersActivityAction) {
        view.registerButtonClicked().subscribe(new Action1<Void>() {
            @Override
            public void call(final Void aVoid) {
                user.register(
                        view.email(),
                        view.password()
                ).map(new Func1<Boolean, Void>() {
                    @Override
                    public Void call(final Boolean aBoolean) {
                        return null;
                    }
                }).subscribe(startBeersActivityAction);
            }
        });
    }
}
