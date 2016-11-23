package net.chmielowski.beer;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;

import net.chmielowski.beer.login.User;
import net.chmielowski.beer.model.Beers;
import net.chmielowski.beer.model.Photo;

import org.mockito.Mockito;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public final class MockModule {

    // TODO: duplicated
    @Singleton
    @Provides
    FirebaseAuth provideFirebaseAuth() {
        return FirebaseAuth.getInstance();
    }

    @Singleton
    @Provides
    Beers provideFireBaseBeers(final FirebaseAuth auth) {
        return Mockito.mock(Beers.class);
    }

    @Singleton
    @Provides
    User provideFirebaseUser(final FirebaseAuth auth) {
        return Mockito.mock(User.class);
    }
    
    @Singleton
    @Provides
    FirebaseStorage provideStorage() {
        return FirebaseStorage.getInstance();
    }

    @Singleton
    @Provides
    Photo providePhoto(final FirebaseStorage storage) {
        return Mockito.mock(Photo.class);
    }
}
