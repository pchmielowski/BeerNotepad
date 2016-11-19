package net.chmielowski.beer.ui.regiser;

import net.chmielowski.beer.login.User;

import rx.functions.Action1;

final class RegisterPresenter {
    RegisterPresenter(final RegisterView view, final User user,
            final Action1<Void> startBeersActivityAction) {
        view.registerButtonClicked().subscribe(new Action1<Void>() {
            @Override
            public void call(final Void aVoid) {
                user.register(
                        view.email(),
                        view.password()
                );
            }
        });
    }
}
