package net.chmielowski.beer.login;

import rx.Observable;

public interface User {
    String uid();

    Observable<Boolean> login(String name, String passwd);

    Observable<Boolean> register(String email, String password);
}
