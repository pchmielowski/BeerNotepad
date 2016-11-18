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

    public Beer(final String name, final String country, final String style,
            final float rating) {
        mName = name;
        mCountry = country;
        mStyle = style;
        mRating = rating;
    }

    public BeerView showOn(final BeerView view) {
        view.showBeer(mName, mRating, mStyle, mCountry);
        return view; // TODO: why is it returning?
    }

    @EqualsAndHashCode
    public static final class CompareByCountry implements Comparator<Beer> {
        private final boolean mAscending;

        public CompareByCountry(final boolean ascending) {
            mAscending = ascending;
        }

        @Override
        public int compare(final Beer first, final Beer second) {
            return new Inverted(
                    new Normalized(
                            first.mCountry.compareTo(second.mCountry)
                    ).value(),
                    !mAscending
            ).value();

        }

    }

    @EqualsAndHashCode
    public static final class CompareByRating implements Comparator<Beer> {
        private final boolean mAscending;

        public CompareByRating(final boolean ascending) {
            mAscending = ascending;
        }

        @Override
        public int compare(final Beer first, final Beer second) {
            return new Inverted(
                    Float.compare(first.mRating, second.mRating),
                    !mAscending
            ).value();
        }
    }

}
