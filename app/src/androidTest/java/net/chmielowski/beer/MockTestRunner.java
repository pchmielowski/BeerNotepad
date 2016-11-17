package net.chmielowski.beer;

import android.app.Application;
import android.content.Context;
import android.support.test.runner.AndroidJUnitRunner;

public final class MockTestRunner extends AndroidJUnitRunner {
    @Override
    public Application newApplication(
            final ClassLoader cl,
            final String className, final Context context
    )
            throws InstantiationException, IllegalAccessException,
            ClassNotFoundException {
        return super.newApplication(
                cl,
                MockBeerApplication.class.getName(),
                context
        );
    }
}
