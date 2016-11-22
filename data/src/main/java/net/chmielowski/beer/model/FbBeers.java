package net.chmielowski.beer.model;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.kelvinapps.rxfirebase.RxFirebaseDatabase;

import java.util.LinkedList;
import java.util.List;

import rx.Observable;
import rx.functions.Func1;
import rx.subjects.ReplaySubject;

public final class FbBeers implements Beers {

    private final DatabaseReference mDatabase;

    public FbBeers(final DatabaseReference ref) {
        mDatabase = ref;
    }

    @Override
    public Observable<List<Beer>> list() {
        return RxFirebaseDatabase.observeSingleValueEvent(mDatabase)
                                 .flatMap(new SnapshotToListObservable());
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
        @Override
        public Observable<List<Beer>> call(final DataSnapshot snapshot) {
            ReplaySubject<Iterable<DataSnapshot>> subject =
                    ReplaySubject.create();
            subject.onNext(snapshot.getChildren());
            return subject.map(new IterableToList());
        }

    }

    static class IterableToList
            implements Func1<Iterable<DataSnapshot>, List<Beer>> {
        @Override
        public List<Beer> call(final Iterable<DataSnapshot> dataSnapshots) {
            // CHECKSTYLE:OFF
            List<Beer> beers = new LinkedList<Beer>();
            for (DataSnapshot s : dataSnapshots) {
                final StructBeer beer = s.getValue(StructBeer.class);
                beers.add(new Beer(beer.mName, beer.mCountry, beer.mStyle,
                                   beer.mRating,
                                   new FbPhotos(
                                           FirebaseStorage.getInstance(),
                                           "gs://beers-541d0.appspot.com"
                                   ).photo("054081b0-1833-46fd-975e-0757fcd7ead9")

                ));
            }
            return beers;
            // CHECKSTYLE:ON
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
