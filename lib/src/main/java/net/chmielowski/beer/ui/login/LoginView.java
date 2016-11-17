package net.chmielowski.beer.ui.login;

import rx.Observable;

interface LoginView {
    Observable<Void> loginButtonClicked();

    String email();

    String password();

    void showError();

    void startMainActivity();

    void showLoading();
}
