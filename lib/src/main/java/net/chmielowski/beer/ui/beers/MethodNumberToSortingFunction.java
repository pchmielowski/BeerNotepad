package net.chmielowski.beer.ui.beers;

import net.chmielowski.beer.model.Beer;
import net.chmielowski.beer.model.CompareByCountry;
import net.chmielowski.beer.model.CompareByRating;
import net.chmielowski.beer.model.SortBeerFunction;

import java.util.List;

import rx.functions.Func1;

class MethodNumberToSortingFunction
        implements Func1<Integer, Func1<List<Beer>, List<Beer>>> {
    @Override
    public Func1<List<Beer>, List<Beer>> call(final Integer choice) {
        switch (choice) {
            case 0:
                return new SortBeerFunction(new CompareByRating());
            case 1:
                return new SortBeerFunction(new CompareByCountry());
            default:
                throw new IllegalStateException();
        }
    }
}
