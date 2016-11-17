package net.chmielowski.beer.model;

import net.chmielowski.beer.ui.beers.BeerView;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public final class BeerTest {
    private final String country = "country";
    private final String style = "style";
    private final float rating = 4.5f;
    private final String name = "name";
    @Mock
    BeerView mockedView;

    @Test
    public void shows_on_view() throws Exception {

        new Beer(name, country, style, rating).showOn(mockedView);

        Mockito.verify(mockedView).showBeer(name, rating, style, country);
    }

    @Test
    public void compares_by_country() throws Exception {
        final String lower = "Austria";
        final String higher = "Czechyyy";

        final int resultLower = new Beer(name, lower, style, rating)
                .compareByCountry(new Beer(name, higher, style, rating));

        final int resultTheSame = new Beer(name, lower, style, rating)
                .compareByCountry(new Beer(name, lower, style, rating));

        final int resultHigher = new Beer(name, higher, style, rating)
                .compareByCountry(new Beer(name, lower, style, rating));

        assertThat(resultLower, is(-1));
        assertThat(resultTheSame, is(0));
        assertThat(resultHigher, is(1));
    }

    @Test
    public void compares_by_rating() throws Exception {
        final float lower = 0.1f;
        final float higher = 4.5f;
        final int resultLower = new Beer(name, country, style, lower)
                .compareByRating(new Beer(name, country, style, higher));

        final int resultTheSame = new Beer(name, country, style, lower)
                .compareByRating(new Beer(name, country, style, lower));

        final int resultHigher = new Beer(name, country, style, higher)
                .compareByRating(new Beer(name, country, style, lower));

        assertThat(resultLower, is(-1));
        assertThat(resultTheSame, is(0));
        assertThat(resultHigher, is(1));
    }

}
