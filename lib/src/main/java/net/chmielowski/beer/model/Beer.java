package net.chmielowski.beer.model;

import net.chmielowski.beer.ui.beers.BeerView;
import net.chmielowski.numeric.Inverted;
import net.chmielowski.numeric.Normalized;

import java.util.Comparator;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public final class Beer {
    private final String mName;
    private final String mCountry;
    private final String mStyle;
    private final float mRating;
    private final Photo mPhoto;

    public Beer(final String name, final String country, final String style,
            final float rating, final Photo photo) {
        mName = name;
        mCountry = country;
        mStyle = style;
        mRating = rating;
        mPhoto = photo;
    }

    public BeerView showOn(final BeerView view) {
        view.showBeer(mName, mRating, mStyle, mCountry, mPhoto);
        return view; // TODO: why is it returning?
    }

    @EqualsAndHashCode(callSuper = true)
    public static final class CompareByCountry extends CompareBeer {

        public CompareByCountry(final boolean ascending) {
            super(ascending);
        }

        @Override
        public int compare(final Beer first, final Beer second) {
            return new CompareStrings(
                    first.mCountry,
                    second.mCountry,
                    mAscending
            ).value();
        }

    }

    @EqualsAndHashCode(callSuper = true)
    public static final class CompareByRating extends CompareBeer {

        public CompareByRating(final boolean ascending) {
            super(ascending);
        }

        @Override
        public int compare(final Beer first, final Beer second) {
            return new Inverted(
                    Float.compare(first.mRating, second.mRating),
                    !mAscending
            ).value();
        }
    }

    private abstract static class CompareBeer implements Comparator<Beer> {
        final boolean mAscending;

        CompareBeer(final boolean ascending) {
            mAscending = ascending;
        }
    }

    private static class CompareStrings {
        private final String mFirst;
        private final String mSecond;
        private final boolean mAscending;

        CompareStrings(final String first, final String second,
                final boolean ascending) {
            mFirst = first;
            mSecond = second;
            mAscending = ascending;
        }

        private int value() {
            return new Inverted(
                    new Normalized(
                            mFirst.compareTo(mSecond)
                    ).value(),
                    !mAscending
            ).value();
        }
    }
}
