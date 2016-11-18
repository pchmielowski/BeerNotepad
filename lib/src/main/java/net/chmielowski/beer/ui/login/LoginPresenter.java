package net.chmielowski.beer.ui.login;

import net.chmielowski.beer.login.User;

import rx.Observable;
import rx.functions.Action1;

final class LoginPresenter {
    private final LoginView mView;
    private final User mUser;

    LoginPresenter(final LoginView view, final User user) {
        this.mView = view;
        this.mUser = user;
        this.mView.loginButtonClicked().subscribe(
                new LoginAction(mView, mUser));
        this.mView.registerLinkClicked().subscribe(new Action1<Void>() {
            @Override
            public void call(final Void aVoid) {
                mView.startRegisterActivity();
            }
        });
    }

    private static final class ShowLoginResultAction
            implements Action1<Boolean> {
        private final LoginView mView;

        private ShowLoginResultAction(final LoginView view) {
            this.mView = view;
        }

        @Override
        public void call(final Boolean success) {
            if (success) {
                this.mView.startMainActivity();
            } else {
                this.mView.showError();
            }
        }
    }

    private static final class LoginAction implements Action1<Void> {
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
            observable.subscribe(new ShowLoginResultAction(this.mView));
        }
    }
}
