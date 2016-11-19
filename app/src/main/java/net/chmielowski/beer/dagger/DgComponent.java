package net.chmielowski.beer.dagger;

import net.chmielowski.beer.ui.addbeer.AddBeerActivity;
import net.chmielowski.beer.ui.beers.BeersActivity;
import net.chmielowski.beer.ui.login.LoginActivity;
import net.chmielowski.beer.ui.regiser.RegisterActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {DgModule.class})
public interface DgComponent {
    void inject(LoginActivity activity);

    void inject(BeersActivity activity);

    void inject(AddBeerActivity activity);

    void inject(RegisterActivity activity);

}
