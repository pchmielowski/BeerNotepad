package net.chmielowski.beer.ui.beers;

import android.app.Instrumentation;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import net.chmielowski.beer.BeerApplication;
import net.chmielowski.beer.R;
import net.chmielowski.beer.TestComponent;
import net.chmielowski.beer.model.Beer;
import net.chmielowski.beer.model.Beers;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import rx.subjects.ReplaySubject;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.PositionAssertions
        .isAbove;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public final class BeersActivityTest {


    @Rule
    public ActivityTestRule<BeersActivity> mActivityTestRule =
            new ActivityTestRule<>(BeersActivity.class, true, false);
    @Inject
    Beers mockedBeers;

    @Before
    public void setUp() {
        Instrumentation instrumentation =
                InstrumentationRegistry.getInstrumentation();
        BeerApplication app = (BeerApplication) instrumentation
                .getTargetContext().getApplicationContext();
        TestComponent component = (TestComponent) app.cachedComponent();
        component.inject(this);
    }

    @Test
    public void sortedByRating() {
        ReplaySubject<List<Beer>> subject = ReplaySubject.create();
        subject.onNext(Arrays.asList(
                new Beer("bbb", "c1", "s1", 0.0f),
                new Beer("aaa", "c2", "s2", 1.0f)
        ));
        Mockito.when(mockedBeers.list())
               .thenReturn(subject);

        mActivityTestRule.launchActivity(new Intent());

        onView(withText("bbb"))
                .check(isAbove(withText("aaa")));
    }

    @Test
    public void sortedByCountry() {
        ReplaySubject<List<Beer>> subject = ReplaySubject.create();
        subject.onNext(Arrays.asList(
                new Beer("from bbb", "Czechy", "s1", 0.0f),
                new Beer("from aaa", "Austria", "s2", 1.0f)
        ));
        Mockito.when(mockedBeers.list())
               .thenReturn(subject);

        mActivityTestRule.launchActivity(new Intent());

        onView(withId(R.id.beers_sp_sort))
                .perform(click());
        onView(withText("Country"))
                .perform(click());

        onView(withText("from aaa"))
                .check(isAbove(withText("from bbb")));
    }

}
