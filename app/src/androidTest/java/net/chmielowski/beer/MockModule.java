package net.chmielowski.beer;


import net.chmielowski.beer.login.User;
import net.chmielowski.beer.model.AddBeers;
import net.chmielowski.beer.model.Photo;
import net.chmielowski.beer.model.ReadBeers;

import org.mockito.Mockito;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public final class MockModule {

    @Singleton
    @Provides
    ReadBeers provideFireBaseBeers() {
        return Mockito.mock(ReadBeers.class);
    }

    @Singleton
    @Provides
    AddBeers provideAddBeers() {
        return Mockito.mock(AddBeers.class);
    }

    @Singleton
    @Provides
    User provideFirebaseUser() {
        return Mockito.mock(User.class);
    }

    @Singleton
    @Provides
    Photo providePhoto() {
        return Mockito.mock(Photo.class);
    }
}
