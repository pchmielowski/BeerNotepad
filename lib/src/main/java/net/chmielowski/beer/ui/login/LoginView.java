package net.chmielowski.beer.ui.login;

import rx.Observable;

interface LoginView {
    Observable<Void> loginButtonClicked();

    Observable<Void> registerLinkClicked();

    String email();

    String password();

    void showError();

    void showLoading();

    void startRegisterActivity();
}
