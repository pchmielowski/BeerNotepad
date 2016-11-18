package net.chmielowski.numeric;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

public class NormalizedTest {
    @Test
    public void value() throws Exception {
        Assert.assertThat(new Normalized(-3).value(), Matchers.is(-1));
        Assert.assertThat(new Normalized(-2).value(), Matchers.is(-1));
        Assert.assertThat(new Normalized(-1).value(), Matchers.is(-1));
        Assert.assertThat(new Normalized(0).value(), Matchers.is(0));
        Assert.assertThat(new Normalized(2).value(), Matchers.is(1));
        Assert.assertThat(new Normalized(1).value(), Matchers.is(1));
        Assert.assertThat(new Normalized(3).value(), Matchers.is(1));
    }
}

