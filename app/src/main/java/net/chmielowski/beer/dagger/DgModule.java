package net.chmielowski.beer.dagger;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import net.chmielowski.beer.login.FbUser;
import net.chmielowski.beer.login.User;
import net.chmielowski.beer.model.Beers;
import net.chmielowski.beer.model.FbBeers;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public final class DgModule {

    @Singleton
    @Provides
    FirebaseAuth provideFirebaseAuth() {
        return FirebaseAuth.getInstance();
    }

    @Singleton // TODO: check if is necessary
    @Provides
    User provideFirebaseUser(final FirebaseAuth auth) {
        return new FbUser(auth);
    }

    @Singleton// TODO: check if is necessary
    @Provides
    Beers provideFireBaseBeers(final User user) {
        return new FbBeers(
                FirebaseDatabase.getInstance()
                                .getReference()
                                .child(user.uid()));
    }

}
