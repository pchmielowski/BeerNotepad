package net.chmielowski.beer.model;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.kelvinapps.rxfirebase.RxFirebaseDatabase;

import java.util.LinkedList;
import java.util.List;

import rx.Observable;
import rx.functions.Func1;

public final class FbReadBeers implements ReadBeers {

    private final DatabaseReference mDatabase;
    private final Photos mPhotos;

    public FbReadBeers(final DatabaseReference ref, final Photos photos) {
        mDatabase = ref;
        mPhotos = photos;
    }

    @Override
    public Observable<List<Beer>> list() {
        return RxFirebaseDatabase
                .observeValueEvent(mDatabase)
                .map(new GetChildren())
                .map(new IterableToList(mPhotos));
    }

    static class IterableToList
            implements Func1<Iterable<DataSnapshot>, List<Beer>> {

        private final Photos mPhotos;

        IterableToList(final Photos photos) {
            mPhotos = photos;
        }

        @Override
        public List<Beer> call(final Iterable<DataSnapshot> dataSnapshots) {
            List<Beer> beers = new LinkedList<>();
            for (DataSnapshot s : dataSnapshots) {
                final StructBeer beer = s.getValue(StructBeer.class);
                beers.add(
                        new Beer(
                                beer.mName,
                                beer.mCountry,
                                beer.mStyle,
                                beer.mRating,
                                mPhotos.photo(beer.mPhoto)
                        ));
            }
            return beers;
        }

    }

    private static class GetChildren
            implements Func1<DataSnapshot, Iterable<DataSnapshot>> {
        @Override
        public Iterable<DataSnapshot> call(
                final DataSnapshot snapshot) {
            return snapshot.getChildren();
        }
    }
}
