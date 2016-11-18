package net.chmielowski.beer.ui.beers;

import net.chmielowski.beer.model.Beer;
import net.chmielowski.beer.model.SortBeerFunction;

import java.util.List;

import rx.functions.Func1;
import rx.functions.Func2;

class MethodNumberToSortingFunction
        implements Func2<Boolean, Integer, Func1<List<Beer>, List<Beer>>> {
    @Override
    public Func1<List<Beer>, List<Beer>> call(final Boolean ascending,
            final Integer choice) {
        switch (choice) {
            case 0:
                return new SortBeerFunction(
                        new Beer.CompareByRating(ascending));
            case 1:
                return new SortBeerFunction(
                        new Beer.CompareByCountry(ascending));
            default:
                throw new IllegalStateException();
        }
    }
}
