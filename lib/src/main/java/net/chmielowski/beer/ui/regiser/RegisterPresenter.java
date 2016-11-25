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
                if (!view.password().equals(view.passwordRepeated())) {
                    view.showError("Passwords do not match");
                    return;
                }
                user.register(
                        view.email(),
                        view.password()
                ).map(new Func1<Boolean, Void>() {
                    @Override
                    public Void call(final Boolean aBoolean) {
                        return null;
                    }
                }).subscribe(
                        startBeersActivityAction,
                        new Action1<Throwable>() {
                            @Override
                            public void call(final Throwable throwable) {
                                view.showError(throwable.getMessage());
                            }
                        }
                );
            }
        });
    }
}
