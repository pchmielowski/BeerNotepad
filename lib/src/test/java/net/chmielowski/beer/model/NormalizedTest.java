package net.chmielowski.beer.model;

import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.Matchers.is;

public class NormalizedTest {
    @Test
    public void value() throws Exception {
        Assert.assertThat(new Beer.CompareByCountry.Normalized(-3).value(), is(-1));
        Assert.assertThat(new Beer.CompareByCountry.Normalized(-2).value(), is(-1));
        Assert.assertThat(new Beer.CompareByCountry.Normalized(-1).value(), is(-1));
        Assert.assertThat(new Beer.CompareByCountry.Normalized(0).value(), is(0));
        Assert.assertThat(new Beer.CompareByCountry.Normalized(2).value(), is(1));
        Assert.assertThat(new Beer.CompareByCountry.Normalized(1).value(), is(1));
        Assert.assertThat(new Beer.CompareByCountry.Normalized(3).value(), is(1));
    }
}

