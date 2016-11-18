package net.chmielowski.beer.model;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import lombok.EqualsAndHashCode;
import rx.functions.Func1;

@EqualsAndHashCode
public final class SortBeerFunction implements Func1<List<Beer>, List<Beer>> {

    private final Comparator<Beer> mRule;

    public SortBeerFunction(
            final Comparator<Beer> rule) {
        mRule = rule;
    }

    @Override
    public List<Beer> call(final List<Beer> beers) {
        Collections.sort(beers, mRule);
        return beers;
    }
}
