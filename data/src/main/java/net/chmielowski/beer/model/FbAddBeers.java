package net.chmielowski.beer.model;

import com.google.firebase.database.DatabaseReference;

public final class FbAddBeers implements AddBeers {

    private final DatabaseReference mDatabase;

    public FbAddBeers(final DatabaseReference ref) {
        mDatabase = ref;
    }

    @Override
    public void add(final String name, final String country, final String style,
            final float rating, final String photo) {
        final FbReadBeers.StructBeer beer = new FbReadBeers.StructBeer();
        beer.mName = name;
        beer.mCountry = country;
        beer.mStyle = style;
        beer.mRating = rating;
        beer.mPhoto = photo;
        mDatabase.push().setValue(beer);
    }
}
