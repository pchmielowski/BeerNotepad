package net.chmielowski.beer.model;

import net.chmielowski.beer.ui.beers.BeerView;

import lombok.EqualsAndHashCode;

import static java.lang.Math.abs;

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

    int compareByCountry(final Beer second) {
        return new Normalized(this.mCountry.compareTo(second.mCountry)).value();
    }

    int compareByRating(final Beer second) {
        return Float.compare(this.mRating, second.mRating);

    }

    static class Normalized {
        private final int mValue;

        Normalized(final int value) {
            mValue = value;
        }

        int value() {
            if (mValue == 0) {
                return 0;
            }
            return mValue / abs(mValue);
        }
    }
}
