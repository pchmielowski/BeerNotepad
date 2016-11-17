package net.chmielowski.beer;

import android.app.Application;

import net.chmielowski.beer.dagger.DaggerDgComponent;
import net.chmielowski.beer.dagger.DgComponent;

public class BeerApplication extends Application {

    private final DgComponent component = component();

    // CHECKSTYLE:OFF
    protected DgComponent component() {
        return DaggerDgComponent.builder().build();
    }
    // CHECKSTYLE:ON

    public final DgComponent cachedComponent() {
        return component;
    }

}
