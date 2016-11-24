package net.chmielowski.beer.model;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.kelvinapps.rxfirebase.RxFirebaseDatabase;

import java.util.LinkedList;
import java.util.List;

import rx.Observable;
import rx.functions.Func1;
import rx.subjects.ReplaySubject;

public final class FbBeers implements Beers {

    private final DatabaseReference mDatabase;
    private final Photos mPhotos;

    public FbBeers(final DatabaseReference ref, final Photos photos) {
        mDatabase = ref;
        mPhotos = photos;
    }

    @Override
    public Observable<List<Beer>> list() {
        return RxFirebaseDatabase
                .observeValueEvent(mDatabase)
                .flatMap(new SnapshotToListObservable(mPhotos));
    }

    @Override
    public void add(final String name, final String country, final String style,
            final float rating, final String photo) {
        final StructBeer beer = new StructBeer();
        beer.mName = name;
        beer.mCountry = country;
        beer.mStyle = style;
        beer.mRating = rating;
        beer.mPhoto = photo;
        mDatabase.push().setValue(beer);
    }

    private static class SnapshotToListObservable
            implements Func1<DataSnapshot, Observable<List<Beer>>> {

        private final Photos mPhotos;

        SnapshotToListObservable(final Photos photos) {
            mPhotos = photos;
        }

        @Override
        public Observable<List<Beer>> call(final DataSnapshot snapshot) {
            ReplaySubject<Iterable<DataSnapshot>> subject =
                    ReplaySubject.create();
            subject.onNext(snapshot.getChildren());
            return subject.map(new IterableToList(mPhotos));
        }

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

    static final class StructBeer {
        String mName;
        String mCountry;
        String mStyle;
        float mRating;
        String mPhoto;
    }

}
