package net.chmielowski.beer;

import net.chmielowski.beer.dagger.DgComponent;

public final class MockBeerApplication extends BeerApplication {
    @Override
    protected DgComponent component() {
        return DaggerTestComponent.builder().build();
    }
}
