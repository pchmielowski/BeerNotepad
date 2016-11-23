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
    @Mock
    Photo mockedPhoto;

    @Test
    public void shows_on_view() throws Exception {

        new Beer(name, country, style, rating, mockedPhoto).showOn(mockedView);

        Mockito.verify(mockedView).show(
                name, rating, style, country, mockedPhoto);
    }

    @Test
    public void compares_by_country_ascending() {
        final String lower = "Austria";
        final String higher = "Czechy";

        final Beer.CompareByCountry rule =
                new Beer.CompareByCountry(true);
        final int resultLower =
                rule.compare(
                        new Beer(name, lower, style, rating, mockedPhoto),
                        new Beer(name, higher, style, rating, mockedPhoto)
                );

        final int resultTheSame =
                rule.compare(
                        new Beer(name, lower, style, rating, mockedPhoto),
                        new Beer(name, lower, style, rating, mockedPhoto)
                );

        final int resultHigher =
                rule.compare(
                        new Beer(name, higher, style, rating, mockedPhoto),
                        new Beer(name, lower, style, rating, mockedPhoto)
                );

        assertThat(resultLower, is(-1));
        assertThat(resultTheSame, is(0));
        assertThat(resultHigher, is(1));
    }

    @Test
    public void compares_by_country_descending() {
        final String lower = "Austria";
        final String higher = "Czechy";

        final Beer.CompareByCountry rule =
                new Beer.CompareByCountry(false);
        final int resultLower =
                rule.compare(
                        new Beer(name, higher, style, rating, mockedPhoto),
                        new Beer(name, lower, style, rating, mockedPhoto)
                );

        final int resultTheSame =
                rule.compare(
                        new Beer(name, lower, style, rating, mockedPhoto),
                        new Beer(name, lower, style, rating, mockedPhoto)
                );

        final int resultHigher =
                rule.compare(
                        new Beer(name, lower, style, rating, mockedPhoto),
                        new Beer(name, higher, style, rating, mockedPhoto)
                );

        assertThat(resultLower, is(-1));
        assertThat(resultTheSame, is(0));
        assertThat(resultHigher, is(1));
    }

    @Test
    public void compares_by_rating_ascending() {
        final float lower = 0.1f;
        final float higher = 4.5f;

        final Beer.CompareByRating rule = new Beer.CompareByRating(true);
        final int resultLower =
                rule.compare(
                        new Beer(name, country, style, lower, mockedPhoto),
                        new Beer(name, country, style, higher, mockedPhoto)
                );

        final int resultTheSame =
                rule.compare(
                        new Beer(name, country, style, lower, mockedPhoto),
                        new Beer(name, country, style, lower, mockedPhoto)
                );

        final int resultHigher =
                rule.compare(
                        new Beer(name, country, style, higher, mockedPhoto),
                        new Beer(name, country, style, lower, mockedPhoto)
                );

        assertThat(resultLower, is(-1));
        assertThat(resultTheSame, is(0));
        assertThat(resultHigher, is(1));
    }

    @Test
    public void compares_by_rating_descending() {
        final float lower = 0.1f;
        final float higher = 4.5f;

        final Beer.CompareByRating rule = new Beer.CompareByRating(false);
        final int resultLower =
                rule.compare(
                        new Beer(name, country, style, higher, mockedPhoto),
                        new Beer(name, country, style, lower, mockedPhoto)
                );

        final int resultTheSame =
                rule.compare(
                        new Beer(name, country, style, lower, mockedPhoto),
                        new Beer(name, country, style, lower, mockedPhoto)
                );

        final int resultHigher =
                rule.compare(
                        new Beer(name, country, style, lower, mockedPhoto),
                        new Beer(name, country, style, higher, mockedPhoto)
                );

        assertThat(resultLower, is(-1));
        assertThat(resultTheSame, is(0));
        assertThat(resultHigher, is(1));
    }

}
