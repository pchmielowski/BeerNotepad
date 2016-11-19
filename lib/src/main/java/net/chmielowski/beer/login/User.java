package net.chmielowski.beer.login;

import rx.Observable;

public interface User {
    String uid();

    Observable<Boolean> login(String name, String passwd);

    void register(String email, String password);
}
