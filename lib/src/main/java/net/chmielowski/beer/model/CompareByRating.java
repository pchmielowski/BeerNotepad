package net.chmielowski.beer.model;

import java.util.Comparator;

public final class CompareByRating implements Comparator<Beer> {
    @Override
    public int compare(final Beer first, final Beer second) {
        return first.compareByRating(second);
    }
}
