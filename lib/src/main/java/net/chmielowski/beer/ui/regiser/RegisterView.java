package net.chmielowski.beer.ui.regiser;

import rx.Observable;

interface RegisterView {
    Observable<Void> registerButtonClicked();

    String email();

    String password();

    void showError(String message);
}
