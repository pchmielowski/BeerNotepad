package net.chmielowski.beer.dagger;

import net.chmielowski.beer.ui.beers.BeersActivity;
import net.chmielowski.beer.ui.login.LoginActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {DgModule.class})
public interface DgComponent {
    void inject(LoginActivity activity);

    void inject(BeersActivity activity);

}
