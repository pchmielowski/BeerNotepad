package net.chmielowski.beer.ui.login;

import net.chmielowski.beer.login.User;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

final class LoginPresenter {
    private final LoginView mView;
    private final User mUser;
    private final Action1<Void> mAfterLoginAction;

    LoginPresenter(final LoginView view, final User user,
            final Action1<Void> onLoginAction) {
        this.mView = view;
        this.mUser = user;
        mAfterLoginAction = onLoginAction;
        this.mView.loginButtonClicked().subscribe(
                new LoginAction(mView, mUser));
        this.mView.registerLinkClicked().subscribe(new Action1<Void>() {
            @Override
            public void call(final Void aVoid) {
                mView.startRegisterActivity();
            }
        });
    }

    private final class LoginAction implements Action1<Void> {
        private final LoginView mView;
        private final User mUser;

        private LoginAction(final LoginView view, final User user) {
            mView = view;
            mUser = user;
        }

        @Override
        public void call(final Void aVoid) {
            this.mView.showLoading();
            final Observable<Boolean> observable = this.mUser.login(
                    this.mView.email(), this.mView.password());
            observable.filter(new Func1<Boolean, Boolean>() {
                @Override
                public Boolean call(final Boolean aBoolean) {
                    return aBoolean;
                }
            }).map(new Func1<Boolean, Void>() {
                @Override
                public Void call(final Boolean aBoolean) {
                    return null;
                }
            })
                      .subscribe(mAfterLoginAction);
            observable.filter(new Func1<Boolean, Boolean>() {
                @Override
                public Boolean call(final Boolean aBoolean) {
                    return !aBoolean;
                }
            }).subscribe(new Action1<Boolean>() {
                @Override
                public void call(final Boolean aBoolean) {
                    mView.showError();
                }
            });
        }
    }
}
