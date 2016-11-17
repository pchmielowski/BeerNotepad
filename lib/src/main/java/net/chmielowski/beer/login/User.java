package net.chmielowski.beer.login;

import rx.Observable;

public interface User {
    Observable<Boolean> login(String name, String passwd);
}
