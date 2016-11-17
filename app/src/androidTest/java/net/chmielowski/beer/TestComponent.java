package net.chmielowski.beer;

import net.chmielowski.beer.dagger.DgComponent;
import net.chmielowski.beer.ui.beers.BeersActivityTest;
import net.chmielowski.beer.ui.login.LoginActivityTest;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = MockModule.class)
public interface TestComponent extends DgComponent {
    void inject(LoginActivityTest test);

    void inject(BeersActivityTest test);
}
