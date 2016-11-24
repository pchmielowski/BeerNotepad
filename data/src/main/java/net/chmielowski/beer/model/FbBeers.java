package net.chmielowski.beer.model;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.kelvinapps.rxfirebase.RxFirebaseDatabase;

import java.util.LinkedList;
import java.util.List;

import rx.Observable;
import rx.functions.Func1;

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
                .map(new GetChildren())
                .map(new IterableToList(mPhotos));
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

    private static class GetChildren
            implements Func1<DataSnapshot, Iterable<DataSnapshot>> {
        @Override
        public Iterable<DataSnapshot> call(
                final DataSnapshot snapshot) {
            return snapshot.getChildren();
        }
    }
}
